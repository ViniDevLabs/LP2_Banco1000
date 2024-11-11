package br.ufrn.bti.banco1000.menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import br.ufrn.bti.banco1000.enums.TipoContaEnum;
import br.ufrn.bti.banco1000.model.Conta;
import br.ufrn.bti.banco1000.model.Usuario;

public final class Menu {
  private static Scanner scanner = new Scanner(System.in);
  private static Usuario usuarioAtual;
  private static Conta contaAtual;
  private static ArrayList<Usuario> usuarios = new ArrayList<>();

  private Menu() {
  }

  public static void iniciarPrograma() {
    Usuario usuario1 = new Usuario("João", "12345678900", "1234");
    Conta contaCorrente1 = new Conta(usuario1.getCpf(), 10, TipoContaEnum.CORRENTE);
    Conta contaPoupanca1 = new Conta(usuario1.getCpf(), 500, TipoContaEnum.POUPANCA);
    usuario1.adicionarConta(contaCorrente1);
    usuario1.adicionarConta(contaPoupanca1);

    Usuario usuario2 = new Usuario("Maria", "98765432100", "1234");
    Conta contaCorrente2 = new Conta(usuario2.getCpf(), 10, TipoContaEnum.CORRENTE);
    Conta contaPoupanca2 = new Conta(usuario2.getCpf(), 500, TipoContaEnum.POUPANCA);
    usuario2.adicionarConta(contaCorrente2);
    usuario2.adicionarConta(contaPoupanca2);

    usuarios.add(usuario1);
    usuarios.add(usuario2);

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
    System.out.println("Seja bem-vindo, " + usuarioAtual.getNome() + "!");
    System.out.println("MENU COM LOGIN");
    System.out.println("0 - DESLOGAR");
    System.out.println("1 - CRIAR CONTA");
    System.out.println("2 - LISTAR MINHAS CONTAS");
    System.out.println("3 - SELECIONAR CONTA");

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
        default -> System.out.println("Opção inválida! Por favor, escolha 0, 1 , 2 ou 3.");
      }
    }
  }

  private static void criarConta() {
    System.out.println("\nCRIAR CONTA");

    try {
      System.out.print("Digite o saldo inicial: ");
      double saldo = Double.parseDouble(scanner.nextLine());

      System.out.print("Digite o tipo da conta (1 - Corrente, 2 - Poupança): ");
      int tipo = Integer.parseInt(scanner.nextLine());

      TipoContaEnum tipoConta;
      if (tipo == 1) {
        tipoConta = TipoContaEnum.CORRENTE;
      } else if (tipo == 2) {
        tipoConta = TipoContaEnum.POUPANCA;
      } else {
        System.out.println("Tipo de conta inválido.\n");
        return;
      }

      usuarioAtual.adicionarConta(new Conta(usuarioAtual.getCpf(), saldo, tipoConta));
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
            (i + 1) + " - " + conta.getId() + " - R$" + conta.getSaldo() + " - " + conta.getTipoConta());
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

  private static void mostrarMenuContaSelecionada() {
    System.out.println("Conta selecionada: " + contaAtual.getId() + " - Tipo: " + contaAtual.getTipoConta());
    System.out.println("MENU CONTA SELECIONADA");
    System.out.println("0 - DESSELECIONAR CONTA");
    System.out.println("1 - CONSULTAR SALDO");
    System.out.println("2 - DEPOSITAR");
    System.out.println("3 - REALIZAR SAQUE");
    System.out.println("4 - TRANSFERIR");

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
        default -> System.out.println("Opção inválida! Por favor, escolha 0, 1, 2, 3 ou 4.");
      }
    }
  }

  private static void depositarNaConta() {
    try {
      System.out.print("\nDigite o valor do depósito: ");
      double valor = Double.parseDouble(scanner.nextLine());
      contaAtual.depositar(valor);
      System.out.println("Depósito realizado com sucesso!\n");
    } catch (Exception e) {
      System.out.println("Erro na operação. Tente novamente.\n");
    }
  }

  private static void realizarSaqueNaConta() {
    try {
      System.out.print("\nDigite o valor do saque: ");
      double valor = Double.parseDouble(scanner.nextLine());
      contaAtual.saque(valor);
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

      System.out.println("Transferência realizada com sucesso para a conta " + contaSelecionada.getId() + "\n");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.out.println("Erro na operação, tente novamente.\n");
    }
  }
}
