package br.ufrn.bti.banco1000.model;

public class ContaPoupanca extends Conta {
  private double rendimentoMensal;

  public ContaPoupanca(String usuarioCpf, double saldo, Agencia agencia, double rendimentoMensal) {
    super(usuarioCpf, saldo, agencia, "Conta poupança");
    this.rendimentoMensal = rendimentoMensal;
  }

  public ContaPoupanca(String usuarioCpf, double saldo, Agencia agencia) {
    super(usuarioCpf, saldo, agencia, "Conta poupança");
    this.rendimentoMensal = 0.5;
  }

  public double getRendimentoMensal() {
    return rendimentoMensal;
  }

  public void setRendimentoMensal(double rendimentoMensal) {
    this.rendimentoMensal = rendimentoMensal;
  }

  public void aplicarRendimento() {
    double rendimento = getSaldo() * (rendimentoMensal / 100);
    depositar(rendimento);
    System.out.println("Rendimento de R$" + rendimento + " aplicado com sucesso!\n");
  }
}
