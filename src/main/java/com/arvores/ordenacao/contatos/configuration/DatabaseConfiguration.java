package com.arvores.ordenacao.contatos.configuration;

import com.arvores.ordenacao.contatos.utility.ContactFileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

  public static final String FILE_NAME = "/contacts.csv";

  @Bean
  public ContactFileReader contactFileReader() {
    return new ContactFileReader(FILE_NAME);
  }
}
