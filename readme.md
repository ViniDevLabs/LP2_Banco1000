Versão 2.0:

- Adicionar agências, as quais podem gerenciar diversas contas
- Cada conta pode somente pertencer a uma agência
- Classe base Conta, novos tipos de contas:

  ContaCorrente: Taxas de manutenção

  ContaPoupanca: Rendimento mensal

  ContaSalario: Restrita a depósitos do empregador e limitado a um número fixo de saques

- Novas exceções personalizadas para tratar erros no contexto de múltiplas agências e contas.
- Salvar dados das agências, contas e transações em arquivos no formato .csv

Ordem dos valores nos arquivos .csv:

Agências: Id, nome  
Contas: Id, CPF do usuário, Saldo, Id da agência, Tipo de conta, Informação associada ao tipo de conta  
Transações: Horário da transação, CPF do usuário, Id da conta, Tipo de conta, Operação realizada, Quantia relacionada  
Usuários: Nome, CPF, Senha
