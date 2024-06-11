/**
 * Interface ContaI que define os métodos que uma conta bancária deve implementar.
 */
public interface ContaI {
    /**
     * Deposita um valor na conta.
     * @param valor Valor a ser depositado.
     * @return Verdadeiro se o depósito foi bem sucedido, falso caso contrário.
     */
    public boolean deposita(double valor);

    /**
     * Saca um valor da conta.
     * @param valor Valor a ser sacado.
     * @return Verdadeiro se o saque foi bem sucedido, falso caso contrário.
     */
    public boolean saca(double valor);

    /**
     * Retorna o dono da conta.
     * @return Dono da conta.
     */
    public Cliente getDono();

    /**
     * Retorna o número da conta.
     * @return Número da conta.
     */
    public int getNumero();

    /**
     * Retorna o saldo da conta.
     * @return Saldo da conta.
     */
    public double getSaldo();

    /**
     * Método para remuneração da conta.
     */
    public void remunera();
}