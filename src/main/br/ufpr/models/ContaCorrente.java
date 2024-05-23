package main.br.ufpr.models;

public class ContaCorrente extends Conta {

    protected double limite;

    public ContaCorrente(int numero, Cliente dono, double saldo, double limite){
        super(numero, dono, saldo);
        this.limite = limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }


    @Override
    public boolean saca(double valor){
        if (valor > 0 && valor <= this.saldo && valor <= this.limite) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public void remunera(){
        this.saldo = this.saldo * 0.01;
    }
}
