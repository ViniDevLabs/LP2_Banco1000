package br.ufrn.bti.banco1000.model;

import br.ufrn.bti.banco1000.utils.TransacaoUtil;

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
    depositar(rendimento, false);
    TransacaoUtil.gerarTransacao(this, "Rendimento da conta poupança", rendimento);
    System.out.println("Rendimento de R$" + rendimento + " aplicado com sucesso!\n");
  }

  @Override
  public String toCsv() {
    return String.format("%s,%s,%s,%s,%s,%s", this.getId(), this.getUsuarioCpf(), this.getSaldo(),
        this.getAgencia().getId(), this.getTipoDeConta(), this.getRendimentoMensal());
  }
}
