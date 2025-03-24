package br.ufrn.bti.banco1000.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import br.ufrn.bti.banco1000.model.Conta;

public final class TransacaoUtil {

  private TransacaoUtil() {
  }

  public static void gerarTransacao(Conta conta, String tipoDeTransacao, double quantia) {
    String transacao = String.format("%s,%s,%s,%s,%s,%s", LocalDateTime.now(), conta.getUsuarioCpf(), conta.getId(),
        conta.getTipoDeConta(), tipoDeTransacao, quantia);

    try (FileWriter fileWriter = new FileWriter("src/main/resources/transacoes.csv", true);) {
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.println(transacao);
      printWriter.close();
    } catch (Exception e) {
      System.err.println("Erro ao adicionar linha no arquivo CSV: " + e.getMessage());
    }
  }

}
