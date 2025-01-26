package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Usuario {
    private String nome;
    private String cpf;
    private String senha;
    private ArrayList<Conta> contas = new ArrayList<>();

    public Usuario(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public void adicionarConta(Conta contaNova) {
        if (contas.stream().noneMatch(conta -> conta.getId().equals(contaNova.getId()))) {
            contas.add(contaNova);
        }
    }

    public String toCsv() {
        return String.format("%s,%s,%s", nome, cpf, senha);
    }

    public static Usuario fromCsv(String linha) {
        String[] campos = linha.split(",");
        return new Usuario(campos[0], campos[1], campos[2]);
    }
}
