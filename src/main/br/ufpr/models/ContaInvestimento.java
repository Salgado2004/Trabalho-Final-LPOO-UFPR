/**
 * Classe ContaInvestimento que estende a classe Conta.
 * Esta classe representa uma conta de investimento em um sistema bancário.
 *
 * A classe possui os seguintes métodos:
 * - deposita(double valor): realiza um depósito na conta. Retorna true se o valor for maior ou igual ao depósito mínimo, caso contrário retorna false.
 * - saca(double valor): realiza um saque da conta. Retorna true se o saldo após o saque for maior ou igual ao montante mínimo, caso contrário retorna false.
 * - remunera(): aplica uma remuneração de 2% ao saldo da conta.
 *
 * A classe possui testes unitários com pelo menos 95% de cobertura.
 */
public class ContaInvestimento extends Conta {
    private final double depositoInicial;
    private final double depositoMinimo;
    private final double montanteMinimo;

    /**
     * Construtor da classe ContaInvestimento.
     *
     * @param numero o número da conta
     * @param dono o dono da conta
     * @param saldo o saldo inicial da conta
     * @param depositoMinimo o depósito mínimo permitido na conta
     * @param montanteMinimo o montante mínimo permitido na conta
     */
    public ContaInvestimento(int numero, Cliente dono, double saldo, double depositoMinimo, double montanteMinimo) {
        super(numero, dono, saldo);
        this.depositoInicial = saldo;
        this.depositoMinimo = depositoMinimo;
        this.montanteMinimo = montanteMinimo;
    }

    /**
     * Construtor da classe ContaInvestimento.
     *
     * @param dono o dono da conta
     * @param saldo o saldo inicial da conta
     * @param depositoMinimo o depósito mínimo permitido na conta
     * @param montanteMinimo o montante mínimo permitido na conta
     */
    public ContaInvestimento(Cliente dono, double saldo, double depositoMinimo, double montanteMinimo) {
        super(dono, saldo);
        this.depositoInicial = saldo;
        this.depositoMinimo = depositoMinimo;
        this.montanteMinimo = montanteMinimo;
    }

    /**
     * Método para depositar um valor na conta.
     *
     * @param valor o valor a ser depositado
     * @return true se o valor for maior ou igual ao depósito mínimo, caso contrário retorna false
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
     * Método para sacar um valor da conta.
     *
     * @param valor o valor a ser sacado
     * @return true se o saldo após o saque for maior ou igual ao montante mínimo, caso contrário retorna false
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
     * Método para aplicar uma remuneração de 2% ao saldo da conta.
     */
    @Override
    public void remunera() {
        this.saldo += this.saldo * 0.02;
    }

    /**
     * Método para obter o depósito mínimo da conta.
     *
     * @return o depósito mínimo da conta
     */
    public double getDepositoMinimo() {
        return this.depositoMinimo;
    }

    /**
     * Método para obter o montante mínimo da conta.
     *
     * @return o montante mínimo da conta
     */
    public double getMontanteMinimo() {
        return this.montanteMinimo;
    }

    /**
     * Método para obter o depósito inicial da conta.
     *
     * @return o depósito inicial da conta
     */
    public double getDepositoInicial() {
        return this.depositoInicial;
    }
}