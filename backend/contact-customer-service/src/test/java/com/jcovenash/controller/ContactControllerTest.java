package com.jcovenash.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jcovenash.model.Contact;
import com.jcovenash.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ContactControllerTest {
    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp () {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Dado un cliente valido, cuando se ejecuta el crear contacto, entonces se crea el contacto")
    void givenValidCustomer_whenCreateContact_thenContactIsCreated() throws Exception {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setEmail("jcovenash@outlook.com");
        contact.setPhone("910788481");
        // Set other properties

        when(contactService.createContact(any(Contact.class))).thenReturn(contact);

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("jcovenash@outlook.com"))
                .andExpect(jsonPath("$.phone").value("910788481"));
    }

    @Test
    @DisplayName("Dado un cliente invalido, cuando se ejecuta el crear contacto, entonces no se crea el contacto y retorna BAD_REQUEST")
    void givenInvalidCustomer_whenCreateContact_thenNotContactIsCreated() throws Exception {
        when(contactService.createContact(any(Contact.class)))
                .thenThrow(new RuntimeException("Error al obtener el cliente"));

        Contact contact = new Contact();

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Dado ID del cliente, cuando se ejecuta el buscar contactos por ID, entonces muestra contactos del cliente")
    void givenIdCustomer_whenGetContactByIdCustomer_thenShowContact() throws Exception{
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setEmail("jcovenash@outlook.com");
        contact1.setPhone("910788481");
        // Set other properties

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setEmail("jcovenash@outlook.com");
        contact2.setPhone("910788481");
        // Set other properties

        List<Contact> contacts = Arrays.asList(contact1, contact2);

        when(contactService.getContactByIdCustomer(anyLong())).thenReturn(contacts);

        mockMvc.perform(get("/contacts/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("jcovenash@outlook.com"))
                .andExpect(jsonPath("$[0].phone").value("910788481"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("jcovenash@outlook.com"))
                .andExpect(jsonPath("$[1].phone").value("910788481"));
    }

}
