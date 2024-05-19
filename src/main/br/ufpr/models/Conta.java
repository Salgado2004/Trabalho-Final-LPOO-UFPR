package main.br.ufpr.models;

public abstract class Conta implements ContaI {
    protected int numero;
    protected Cliente dono;
    protected double saldo;
    protected double limite;

    public Conta(int numero, Cliente dono, double saldo, double limite) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
        this.limite = limite;
    }


    public boolean depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= this.saldo + this.limite) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public Cliente getDono() {
        return this.dono;
    }

    public int getNumero() {
        return this.numero;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public abstract void remunera();
}
