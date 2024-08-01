package main.br.ufpr.models;

/**
 * Esta classe representa uma Conta Corrente no sistema.
 * É uma subclasse da classe Conta e inclui informações adicionais como depósito inicial e limite.
 */
public class ContaCorrente extends Conta {
    private final double depositoInicial;
    private double limite;

    /**
     * Construtor para a classe ContaCorrente.
     * Define os valores iniciais para as variáveis de instância.
     *
     * @param numero          O número da conta.
     * @param dono            O proprietário da conta.
     * @param saldo           O saldo inicial da conta.
     * @param limite          O limite da conta.
     */
    public ContaCorrente(int numero, Cliente dono, double saldo, double limite) {
        super(numero, dono, saldo);
        this.depositoInicial = saldo;
        this.limite = limite;
    }

    /**
     * Construtor para a classe ContaCorrente.
     * Define os valores iniciais para as variáveis de instância.
     * O número da conta é incrementado automaticamente.
     *
     * @param dono            O proprietário da conta.
     * @param saldo           O saldo inicial da conta.
     * @param limite          O limite da conta.
     */
    public ContaCorrente(Cliente dono, double saldo, double limite) {
        super(dono, saldo);
        this.depositoInicial = saldo;
        this.limite = limite;
    }

    public double getLimite() {
        return this.limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getDepositoInicial() {
        return this.depositoInicial;
    }

    /**
     * Este método saca um valor da conta.
     * Verifica se o valor é maior que 0 e menor ou igual ao saldo mais o limite antes de sacar.
     *
     * @param valor O valor a ser sacado.
     * @return      Verdadeiro se o saque foi bem-sucedido, falso caso contrário.
     */
    @Override
    public boolean saca(double valor) {
        if (valor > 0 && valor <= this.saldo + this.limite) {
            this.saldo -= valor;
            support.firePropertyChange("saldo", null, this.saldo);
            return true;
        }
        return false;
    }

    /**
     * Este método calcula a remuneração da conta.
     * Adiciona 1% do saldo atual ao saldo.
     */
    public void remunera() {
        this.saldo += this.saldo * 0.01;
        support.firePropertyChange("saldo", null, this.saldo);
    }
}
