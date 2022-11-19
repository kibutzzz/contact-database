package com.arvores.ordenacao.contatos.utility;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArvoreAvl<T> {

  protected No<T> raiz;

  private Comparator<T> comparator;

  private ArvoreAvl(No<T> raiz, Comparator<T> comparator) {
    this.raiz = raiz;
    this.comparator = comparator;
  }

  public ArvoreAvl(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  public void inserir(T k) {
    final var n = new No<>(k);
    inserirAVL(this.raiz, n);
  }

  private void inserirAVL(No<T> aComparar, No<T> aInserir) {

    if (aComparar == null) {
      this.raiz = aInserir;

    } else {

      if (comparator.compare(aInserir.getChave(), aComparar.getChave()) < 0) {

        if (aComparar.getEsquerda() == null) {
          aComparar.setEsquerda(aInserir);
          aInserir.setPai(aComparar);
          verificarBalanceamento(aComparar);

        } else {
          inserirAVL(aComparar.getEsquerda(), aInserir);
        }

      } else if (comparator.compare(aInserir.getChave(), aComparar.getChave()) > 0) {

        if (aComparar.getDireita() == null) {
          aComparar.setDireita(aInserir);
          aInserir.setPai(aComparar);
          verificarBalanceamento(aComparar);

        } else {
          inserirAVL(aComparar.getDireita(), aInserir);
        }

      }
    }
  }

  private void verificarBalanceamento(No<T> atual) {
    setBalanceamento(atual);
    final var balanceamento = atual.getBalanceamento();

    if (balanceamento == -2) {

      if (altura(atual.getEsquerda().getEsquerda()) >= altura(atual.getEsquerda().getDireita())) {
        atual = rotacaoDireita(atual);

      } else {
        atual = duplaRotacaoEsquerdaDireita(atual);
      }

    } else if (balanceamento == 2) {

      if (altura(atual.getDireita().getDireita()) >= altura(atual.getDireita().getEsquerda())) {
        atual = rotacaoEsquerda(atual);

      } else {
        atual = duplaRotacaoDireitaEsquerda(atual);
      }
    }

    if (atual.getPai() != null) {
      verificarBalanceamento(atual.getPai());
    } else {
      this.raiz = atual;
    }
  }

  public List<T> buscar(Comparator<T> comparator, T k) {

    final var node = buscar(this.raiz, k, comparator);

    return new ArvoreAvl<>(node, comparator).inorder().stream()
        .map(No::getChave)
        .filter(contact -> comparator.compare(contact, k) == 0)
        .collect(Collectors.toList());
  }

  private No<T> buscar(No<T> atual, T k, Comparator<T> comparator) {
    if (atual == null) {
      return null;
    } else {

      if (comparator.compare(atual.getChave(), k) > 0) {
        return buscar(atual.getEsquerda(), k, comparator);

      } else if (comparator.compare(atual.getChave(), k) < 0) {
        return buscar(atual.getDireita(), k, comparator);

      }
      return atual;
    }
  }

  private No<T> rotacaoEsquerda(No<T> inicial) {

    final var direita = inicial.getDireita();
    direita.setPai(inicial.getPai());

    inicial.setDireita(direita.getEsquerda());

    if (inicial.getDireita() != null) {
      inicial.getDireita().setPai(inicial);
    }

    direita.setEsquerda(inicial);
    inicial.setPai(direita);

    if (direita.getPai() != null) {

      if (direita.getPai().getDireita() == inicial) {
        direita.getPai().setDireita(direita);

      } else if (direita.getPai().getEsquerda() == inicial) {
        direita.getPai().setEsquerda(direita);
      }
    }

    setBalanceamento(inicial);
    setBalanceamento(direita);

    return direita;
  }

  private No<T> rotacaoDireita(No<T> inicial) {

    final var esquerda = inicial.getEsquerda();
    esquerda.setPai(inicial.getPai());

    inicial.setEsquerda(esquerda.getDireita());

    if (inicial.getEsquerda() != null) {
      inicial.getEsquerda().setPai(inicial);
    }

    esquerda.setDireita(inicial);
    inicial.setPai(esquerda);

    if (esquerda.getPai() != null) {

      if (esquerda.getPai().getDireita() == inicial) {
        esquerda.getPai().setDireita(esquerda);

      } else if (esquerda.getPai().getEsquerda() == inicial) {
        esquerda.getPai().setEsquerda(esquerda);
      }
    }

    setBalanceamento(inicial);
    setBalanceamento(esquerda);

    return esquerda;
  }

  private No<T> duplaRotacaoEsquerdaDireita(No<T> inicial) {
    inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
    return rotacaoDireita(inicial);
  }

  private No<T> duplaRotacaoDireitaEsquerda(No<T> inicial) {
    inicial.setDireita(rotacaoDireita(inicial.getDireita()));
    return rotacaoEsquerda(inicial);
  }

  private int altura(No<T> atual) {
    if (atual == null) {
      return -1;
    }

    if (atual.getEsquerda() == null && atual.getDireita() == null) {
      return 0;

    } else if (atual.getEsquerda() == null) {
      return 1 + altura(atual.getDireita());

    } else if (atual.getDireita() == null) {
      return 1 + altura(atual.getEsquerda());

    } else {
      return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
    }
  }

  private void setBalanceamento(No<T> no) {
    no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
  }

  public final List<No<T>> inorder() {
    final var ret = new ArrayList<No<T>>();
    inorder(raiz, ret);
    return ret;
  }

  private void inorder(No<T> no, List<No<T>> lista) {
    if (no == null) {
      return;
    }
    inorder(no.getEsquerda(), lista);
    lista.add(no);
    inorder(no.getDireita(), lista);
  }
}