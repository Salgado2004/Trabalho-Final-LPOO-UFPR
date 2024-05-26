
package main.br.ufpr.models;
/* Criar uma classe ContaInvestimento

 A classe deve herdar a super Conta
 A classe deve ter os métodos deposita(), saca() e remunera() conforme R1
 A classe deve ter testes unitários, com pelo menos 95% de cobertura
R1
public boolean deposita(double valor): Recebe como parâmetro o valor a ser depositado. Se o valor a ser depositado for maior ou igual ao depositoMinimo então, o depósito deve ser efetuado. Para isso chame o método deposita da classe pai (Conta) e retorne true. Caso contrário, deve-se retornar false. Mostrar mensagem na tela informando usuário.

public boolean saca(double valor): Recebe como parâmetro o valor a ser sacado. Se o novo valor do saldo (considerando o saque) for maior ou igual ao montanteMinimo, o saque deve ser efetuado. Para isso invoque o método saque da classe pai (Conta) e retorne true. Caso contrário, deve-se retornar false. Mostrar mensagem na tela informando usuário.
public void remunera(): Aplicar remuneração de 2% ao saldo da conta. */
public class ContaInvestimento extends Conta{
    private double depositoMinimo;
    private double montanteMinimo;
    public ContaInvestimento(int numero, Cliente dono, double saldo, double depositoMinimo, double montanteMinimo) {
        super(numero, dono, saldo);
        this.depositoMinimo = depositoMinimo;
        this.montanteMinimo = montanteMinimo;
    }

    @Override
    public boolean deposita(double valor) {
        try {
            if (valor >= this.depositoMinimo) {
                super.deposita(valor);
                return true;
            }
            throw new Exception("Valor de depósito menor que o mínimo");
        } catch (Exception e) {
            System.out.println("Erro ao depositar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean saca(double valor) {
        try {
            if (this.saldo - valor >= this.montanteMinimo) {
                super.saca(valor);
                return true;
            }
            throw new Exception("Saldo insuficiente");
        } catch (Exception e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
            return false;
        }
    }
    

    @Override
    public void remunera() {
        this.saldo += this.saldo * 0.02;
    }

}
