package com.jcovenash.controller;

import com.jcovenash.model.Customer;
import com.jcovenash.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        log.info("------- CustomerController: Ingreso al metodo createCustomer() --------");

        return customerService.createCustomer(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer() {
        log.info("------- CustomerController: Ingreso al metodo getAllCustomer() --------");
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        log.info("------- CustomerController: Ingreso al metodo getCustomerById() --------");
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
