package com.jcovenash.service;

import com.jcovenash.model.Contact;
import com.jcovenash.model.Customer;
import com.jcovenash.repository.ContactRepository;
import com.jcovenash.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    @DisplayName("Dado un cliente valido, cuando se ejecuta el crear contacto, entonces se crea el contacto")
    void givenValidCustomer_whenCreateContact_thenContactIsCreated() {
        Contact contactRequest = new Contact();
        contactRequest.setCustomerId(1L);
        Customer customerResponse = new Customer();

        when(restTemplate.getForObject("http://localhost:8081/api/v1/customers/1", Customer.class))
                .thenReturn(customerResponse);
        when(contactRepository.save(contactRequest)).thenReturn(contactRequest);

        Contact result = contactService.createContact(contactRequest);
        assertNotNull(result);
        verify(restTemplate).getForObject("http://localhost:8081/api/v1/customers/1", Customer.class);
        verify(contactRepository).save(contactRequest);
    }

    @Test
    @DisplayName("Dado un cliente invalido, cuando se ejecuta el crear contacto, entonces no se crea el contacto")
    void givenInvalidCustomer_whenCreateContact_thenNotContactIsCreated() {
        Contact contactRequest = new Contact();
        contactRequest.setCustomerId(1L);

        when(restTemplate.getForObject("http://localhost:8081/api/v1/customers/1", Customer.class))
                .thenThrow(new RuntimeException("Error en el cliente"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactService.createContact(contactRequest);
        });

        assertEquals("Error al obtener el cliente", exception.getMessage());

        verify(restTemplate).getForObject("http://localhost:8081/api/v1/customers/1", Customer.class);
        verify(contactRepository, never()).save(contactRequest);
    }

    @Test
    @DisplayName("Dado ID del cliente, cuando se ejecuta el buscar contactos por ID, entonces muestra contactos del cliente")
    void givenIdCustomer_whenGetContactByIdCustomer_thenShowContact() {
        Long customerId = 1L;
        List<Contact> contacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setCustomerId(customerId);
        contacts.add(contact1);
        Contact contact2 = new Contact();
        contact2.setCustomerId(customerId);
        contacts.add(contact2);

        when(contactRepository.findByCustomerId(customerId)).thenReturn(contacts);

        List<Contact> result = contactService.getContactByIdCustomer(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(contact1));
        assertTrue(result.contains(contact2));
        verify(contactRepository).findByCustomerId(customerId);
    }
}
