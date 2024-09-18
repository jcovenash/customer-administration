package com.jcovenash.repository;

import com.jcovenash.model.Contact;
import com.jcovenash.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByCustomerId(Long customerId);
}
