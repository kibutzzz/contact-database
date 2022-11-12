package com.arvores.ordenacao.contatos.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
public class No<T> {

  private No<T> esquerda;
  private No<T> direita;
  private No<T> pai;

  @Setter
  private T chave;
  @Setter
  private int balanceamento;

  public No(T k) {
    setEsquerda(setDireita(setPai(null)));
    setBalanceamento(0);
    setChave(k);
  }

  public String toString() {
    return getChave().toString();
  }

  public No<T> setPai(No<T> pai) {
    this.pai = pai;
    return pai;
  }


  public No<T> setDireita(No<T> direita) {
    this.direita = direita;
    return direita;
  }


  public void setEsquerda(No<T> esquerda) {
    this.esquerda = esquerda;
  }
}