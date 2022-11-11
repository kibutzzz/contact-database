package com.arvores.ordenacao.contatos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
public class Contact {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private long cpf;
  private long rg;
  private String name;
  private LocalDate birthDate;
  private String birthPlace;

  public static Contact make(String[] values) {
    return new Contact(
        Long.parseLong(values[0]),
        Long.parseLong(values[1]),
        values[2],
        dateOf(values[3]),
        values[4]
    );
  }

  private static LocalDate dateOf(String date) {
    return Optional.ofNullable(date)
        .map(string -> LocalDate.parse(string, FORMATTER))
        .orElse(null);
  }

}
