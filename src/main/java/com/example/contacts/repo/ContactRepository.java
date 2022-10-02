package com.example.contacts.repo;

import com.example.contacts.models.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository <Contact, Long>{

}
