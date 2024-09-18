package com.jcovenash.service;

import com.jcovenash.model.Contact;

import java.util.List;

public interface ContactService {
    Contact createContact(Contact contactRequest);
    List<Contact> getContactByIdCustomer(Long id);
}
