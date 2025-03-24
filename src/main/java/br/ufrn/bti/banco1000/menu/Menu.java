package br.ufrn.bti.banco1000.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.ufrn.bti.banco1000.model.Agencia;
import br.ufrn.bti.banco1000.model.Conta;
import br.ufrn.bti.banco1000.model.ContaCorrente;
import br.ufrn.bti.banco1000.model.ContaPoupanca;
import br.ufrn.bti.banco1000.model.ContaSalario;
import br.ufrn.bti.banco1000.model.Usuario;
import br.ufrn.bti.banco1000.utils.CsvUtil;

public final class Menu {
  private static Scanner scanner = new Scanner(System.in);
  private static Usuario usuarioAtual;
  private static Conta contaAtual;
  private static ArrayList<Usuario> usuarios = new ArrayList<>();
  private static ArrayList<Agencia> agencias = new ArrayList<>();

  private Menu() {
  }

  public static void iniciarPrograma() {
    carregarUsuarios();
    carregarAgencias();
    carregarContas();

    // Caso seja necessário criar as agências, usuários e contas novamente,
    // descomente o código abaixo

    // Agencia agencia1 = new Agencia(001, "Agência 1");
    // Agencia agencia2 = new Agencia(002, "Agência 2");

    // agencias.add(agencia1);
    // agencias.add(agencia2);
    // reescreverAgencias(agencias);

    // Usuario usuario1 = new Usuario("João", "12345678900", "1234");
    // Conta contaCorrente1 = new ContaCorrente(usuario1.getCpf(), 500, agencia1);
    // Conta contaPoupanca1 = new ContaPoupanca(usuario1.getCpf(), 10, agencia1);
    // Conta contaSalario1 = new ContaSalario(usuario1.getCpf(), 1500, agencia1);

    // usuario1.adicionarConta(contaCorrente1);
    // usuario1.adicionarConta(contaPoupanca1);
    // usuario1.adicionarConta(contaSalario1);

    // Usuario usuario2 = new Usuario("Maria", "98765432100", "1234");
    // Conta contaCorrente2 = new ContaCorrente(usuario2.getCpf(), 500, agencia2);
    // Conta contaPoupanca2 = new ContaPoupanca(usuario2.getCpf(), 10, agencia2);
    // Conta contaSalario2 = new ContaSalario(usuario2.getCpf(), 1500, agencia2);

    // usuario2.adicionarConta(contaCorrente2);
    // usuario2.adicionarConta(contaPoupanca2);
    // usuario2.adicionarConta(contaSalario2);

    // usuarios.add(usuario1);
    // usuarios.add(usuario2);
    // reescreverUsuarios(usuarios);
    // reescreverContas(usuarios);

    while (true) {
      if (usuarioAtual == null) {
        mostrarMenuPrincipal();
      } else if (contaAtual == null) {
        mostrarMenuUsuarioLogado();
      } else {
        mostrarMenuContaSelecionada();
      }
    }
  }

  private static void limparTerminal() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private static void mostrarMenuPrincipal() {
    System.out.println("BEM VINDO AO BANCO 1000");
    System.out.println("MENU SEM LOGIN");
    System.out.println("0 - ENCERRAR O PROGRAMA");
    System.out.println("1 - CRIAR CADASTRO");
    System.out.println("2 - FAZER LOGIN");

    boolean opcaoInvalida = true;
    while (opcaoInvalida) {
      System.out.print("Opção: ");
      int opcao;

      try {
        opcao = Integer.parseInt(scanner.nextLine());
      } catch (Exception e) {
        opcao = -1;
      }

      switch (opcao) {
        case 0 -> System.exit(0);
        case 1 -> {
          opcaoInvalida = false;
          limparTerminal();
          criarCadastro();
          break;
        }
        case 2 -> {
          opcaoInvalida = false;
          limparTerminal();
          fazerLogin();
          break;
        }
        default -> System.out.println("Opção inválida! Por favor, escolha 0, 1 ou 2.");
      }
    }
  }

