package com.arvores.ordenacao.contatos.repository;

import com.arvores.ordenacao.contatos.domain.Contact;
import com.arvores.ordenacao.contatos.utility.ArvoreAvl;
import com.arvores.ordenacao.contatos.utility.ContactFileReader;
import com.arvores.ordenacao.contatos.utility.No;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

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
     return nameIndexedTree.inorder().stream().map(No::getChave).collect(Collectors.toList());
  }

  public List<Contact> findByBirthDate(String name) {
    return birthDateIndexedTree.inorder().stream().map(No::getChave).collect(Collectors.toList());
  }

  public List<Contact> findByCpf(String cpf) {
    return cpfIndexedTree.inorder().stream().map(No::getChave).collect(Collectors.toList());
  }

  public Contact saveContact(Contact contact) {
    cpfIndexedTree.inserir(contact);
    nameIndexedTree.inserir(contact);
    birthDateIndexedTree.inserir(contact);

    return contact;
  }

}
