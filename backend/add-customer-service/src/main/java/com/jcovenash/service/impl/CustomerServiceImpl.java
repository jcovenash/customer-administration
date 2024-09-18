package com.jcovenash.service.impl;

import com.jcovenash.model.Customer;
import com.jcovenash.repository.CustomerRepository;
import com.jcovenash.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public ResponseEntity<?> createCustomer(Customer customer) {
        log.info("-------- CustomerServiceImpl(): Ingreso al metodo createCustomer() -----------");

        if (customerRepository.findByName(customer.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("{\"message\": \"Ya existe un cliente con ese nombre\"}");
        }

        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("-------- CustomerServiceImpl(): Ingreso al metodo getAllCustomers() -----------");
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        log.info("-------- CustomerServiceImpl(): Ingreso al metodo getCustomerById() -----------");
        return customerRepository.findById(id);
    }
}
