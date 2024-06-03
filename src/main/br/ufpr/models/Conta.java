package main.br.ufpr.models;

public abstract class Conta implements ContaI {
    private static int contador = 0;
    protected int numero;
    protected Cliente dono;
    protected double saldo;

    public Conta(int numero, Cliente dono, double saldo) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
    }

    public Conta(Cliente dono, double saldo) {
        this.numero = ++contador;
        this.dono = dono;
        this.saldo = saldo;
    }


    public boolean deposita(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean saca(double valor) {
        if (valor > 0 && valor <= this.saldo) {
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