  private static void criarCadastro() {
    System.out.println("CRIAR CADASTRO");

    String nome;
    String cpf;
    String senha;

    while (true) {
      System.out.print("Digite o nome: ");
      nome = scanner.nextLine();

      if (nome.length() > 0) {
        break;
      } else {
        System.out.println("O nome não deve ser vazio");
      }
    }

    while (true) {
      System.out.print("Digite o cpf: ");
      cpf = scanner.nextLine();

      if (cpf.length() > 0) {
        break;
      } else {
        System.out.println("O cpf não deve ser vazio");
      }
    }

    while (true) {
      System.out.print("Digite a senha: ");
      senha = scanner.nextLine();

      if (senha.length() > 0) {
        break;
      } else {
        System.out.println("A senha não deve ser vazio");
      }
    }

    final String valorCpf = cpf;

    if (usuarios.stream().anyMatch(usuario -> usuario.getCpf().equals(valorCpf))) {
      System.out.println("CPF já cadastrado em outro usuário. Tente novamente!");
      criarCadastro();
    } else {
      Usuario usuario = new Usuario(nome, valorCpf, senha);
      usuarios.add(usuario);
      reescreverUsuarios(usuarios);
      System.out.println("Usuário cadastrado com sucesso!\n");
    }
  }

  private static void fazerLogin() {
    System.out.println("FAZER LOGIN");

    System.out.print("Digite o cpf do usuário: ");
    String cpf = scanner.nextLine();
    System.out.print("Digite a senha: ");
    String senha = scanner.nextLine();

    for (Usuario usuario : usuarios) {
      if (usuario.getCpf().equals(cpf) && usuario.getSenha().equals(senha)) {
        usuarioAtual = usuario;
        limparTerminal();
        return;
      }
    }

    if (usuarioAtual == null) {
      System.out.println("Credenciais inválidas. Tente novamente!\n");
    }

  }

  private static void mostrarMenuUsuarioLogado() {
    System.out.println("Seja bem-vindo(a), " + usuarioAtual.getNome() + "!");
    System.out.println("MENU COM LOGIN");
    System.out.println("0 - DESLOGAR");
    System.out.println("1 - CRIAR CONTA");
    System.out.println("2 - LISTAR MINHAS CONTAS");
    System.out.println("3 - SELECIONAR CONTA");
    System.out.println("4 - LISTAR AGÊNCIAS");

    boolean opcaoInvalida = true;
    while (opcaoInvalida) {
      System.out.print("Opção: ");
      int opcao;

      try {
        opcao = Integer.parseInt(scanner.nextLine());
      } catch (Exception e) {
        opcao = -1;
      }

      switch (opcao) {
        case 0 -> {
          limparTerminal();
          opcaoInvalida = false;
          usuarioAtual = null;
          break;
        }
        case 1 -> {
          opcaoInvalida = false;
          criarConta();
          break;
        }
        case 2 -> {
          opcaoInvalida = false;
          listarContas(Optional.empty());
          break;
        }
        case 3 -> {
          opcaoInvalida = false;
          selecionarConta();
          break;
        }
        case 4 -> {
          opcaoInvalida = false;
          listarAgencias();
          break;
        }
        default -> System.out.println("Opção inválida! Por favor, escolha 0, 1 , 2 ou 3.");
      }
    }
  }

  private static void criarConta() {
    System.out.println("\nCRIAR CONTA");
    Agencia agenciaSelecionada;

    try {
      System.out.println("Escolha um índice das agências abaixo:");
      listarAgencias();

      System.out.print("Índice selecionado: ");
      int indiceSelecionado = Integer.parseInt(scanner.nextLine());
      agenciaSelecionada = agencias.get(indiceSelecionado - 1);

      System.out.print("Digite o saldo inicial: ");
      double saldo = Double.parseDouble(scanner.nextLine());

      System.out.print("Digite o tipo da conta (1 - Corrente, 2 - Poupança, 3 - Salário): ");
      int tipo = Integer.parseInt(scanner.nextLine());

      Conta contaNova;
      if (tipo == 1) {
        contaNova = new ContaCorrente(usuarioAtual.getCpf(), saldo, agenciaSelecionada);
      } else if (tipo == 2) {
        contaNova = new ContaPoupanca(usuarioAtual.getCpf(), saldo, agenciaSelecionada);
      } else if (tipo == 3) {
        contaNova = new ContaSalario(usuarioAtual.getCpf(), saldo, agenciaSelecionada);
      } else {
        System.out.println("Tipo de conta inválido.\n");
        return;
      }

      usuarioAtual.adicionarConta(contaNova);
      reescreverContas(usuarios);
      System.out.println("Conta criada com sucesso!\n");
    } catch (Exception e) {
      System.err.println("Erro em alguma operação. Tente novamente\n");
    }

  }

