package com.arvores.ordenacao.contatos.utility;

import com.arvores.ordenacao.contatos.domain.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContactFileReader {

  private final InputStreamReader inputStreamReader;

  public ContactFileReader(String fileName) {
    inputStreamReader = new InputStreamReader(ContactFileReader.class.getResourceAsStream(fileName));
  }

  public List<Contact> read() {
    final var contacts = new ArrayList<Contact>();

    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] values = line.split(";");
        contacts.add(Contact.make(values));
      }

    } catch (IOException e) {
      throw new RuntimeException("error reading file", e);
    }

    return contacts;
  }

}
