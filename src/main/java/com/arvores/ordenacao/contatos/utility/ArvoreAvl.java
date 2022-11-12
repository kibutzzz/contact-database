package com.arvores.ordenacao.contatos.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArvoreAvl<T> {

  protected No<T> raiz;

  private Comparator<T> comparator;

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

  public void remover(T k) {
    removerAVL(this.raiz, k);
  }

  private void removerAVL(No<T> atual, T k) {
    if (atual == null) {
      return;

    } else {

      if (comparator.compare(atual.getChave(), k) > 0) {
        removerAVL(atual.getEsquerda(), k);

      } else if (comparator.compare(atual.getChave(), k) < 0) {
        removerAVL(atual.getDireita(), k);

      } else if (comparator.compare(atual.getChave(), k) == 0) {
        removerNoEncontrado(atual);
      }
    }
  }

  private void removerNoEncontrado(No<T> aRemover) {
    No<T> r;

    if (aRemover.getEsquerda() == null || aRemover.getDireita() == null) {

      if (aRemover.getPai() == null) {
        this.raiz = null;
        aRemover = null;
        return;
      }
      r = aRemover;

    } else {
      r = sucessor(aRemover);
      aRemover.setChave(r.getChave());
    }

    No<T> p;
    if (r.getEsquerda() != null) {
      p = r.getEsquerda();
    } else {
      p = r.getDireita();
    }

    if (p != null) {
      p.setPai(r.getPai());
    }

    if (r.getPai() == null) {
      this.raiz = p;
    } else {
      if (r == r.getPai().getEsquerda()) {
        r.getPai().setEsquerda(p);
      } else {
        r.getPai().setDireita(p);
      }
      verificarBalanceamento(r.getPai());
    }
    r = null;
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

  private No<T> sucessor(No<T> q) {
    if (q.getDireita() != null) {
      var r = q.getDireita();
      while (r.getEsquerda() != null) {
        r = r.getEsquerda();
      }
      return r;
    } else {
      var p = q.getPai();
      while (p != null && q == p.getDireita()) {
        q = p;
        p = q.getPai();
      }
      return p;
    }
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