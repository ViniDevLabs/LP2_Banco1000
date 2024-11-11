package br.ufrn.bti.banco1000.enums;

public enum TipoContaEnum {
  CORRENTE("Corrente"),
  POUPANCA("Poupança");

  private String descricao;

  TipoContaEnum(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
