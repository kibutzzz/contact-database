package com.arvores.ordenacao.contatos.controller;


import com.arvores.ordenacao.contatos.domain.Contact;
import com.arvores.ordenacao.contatos.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactController {

  private final ContactRepository repository;


  @GetMapping("/name")
  public List<Contact> findByName() {
    return repository.findByName("");
  }
  @GetMapping("/cpf")
  public List<Contact> findByCpf() {
    return repository.findByCpf("");
  }
  @GetMapping("/birthDate")
  public List<Contact> findByBirthDate() {
    return repository.findByBirthDate("");
  }

  @PostMapping
  public Contact saveContact(@RequestBody Contact contact) {
    return repository.saveContact(contact);
  }

}
