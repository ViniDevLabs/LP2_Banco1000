package br.ufrn.bti.banco1000.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public final class CsvUtil {

  private CsvUtil() {
  }

  public static void salvarArquivo(String caminho, List<String> linhas) {
    try {
      Files.write(Paths.get(caminho), linhas);
    } catch (Exception e) {
      System.err.println("Erro ao salvar arquivo CSV: " + e.getMessage());
    }
  }

  public static List<String> carregarArquivo(String caminho) {
    Path path = Paths.get(caminho);

    try {
      if (!Files.exists(path)) {
        Files.createFile(path);
        return Collections.emptyList();
      }

      return Files.readAllLines(path);

    } catch (Exception e) {
      System.err.println("Erro ao carregar arquivo CSV: " + e.getMessage());
      return Collections.emptyList();
    }
  }
}
