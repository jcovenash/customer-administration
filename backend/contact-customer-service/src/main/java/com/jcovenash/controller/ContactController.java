package com.jcovenash.controller;

import com.jcovenash.model.Contact;
import com.jcovenash.service.ContactService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
@Slf4j
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        log.info("--------- ContactController: Ingreso al metodo createContact() ----------");
        try {
            Contact newContact = contactService.createContact(contact);
            return new ResponseEntity<>(newContact, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/{customer_id}")
    public List<Contact> getContactByIdCustomer (@PathVariable Long customer_id) {
        log.info("--------- ContactController: Ingreso al metodo getContactByIdCustomer() ----------");
        return contactService.getContactByIdCustomer(customer_id);
    }
}