  private static void listarContas(Optional<String> contaId) {
    System.out.println("\nLISTAR CONTAS");
    if (usuarioAtual.getContas().isEmpty()) {
      System.out.println("Nenhuma conta encontrada.\n");
      return;
    }

    System.out.println("ÍNDICE - ID - SALDO - TIPO");
    for (int i = 0; i < usuarioAtual.getContas().size(); i++) {
      Conta conta = usuarioAtual.getContas().get(i);
      if (!(contaId.isPresent() && conta.getId().equals(contaId.get()))) {
        System.out.println(
            (i + 1) + " - " + conta.getId() + " - R$" + conta.getSaldo() + " - " + conta.getTipoDeConta());
      }

    }
    System.err.println("");
  }

  private static void selecionarConta() {
    System.out.println("\nSELECIONAR CONTA");

    if (usuarioAtual.getContas().isEmpty()) {
      System.out.println("Nenhuma conta cadastrada no nome do usuário.\n");
      return;
    }

    System.out.println("Escolha um índice das contas abaixo:");
    listarContas(Optional.empty());

    try {
      System.out.print("Índice selecionado: ");
      int indiceSelecionado = Integer.parseInt(scanner.nextLine());
      contaAtual = usuarioAtual.getContas().get(indiceSelecionado - 1);
    } catch (Exception e) {
      System.out.println("Índice inválido, tente novamente.\n");
    }
  }

  private static void listarAgencias() {
    System.out.println("\nLISTAR AGÊNCIAS");
    if (agencias.isEmpty()) {
      System.out.println("Nenhuma agência encontrada.\n");
      return;
    }

    System.out.println("ÍNDICE - ID - NOME");
    for (int i = 0; i < agencias.size(); i++) {
      Agencia agencia = agencias.get(i);
      System.out.println((i + 1) + " - " + agencia.getId() + " - " + agencia.getNome());
    }
    System.err.println("");
  }

  private static void mostrarMenuContaSelecionada() {
    System.out.println("Conta selecionada: " + contaAtual.getId() + " - Tipo: " + contaAtual.getTipoDeConta());
    System.out.println("MENU CONTA SELECIONADA");
    System.out.println("0 - DESSELECIONAR CONTA");
    System.out.println("1 - CONSULTAR SALDO");
    System.out.println("2 - DEPOSITAR");
    System.out.println("3 - REALIZAR SAQUE");
    System.out.println("4 - TRANSFERIR");
    if (contaAtual instanceof ContaCorrente) {
      System.out.println("5 - APLICAR TAXA DE MANUTENÇÃO");
    } else if (contaAtual instanceof ContaPoupanca) {
      System.out.println("5 - APLICAR RENDIMENTO MENSAL");
    } else if (contaAtual instanceof ContaSalario) {
      System.out.println("5 - CHECAR SAQUES RESTANTES");
    }

    boolean opcaoInvalida = true;
    while (opcaoInvalida) {
      System.out.print("Opção: ");
      int opcao;

      try {
        opcao = Integer.parseInt(scanner.nextLine());
      } catch (Exception e) {
        opcao = -1;
      }

      switch (opcao) {
        case 0 -> {
          limparTerminal();
          opcaoInvalida = false;
          contaAtual = null;
          break;
        }
        case 1 -> {
          opcaoInvalida = false;
          contaAtual.consultarSaldo();
          System.out.println();
          break;
        }
        case 2 -> {
          opcaoInvalida = false;
          depositarNaConta();
          break;
        }
        case 3 -> {
          opcaoInvalida = false;
          realizarSaqueNaConta();
          break;
        }
        case 4 -> {
          opcaoInvalida = false;
          realizarTransferenciaNaConta();
          break;
        }
        case 5 -> {
          if (contaAtual instanceof ContaCorrente conta) {
            opcaoInvalida = false;
            conta.aplicarTaxaManutencao();
            reescreverContas(usuarios);
          } else if (contaAtual instanceof ContaPoupanca conta) {
            opcaoInvalida = false;
            conta.aplicarRendimento();
            reescreverContas(usuarios);
          } else if (contaAtual instanceof ContaSalario conta) {
            opcaoInvalida = false;
            int saquesRestantes = conta.getSaquesRestantes();
            System.out.println("Você possui " + saquesRestantes + " saques restantes.\n");
          }
          break;
        }
        default -> System.out.println("Opção inválida! Por favor, escolha 0, 1, 2, 3, 4 ou 5.");
      }
    }
  }

