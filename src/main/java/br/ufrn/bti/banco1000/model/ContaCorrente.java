package br.ufrn.bti.banco1000.model;

import br.ufrn.bti.banco1000.utils.TransacaoUtil;

public class ContaCorrente extends Conta {
  private double taxaManutencao;

  public ContaCorrente(String usuarioCpf, double saldo, Agencia agencia, double taxaManutencao) {
    super(usuarioCpf, saldo, agencia, "Conta corrente");
    this.taxaManutencao = taxaManutencao;
  }

  public ContaCorrente(String usuarioCpf, double saldo, Agencia agencia) {
    this(usuarioCpf, saldo, agencia, 25.0);
  }

  public double getTaxaManutencao() {
    return taxaManutencao;
  }

  public void setTaxaManutencao(double taxaManutencao) {
    this.taxaManutencao = taxaManutencao;
  }

  public void aplicarTaxaManutencao() throws IllegalArgumentException {
    if (getSaldo() < taxaManutencao) {
      throw new IllegalArgumentException("Saldo insuficiente para aplicar a taxa de manutenção!");
    }

    saque(taxaManutencao, false);
    TransacaoUtil.gerarTransacao(this, "Pagamento da taxa de manutenção", taxaManutencao);
    System.out.println("Taxa de manutenção de R$" + taxaManutencao + " aplicada com sucesso.");
  }

  @Override
  public String toCsv() {
    return String.format("%s,%s,%s,%s,%s,%s", this.getId(), this.getUsuarioCpf(), this.getSaldo(),
        this.getAgencia().getId(), this.getTipoDeConta(), this.getTaxaManutencao());
  }
}
