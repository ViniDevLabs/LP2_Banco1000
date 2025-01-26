package br.ufrn.bti.banco1000.model;

import java.util.List;

import br.ufrn.bti.banco1000.utils.TransacaoUtil;
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
        TransacaoUtil.gerarTransacao(this, "Depósito", quantia);
        return saldo;
    }

    public double depositar(double quantia, boolean deveGerarTransacao) throws IllegalArgumentException {
        if (quantia <= 0) {
            throw new IllegalArgumentException("O depósito deve ser de uma quantia positiva!");
        }

        this.saldo += quantia;
        if (deveGerarTransacao) {
            TransacaoUtil.gerarTransacao(this, "Depósito", quantia);
        }
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
        TransacaoUtil.gerarTransacao(this, "Saque", quantia);
        return this.saldo;
    }

    public double saque(double quantia, boolean deveGerarTransacao) throws IllegalArgumentException {
        if (quantia <= 0) {
            throw new IllegalArgumentException("O saque deve ser de uma quantia positiva!");
        }

        if (quantia > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para saque!");
        }

        this.saldo -= quantia;
        if (deveGerarTransacao) {
            TransacaoUtil.gerarTransacao(this, "Saque", quantia);
        }
        return this.saldo;
    }

    public void transferencia(Conta contaAReceber, double quantia) throws IllegalArgumentException {
        if (!this.usuarioCpf.equals(contaAReceber.getUsuarioCpf())) {
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

        contaAReceber.depositar(quantia, false);
        this.saldo -= quantia;
        TransacaoUtil.gerarTransacao(this, "Transferência para ID " + contaAReceber.getId(), quantia);
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s", id, usuarioCpf, saldo, agencia.getId(), tipoDeConta);
    }

    public static Conta fromCsv(String linha, List<Agencia> agencias) {
        String[] campos = linha.split(",");
        int agenciaId = Integer.parseInt(campos[3]);
        Agencia agencia = agencias.stream().filter(a -> a.getId() == agenciaId).findFirst().orElse(null);

        if (campos[4].equals("Conta corrente")) {
            return new ContaCorrente(campos[1], Double.parseDouble(campos[2]), agencia, Double.parseDouble(campos[5]));
        } else if (campos[4].equals("Conta poupança")) {
            return new ContaPoupanca(campos[1], Double.parseDouble(campos[2]), agencia, Double.parseDouble(campos[5]));
        } else if (campos[4].equals("Conta salário")) {
            return new ContaSalario(campos[1], Double.parseDouble(campos[2]), agencia, Integer.parseInt(campos[5]));
        } else {
            return null;
        }
    }
}
