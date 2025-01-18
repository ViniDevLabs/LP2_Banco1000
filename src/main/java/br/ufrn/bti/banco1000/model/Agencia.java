package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Agencia {
  private int id;
  private String nome;
  private ArrayList<Conta> contas = new ArrayList<>();

  public Agencia(int id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public void adicionarConta(Conta contaNova) throws IllegalArgumentException {
    if (contas.stream().noneMatch(conta -> conta.getId().equals(contaNova.getId()))
        && contaNova.getAgencia().getId() == this.id) {
      contas.add(contaNova);
    } else {
      throw new IllegalArgumentException("Conta já cadastrada na agência");
    }
  }
}
