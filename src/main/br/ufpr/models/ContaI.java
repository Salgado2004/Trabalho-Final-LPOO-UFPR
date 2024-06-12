package main.br.ufpr.models;

/**
 * Esta é uma interface que define os métodos que uma conta deve implementar.
 * Os métodos incluem operações básicas como depósito, saque, obtenção do proprietário, número e saldo da conta, e remuneração.
 */
public interface ContaI {
    /**
     * Este método deposita um valor na conta.
     *
     * @param valor O valor a ser depositado.
     * @return      Verdadeiro se o depósito foi bem-sucedido, falso caso contrário.
     */
    public boolean deposita(double valor);

    /**
     * Este método saca um valor da conta.
     *
     * @param valor O valor a ser sacado.
     * @return      Verdadeiro se o saque foi bem-sucedido, falso caso contrário.
     */
    public boolean saca(double valor);

    /**
     * Este método retorna o proprietário da conta.
     *
     * @return O proprietário da conta.
     */
    public Cliente getDono();

    /**
     * Este método retorna o número da conta.
     *
     * @return O número da conta.
     */
    public int getNumero();

    /**
     * Este método retorna o saldo da conta.
     *
     * @return O saldo da conta.
     */
    public double getSaldo();

    /**
     * Este é um método que deve ser implementado nas classes que implementam esta interface.
     * Ele é usado para calcular a remuneração da conta.
     */
    public void remunera();
}