/**
 * Classe abstrata Conta que representa uma conta bancária.
 */
public abstract class Conta implements ContaI {
    private static int contador = 0;
    protected int numero;
    protected Cliente dono;
    protected double saldo;

    /**
     * Construtor da classe Conta.
     * @param numero Número da conta.
     * @param dono Dono da conta.
     * @param saldo Saldo inicial da conta.
     */
    public Conta(int numero, Cliente dono, double saldo) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
    }

    /**
     * Construtor da classe Conta.
     * @param dono Dono da conta.
     * @param saldo Saldo inicial da conta.
     */
    public Conta(Cliente dono, double saldo) {
        this.numero = ++contador;
        this.dono = dono;
        this.saldo = saldo;
    }

    /**
     * Deposita um valor na conta.
     * @param valor Valor a ser depositado.
     * @return Verdadeiro se o depósito foi bem sucedido, falso caso contrário.
     */
    public boolean deposita(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    /**
     * Saca um valor da conta.
     * @param valor Valor a ser sacado.
     * @return Verdadeiro se o saque foi bem sucedido, falso caso contrário.
     */
    public boolean saca(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    /**
     * Retorna o dono da conta.
     * @return Dono da conta.
     */
    public Cliente getDono() {
        return this.dono;
    }

    /**
     * Retorna o número da conta.
     * @return Número da conta.
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * Retorna o saldo da conta.
     * @return Saldo da conta.
     */
    public double getSaldo() {
        return this.saldo;
    }

    /**
     * Método abstrato para remuneração da conta.
     */
    public abstract void remunera();
}