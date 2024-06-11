/**
 * Classe ContaCorrente que representa uma conta corrente bancária.
 */
public class ContaCorrente extends Conta {
    private final double depositoInicial;
    private double limite;

    /**
     * Construtor da classe ContaCorrente.
     * @param numero Número da conta.
     * @param dono Dono da conta.
     * @param saldo Saldo inicial da conta.
     * @param limite Limite de saque da conta.
     */
    public ContaCorrente(int numero, Cliente dono, double saldo, double limite) {
        super(numero, dono, saldo);
        this.depositoInicial = saldo;
        this.limite = limite;
    }

    /**
     * Construtor da classe ContaCorrente.
     * @param dono Dono da conta.
     * @param saldo Saldo inicial da conta.
     * @param limite Limite de saque da conta.
     */
    public ContaCorrente(Cliente dono, double saldo, double limite) {
        super(dono, saldo);
        this.depositoInicial = saldo;
        this.limite = limite;
    }

    /**
     * Retorna o limite de saque da conta.
     * @return Limite de saque da conta.
     */
    public double getLimite() {
        return this.limite;
    }

    /**
     * Define o limite de saque da conta.
     * @param limite Limite de saque da conta.
     */
    public void setLimite(double limite) {
        this.limite = limite;
    }

    /**
     * Retorna o depósito inicial da conta.
     * @return Depósito inicial da conta.
     */
    public double getDepositoInicial() {
        return this.depositoInicial;
    }

    /**
     * Saca um valor da conta, considerando o limite.
     * @param valor Valor a ser sacado.
     * @return Verdadeiro se o saque foi bem sucedido, falso caso contrário.
     */
    @Override
    public boolean saca(double valor) {
        if (valor > 0 && valor <= this.saldo + this.limite) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    /**
     * Remunera a conta com 1% do saldo.
     */
    public void remunera() {
        this.saldo += this.saldo * 0.01;
    }
}