  private static void depositarNaConta() {
    try {
      System.out.print("\nDigite o valor do depósito: ");
      double valor = Double.parseDouble(scanner.nextLine());
      contaAtual.depositar(valor);
      reescreverContas(usuarios);
      System.out.println("Depósito realizado com sucesso!\n");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.out.println("Erro na operação. Tente novamente.\n");
    }
  }

  private static void realizarSaqueNaConta() {
    try {
      System.out.print("\nDigite o valor do saque: ");
      double valor = Double.parseDouble(scanner.nextLine());
      contaAtual.saque(valor);
      reescreverContas(usuarios);
      System.out.println("Saque realizado com sucesso!\n");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.out.println("Erro na operação. Tente novamente.\n");
    }
  }

  private static void realizarTransferenciaNaConta() {
    if (usuarioAtual.getContas().size() == 1) {
      System.out.println("O usuário logado possui apenas uma conta, não é possível utilizar a transferência\n");
      return;
    }

    System.out.println("Escolha um índice das contas abaixo:");
    listarContas(Optional.of(contaAtual.getId()));

    try {
      System.out.print("Índice selecionado: ");
      int indiceSelecionado = Integer.parseInt(scanner.nextLine());
      Conta contaSelecionada = usuarioAtual.getContas().get(indiceSelecionado - 1);

      System.out.print("Digite o valor a ser transferido: ");
      double valor = Double.parseDouble(scanner.nextLine());
      contaAtual.transferencia(contaSelecionada, valor);
      reescreverContas(usuarios);

      System.out.println("Transferência realizada com sucesso para a conta " + contaSelecionada.getId() + "\n");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.out.println("Erro na operação, tente novamente.\n");
    }
  }

  private static void carregarUsuarios() {
    for (String linha : CsvUtil.carregarArquivo("src/main/resources/usuarios.csv")) {
      usuarios.add(Usuario.fromCsv(linha));
    }
  }

  private static void carregarAgencias() {
    for (String linha : CsvUtil.carregarArquivo("src/main/resources/agencias.csv")) {
      agencias.add(Agencia.fromCsv(linha));
    }
  }

  private static void carregarContas() {
    for (String linha : CsvUtil.carregarArquivo("src/main/resources/contas.csv")) {
      Conta conta = Conta.fromCsv(linha, agencias);
      Optional<Usuario> usuario = usuarios.stream()
          .filter(u -> u.getCpf().equals(conta.getUsuarioCpf()))
          .findFirst();
      usuario.ifPresent(u -> u.adicionarConta(conta));
    }
  }

  private static void reescreverUsuarios(List<Usuario> usuarios) {
    List<String> linhas = usuarios.stream()
        .map(Usuario::toCsv)
        .toList();

    CsvUtil.salvarArquivo("src/main/resources/usuarios.csv", linhas);
  }

  private static void reescreverAgencias(List<Agencia> agencias) {
    List<String> linhas = agencias.stream()
        .map(Agencia::toCsv)
        .toList();
    CsvUtil.salvarArquivo("src/main/resources/agencias.csv", linhas);
  }

  private static void reescreverContas(List<Usuario> usuarios) {
    List<Conta> contas = new ArrayList<>();
    for (Usuario usuario : usuarios) {
      contas.addAll(usuario.getContas());
    }

    List<String> linhas = contas.stream()
        .map(Conta::toCsv)
        .toList();
    CsvUtil.salvarArquivo("src/main/resources/contas.csv", linhas);
  }

}
