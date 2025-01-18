package br.ufrn.bti.banco1000.model;

import lombok.Data;

@Data
public class Conta {
    private static long contador = 1;
    private String id;
    private String usuarioCpf;
    private double saldo;
    private Agencia agencia;
    private String tipoDeConta;

    public Conta(String usuarioCpf, double saldo, Agencia agencia, String tipoDeConta) {
        this.id = agencia.getId() + "-" + String.format("%05d", contador++);
        this.usuarioCpf = usuarioCpf;
        this.saldo = saldo;
        this.agencia = agencia;
        this.agencia.adicionarConta(this);
        this.tipoDeConta = tipoDeConta;
    }

    public double consultarSaldo() {
        System.out.println("Seu saldo é de R$" + saldo);
        return saldo;
    }

    public double depositar(double quantia) throws IllegalArgumentException {
        if (quantia <= 0) {
            throw new IllegalArgumentException("O depósito deve ser de uma quantia positiva!");
        }

        this.saldo += quantia;
        return saldo;
    }

    public double saque(double quantia) throws IllegalArgumentException {
        if (quantia <= 0) {
            throw new IllegalArgumentException("O saque deve ser de uma quantia positiva!");
        }

        if (quantia > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para saque!");
        }

        this.saldo -= quantia;
        return this.saldo;
    }

    public void transferencia(Conta contaAReceber, double quantia) throws IllegalArgumentException {
        if (this.usuarioCpf != contaAReceber.getUsuarioCpf()) {
            throw new IllegalArgumentException(
                    "A conta a receber a transferência deve pertencer ao mesmo usuário cadastrado no sistema.");
        }

        if (this.id.equals(contaAReceber.getId())) {
            throw new IllegalArgumentException("Não é possível transferir para a própria conta");
        }

        if (quantia > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para transferência");
        }

        if (quantia <= 0) {
            throw new IllegalArgumentException("O depósito deve ser de uma quantia positiva!");
        }

        this.saldo -= quantia;
        contaAReceber.depositar(quantia);
    }
}
