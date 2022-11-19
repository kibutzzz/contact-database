package com.arvores.ordenacao.contatos.controller;


import com.arvores.ordenacao.contatos.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ContactController implements CommandLineRunner {


  private final ConfigurableApplicationContext context;
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final ContactRepository repository;
  private boolean isRunning = true;

  @Override
  public void run(String... args) {

    while (isRunning) {
      chooseOption();
    }

    context.close();
  }

  public void printMenu() {
    System.out.println("\n\n1 - Buscar cpf \n" +
        "2 - Buscar nome \n" +
        "3 - Buscar data nascimento \n" +
        "4 - Sair \n");
  }

  public void chooseOption() {
    Scanner input = new Scanner(System.in);
    printMenu();
    System.out.println("\nEscolha uma opção: ");
    int option = input.nextInt();

    switch (option) {
      case 1:
        buscarCpf();
        break;
      case 2:
        buscarNome();
        break;
      case 3:
        buscarPorDatas();
        break;
      case 4:
        System.out.println("\nEncerrando operação...");
        this.isRunning = false;
        break;
      default:
        System.out.println("\nOpção Inválida, escolha outra! \n");
        break;
    }
  }

  private void buscarCpf() {
    Scanner input = new Scanner(System.in);
    System.out.println("Digite CPF: \n");
    final var cpf = input.nextLine();

    repository.findByCpf(Long.parseLong(cpf)).forEach(System.out::println);
  }


  private void buscarNome() {
    Scanner input = new Scanner(System.in);
    System.out.println("Digite Nome: \n");
    final var name = input.nextLine();

    repository.findByName(name).forEach(System.out::println);
  }


  private void buscarPorDatas() {
    Scanner input = new Scanner(System.in);
    System.out.println("Digite data minima: \n");
    final var minDate = LocalDate.parse(input.nextLine(), FORMATTER);
    System.out.println("Digite data maxima: \n");
    final var maxDate = LocalDate.parse(input.nextLine(), FORMATTER);

    repository.findByBirthDate(minDate, maxDate).forEach(System.out::println);
  }


}
