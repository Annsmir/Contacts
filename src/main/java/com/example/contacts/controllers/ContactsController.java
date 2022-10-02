package com.example.contacts.controllers;

import com.example.contacts.models.Contact;
import com.example.contacts.repo.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;


    @GetMapping("/contacts")
    public String contacts(Model model) {
        Iterable<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }

    @GetMapping("/add")
    public String contactsAdd(Model model) {
        return "contacts-add";
    }

    @PostMapping("/add")
    public String contactsPostAdd(@RequestParam String name, @RequestParam String surname, @RequestParam String mobile, @RequestParam String home, @RequestParam String info, Model model) {
        Contact contact = new Contact(name, surname, mobile, home, info);
        contactRepository.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/contact/{id}")
    public String contactsInfo(@PathVariable(value = "id") long id, Model model){
        if(!contactRepository.existsById(id)) {
            return "redirect:/contacts";
        }
        Optional<Contact> contact = contactRepository.findById(id);
        ArrayList<Contact> res = new ArrayList<>();
        contact.ifPresent(res::add);
        model.addAttribute("contact", res);
        return "contact-details";
    }

    @GetMapping("/contact/{id}/edit")
    public String contactsEdit(@PathVariable(value = "id") long id, Model model){
        if(!contactRepository.existsById(id)) {
            return "redirect:/contacts";
        }
        Optional<Contact> contact = contactRepository.findById(id);
        ArrayList<Contact> res = new ArrayList<>();
        contact.ifPresent(res::add);
        model.addAttribute("contact", res);
        return "contacts-edit";
    }

    @PostMapping("/contact/{id}/edit")
    public String contactsPostEdit(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String surname, @RequestParam String mobile, @RequestParam String home, @RequestParam String info, Model model) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setMobile(mobile);
        contact.setHome(home);
        contact.setInfo(info);
        contactRepository.save(contact);
        return "redirect:/contacts";
    }

    @PostMapping("/contact/{id}/delete")
    public String contactsPostDelete(@PathVariable(value = "id") long id, Model model) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        contactRepository.delete(contact);
        return "redirect:/contacts";
    }
}


