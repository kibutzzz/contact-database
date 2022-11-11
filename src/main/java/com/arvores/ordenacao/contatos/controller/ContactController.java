package com.arvores.ordenacao.contatos.controller;


import com.arvores.ordenacao.contatos.domain.Contact;
import com.arvores.ordenacao.contatos.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("contacts/")
@RequiredArgsConstructor
public class ContactController {

  private final ContactRepository repository;


  @GetMapping
  public List<Contact> readByName() {

    return repository.findAll();
  }

}
