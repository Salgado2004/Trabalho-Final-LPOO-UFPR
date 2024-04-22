# Especificação do trabalho
### Trabalho prático POO e Swing
Um banco resolveu aprimorar os seus produtos financeiros, e por conta disso vai alterar o sistema que gerencia as contas de seus clientes. O novo sistema precisa ser em Java com interface SWING e seguindo o paradigma orientado a objetos. O sistema terá os seguintes requisitos

**Equipes de 4 pessoas**
### Requisitos
**Telas**
| Manter Cliente |
|---|
| Deve ser possível listar todos os clientes (Use _AbstractTableModel_) |
| Deve ser possível atualizar os dados de um cliente.|
| Deve ser possível excluir um cliente. Quando isso acontecer uma tela deve ser mostrada para confirmar o procedimento.| 
| Deve-se avisar que todas as contas do cliente serão apagadas. Todas as contas vinculadas este cliente devem ser apagadas. |
| Deve ser possível listar os clientes por nome (ou parte do nome), por sobrenome (ou parte), por RG ou por CPF. |
| Deve ser possível ordenar a lista por nome, sobrenome(ordem alfabética) ou salário (do maior para o menor). Implemente a interface Comparable.|

| Vincular Cliente à conta |
|---|
| Deve ser possível selecionar um cliente |
| Os tipos de conta vinculados ao cliente podem ser selecionadas de uma combobox. Os tipos de conta serão: `Conta Corrente` e `Conta Investimento` |
| Uma vez selecionado o cliente e o tipo de conta a tela deve apresentar os campos para criação da conta de acordo com a `RN1` |

| Manipular conta |
|---|
| Deve ser possível selecionar a conta de um cliente pelo CPF. |
| Deve mostrar as opções: `Saque`, `Depósito`, `Ver Saldo` e `Remunera` |

**Regra de Negócio**

`RN1`
| Se o tipo de conta selecionada for Conta Corrente | Se o tipo da conta selecionada for Conta Investimento |
|---|---|
| 1. Depósito Inicial (valor em R$) | 1. Montante Mínimo |
|  2. Limite (valor em R$) | 2. Depósito Mínimo |
|  3. O número da conta gerado automaticamente pelo sistema. | 3. Depósito Inicial |

**Classes**

`Conta`
- `public boolean deposita(double valor)`: o valor depositado deve ser positivo. Caso contrário o método retorna false
- `public boolean saca(double valor)`: o valor sacado deve ser positivo. Caso contrário o método retorna false. Mostrar mensagem na tela informando usuário.

`ContaCorrente`
- `public boolean saca(double valor)`: Antes de efetuar o saque deve-se verificar se o valor sacado não ultrapassa o limite da conta. Ou seja, a conta poderá ficar negativa até o limite estipulado na sua criação. Mostrar mensagem na tela informando o usuário.
- `public void remunera()`: Aplicar remuneração de 1% ao saldo da conta.

`ContaInvestimento`
- `public boolean deposita(double valor)`: Recebe como parâmetro o valor a ser depositado. Se o valor a ser depositado for maior ou igual ao depositoMinimo então, o depósito deve ser efetuado. Para isso chame o método deposita da classe pai (Conta) e retorne true. Caso contrário, deve-se retornar false. Mostrar mensagem na tela informando usuário.
- `public boolean saca(double valor)`: Recebe como parâmetro o valor a ser sacado. Se o novo valor do saldo (considerando o saque) for maior ou igual ao montanteMinimo, o saque deve ser efetuado. Para isso invoque o método saque da classe pai (Conta) e retorne true. Caso contrário, deve-se retornar false. Mostrar mensagem na tela informando usuário.
- `public void remunera()`: Aplicar remuneração de 2% ao saldo da conta.

**Interface**
```
public interface ContaI {
  public boolean deposita(double valor);
  public boolean saca(double valor);
  public Cliente getDono();
  public int getNumero();
  public double getSaldo();
  public void remunera();
}
```
