package br.ufpr.lpoo.models;

/**
 * Esta classe representa uma Conta de Investimento no sistema.
 * É uma subclasse da classe Conta e inclui informações adicionais como depósito inicial, depósito mínimo e montante mínimo.
 */
public class ContaInvestimento extends Conta {
    private final double depositoInicial;
    private final double depositoMinimo;
    private final double montanteMinimo;

    /**
     * Construtor para a classe ContaInvestimento.
     * Define os valores iniciais para as variáveis de instância.
     *
     * @param numero          O número da conta.
     * @param dono            O proprietário da conta.
     * @param saldo           O saldo inicial da conta.
     * @param depositoMinimo  O depósito mínimo da conta.
     * @param montanteMinimo  O montante mínimo da conta.
     */
    public ContaInvestimento(int numero, Cliente dono, double saldo, double depositoMinimo, double montanteMinimo) {
        super(numero, dono, saldo);
        this.depositoInicial = saldo;
        this.depositoMinimo = depositoMinimo;
        this.montanteMinimo = montanteMinimo;
    }

    /**
     * Construtor para a classe ContaInvestimento.
     * Define os valores iniciais para as variáveis de instância.
     * O número da conta é incrementado automaticamente.
     *
     * @param dono            O proprietário da conta.
     * @param saldo           O saldo inicial da conta.
     * @param depositoMinimo  O depósito mínimo da conta.
     * @param montanteMinimo  O montante mínimo da conta.
     */
    public ContaInvestimento(Cliente dono, double saldo, double depositoMinimo, double montanteMinimo) {
        super(dono, saldo);
        this.depositoInicial = saldo;
        this.depositoMinimo = depositoMinimo;
        this.montanteMinimo = montanteMinimo;
    }

    /**
     * Este método deposita um valor na conta.
     * Verifica se o valor é maior ou igual ao depósito mínimo antes de depositar.
     *
     * @param valor O valor a ser depositado.
     * @return      Verdadeiro se o depósito foi bem-sucedido, falso caso contrário.
     */
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

    /**
     * Este método saca um valor da conta.
     * Verifica se o novo valor do saldo (considerando o saque) é maior ou igual ao montante mínimo antes de sacar.
     *
     * @param valor O valor a ser sacado.
     * @return      Verdadeiro se o saque foi bem-sucedido, falso caso contrário.
     */
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

    /**
     * Este método calcula a remuneração da conta.
     * Adiciona 2% do saldo atual ao saldo.
     */
    @Override
    public void remunera() {

        this.saldo += this.saldo * 0.02;
        support.firePropertyChange("saldo", null, this.saldo);
    }

    // Os getters para as variáveis de instância estão aqui.

    /**
     * Este método retorna o depósito mínimo da conta.
     *
     * @return O depósito mínimo da conta.
     */
    public double getDepositoMinimo() {
        return this.depositoMinimo;
    }

    /**
     * Este método retorna o montante mínimo da conta.
     *
     * @return O montante mínimo da conta.
     */
    public double getMontanteMinimo() {
        return this.montanteMinimo;
    }

    /**
     * Este método retorna o depósito inicial da conta.
     *
     * @return O depósito inicial da conta.
     */
    public double getDepositoInicial() {
        return this.depositoInicial;
    }
}