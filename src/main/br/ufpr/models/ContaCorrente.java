package main.br.ufpr.models;

public class ContaCorrente extends Conta {
    private final double depositoInicial;
    private double limite;

    public ContaCorrente(int numero, Cliente dono, double saldo, double limite) {
        super(numero, dono, saldo);
        this.depositoInicial = saldo;
        this.limite = limite;
    }

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

    @Override
    public boolean saca(double valor) {
        if (valor > 0 && valor <= this.saldo + this.limite) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public void remunera() {
        this.saldo += this.saldo * 0.01;
    }
}
