package com.jcovenash.service.impl;

import com.jcovenash.model.Contact;
import com.jcovenash.model.Customer;
import com.jcovenash.repository.ContactRepository;
import com.jcovenash.service.ContactService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final RestTemplate restTemplate;

    @Override
    public Contact createContact(Contact contactRequest) {
        log.info("--------- ContactServiceImpl: Ingreso al metodo createContact() ----------");
        try {
            String url = "http://localhost:8081/api/v1/customers/" + contactRequest.getCustomerId();
            Customer customer = restTemplate.getForObject(url, Customer.class);

            contactRepository.save(contactRequest);
            return contactRequest;
        } catch (Exception e) {
            log.error("------- Sucedio un error: {}", e.getMessage());
            throw new RuntimeException("Error al obtener el cliente", e);
        }
    }

    @Override
    public List<Contact> getContactByIdCustomer(Long id) {
        log.info("--------- ContactServiceImpl: Ingreso al metodo getContactByIdCustomer() ----------");
        return contactRepository.findByCustomerId(id);
    }
}
