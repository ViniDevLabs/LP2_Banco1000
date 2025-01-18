package br.ufrn.bti.banco1000.model;

public class ContaSalario extends Conta {
  private int limiteSaques;
  private int saquesRealizados;

  public ContaSalario(String usuarioCpf, double saldo, Agencia agencia, int limiteSaques) {
    super(usuarioCpf, saldo, agencia, "Conta salário");
    this.limiteSaques = limiteSaques;
    this.saquesRealizados = 0;
  }

  public ContaSalario(String usuarioCpf, double saldo, Agencia agencia) {
    super(usuarioCpf, saldo, agencia, "Conta salário");
    this.limiteSaques = 5;
    this.saquesRealizados = 0;
  }

  @Override
  public double depositar(double quantia) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Depósitos só podem ser feitos pelo empregador.");
  }

  @Override
  public double saque(double quantia) throws IllegalArgumentException {
    if (saquesRealizados >= limiteSaques) {
      throw new IllegalArgumentException("Limite de saques mensais atingido!");
    }
    double saldoAtual = super.saque(quantia);
    saquesRealizados++;
    System.out.println("Você possui " + (limiteSaques - saquesRealizados) + " saque(s) restantes.");
    return saldoAtual;
  }

  public int getLimiteSaques() {
    return limiteSaques;
  }

  public void setLimiteSaques(int limiteSaques) {
    this.limiteSaques = limiteSaques;
  }

  public int getSaquesRealizados() {
    return saquesRealizados;
  }

  public void resetarSaquesMensais() {
    this.saquesRealizados = 0;
  }

  public int getSaquesRestantes() {
    return limiteSaques - saquesRealizados;
  }
}
