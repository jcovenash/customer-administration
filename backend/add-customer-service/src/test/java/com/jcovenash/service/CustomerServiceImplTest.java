package com.jcovenash.service;

import com.jcovenash.model.Customer;
import com.jcovenash.repository.CustomerRepository;
import com.jcovenash.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    public void setUp () {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Junior Smit");
    }

    @Test
    @DisplayName("Dado los datos de un cliente, cuando se ejecuta el crea cliente, entonces se agrega el cliente y retorna CREATED")
    public void givenCustomersData_whenCreateCustomer_thenCustomerIsAdded() {
        when(customerRepository.findByName(customer.getName())).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        ResponseEntity<?> response = customerService.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    @DisplayName("Dado un cliente existente, cuando se ejecuta el crea cliente, entonces no se agrega el cliente y retorna CONFLICT")
    public void givenExistingClient_whenCreateCustomer_thenClientIsNotAdded() {
        when(customerRepository.findByName(customer.getName())).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = customerService.createCustomer(customer);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("{\"message\": \"Ya existe un cliente con ese nombre\"}", response.getBody());
    }

    @Test
    @DisplayName("Dado el endpoint parar listar clientes, cuando se ejecta mostrar todos los clientes, entonces muestra lista de clientes")
    public void givenEndpoint_whenGetAllCustomers_thenShowListCustomers() {
        List<Customer> customerList;
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");
        customerList = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        assertEquals(customerList, result);
    }

    @Test
    @DisplayName("Dado el ID de un cliente, cuando se ejecuta buscar cleinte por ID, entonces muestra los datos del cliente")
    public void givenIdCustomer_whenGetCustomerById_thenShowCustomer() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Optional<Customer> result = customerService.getCustomerById(customer.getId());

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

}
