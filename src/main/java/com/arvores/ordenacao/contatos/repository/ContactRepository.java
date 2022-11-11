package com.arvores.ordenacao.contatos.repository;

import com.arvores.ordenacao.contatos.domain.Contact;
import com.arvores.ordenacao.contatos.utility.ContactFileReader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactRepository {

  private final ContactFileReader contactFileReader;
  private final List<Contact> contacts;

  ContactRepository(ContactFileReader contactFileReader) {
    this.contactFileReader = contactFileReader;
    this.contacts = this.contactFileReader.read();
  }

  public List<Contact> findAll() {
    return contacts;
  }

}
