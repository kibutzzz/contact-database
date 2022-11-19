package com.arvores.ordenacao.contatos.repository;

import com.arvores.ordenacao.contatos.domain.Contact;
import com.arvores.ordenacao.contatos.utility.ArvoreAvl;
import com.arvores.ordenacao.contatos.utility.ContactFileReader;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Repository
public class ContactRepository {

  private final ContactFileReader contactFileReader;
  private final List<Contact> contacts;
  private final ArvoreAvl<Contact> nameIndexedTree;
  private final ArvoreAvl<Contact> birthDateIndexedTree;
  private final ArvoreAvl<Contact> cpfIndexedTree;



  public ContactRepository(ContactFileReader contactFileReader) {
    this.contactFileReader = contactFileReader;
    this.contacts = this.contactFileReader.read();

    this.nameIndexedTree = new ArvoreAvl<>(comparing(Contact::getName));
    this.birthDateIndexedTree = new ArvoreAvl<>(comparing(Contact::getBirthDate).thenComparing(Contact::getCpf));
    this.cpfIndexedTree = new ArvoreAvl<>(comparing(Contact::getCpf));

    this.contacts.forEach(contact -> {
      nameIndexedTree.inserir(contact);
      birthDateIndexedTree.inserir(contact);
      cpfIndexedTree.inserir(contact);
    });
  }

  public List<Contact> findByName(String name) {
    return nameIndexedTree.buscar((contact, key) -> {
      if (contact.getName().startsWith(key.getName())) {
        return 0;
      }

      return contact.getName().compareTo(key.getName());
    }, new Contact(0, 0, name, null, null));
  }

  public List<Contact> findByBirthDate(LocalDate minDate, LocalDate maxDate) {

    return birthDateIndexedTree
        .buscar((contact, key) -> {
          if (contact.getBirthDate().isAfter(key.getBirthDate())) {
            return 0;
          }

          return contact.getBirthDate().compareTo(key.getBirthDate());
        }, new Contact(0, 0, null, minDate, null))
        .stream().filter(contact -> contact.getBirthDate().isBefore(maxDate))
        .collect(Collectors.toList());
  }

  public List<Contact> findByCpf(long cpf) {
    return cpfIndexedTree.buscar(Comparator.comparing(Contact::getCpf),
        new Contact(cpf, 0, null, null, null));
  }

}
