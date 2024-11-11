package br.ufrn.bti.banco1000.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Movimentacao {
    private LocalDateTime data;
    private String tipo;
    private Usuario usuario;
    private String descricao;
    private double valor;

    public Movimentacao(String tipo, Usuario usuario, String descricao, double valor) {
        this.data = LocalDateTime.now();
        this.tipo = tipo;
        this.usuario = usuario;
        this.descricao = descricao;
        this.valor = valor;
    }
}
