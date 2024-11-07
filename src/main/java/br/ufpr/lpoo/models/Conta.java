package br.ufpr.lpoo.models;

import br.ufpr.lpoo.utils.Observer;

import java.beans.PropertyChangeSupport;

/**
 * Esta é uma classe abstrata que representa uma conta no sistema.
 * Inclui informações como número da conta, proprietário (cliente) e saldo.
 * Implementa a interface ContaI.
 */
public abstract class Conta implements ContaI {
    private static int contador = 0;
    protected int numero;
    protected Cliente dono;
    protected double saldo;
    PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Construtor para a classe Conta.
     * Define os valores iniciais para as variáveis de instância.
     *
     * @param numero O número da conta.
     * @param dono   O proprietário da conta.
     * @param saldo  O saldo inicial da conta.
     */
    public Conta(int numero, Cliente dono, double saldo) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
    }

    /**
     * Construtor para a classe Conta.
     * Define os valores iniciais para as variáveis de instância.
     * O número da conta é incrementado automaticamente.
     *
     * @param dono  O proprietário da conta.
     * @param saldo O saldo inicial da conta.
     */
    public Conta(Cliente dono, double saldo) {
        this.numero = ++contador;
        this.dono = dono;
        this.saldo = saldo;
    }

    /**
     * Este método adiciona um observador à conta.
     *
     * @param observer O observador a ser adicionado.
     */
    public void addObserver(Observer observer) {
        this.support.addPropertyChangeListener(observer);
    }

    /**
     * Este método deposita um valor na conta.
     * Verifica se o valor é maior que 0 antes de depositar.
     *
     * @param valor O valor a ser depositado.
     * @return      Verdadeiro se o depósito foi bem-sucedido, falso caso contrário.
     */
    public boolean deposita(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            support.firePropertyChange("saldo", null, this.saldo);
            return true;
        }
        return false;
    }

    /**
     * Este método saca um valor da conta.
     * Verifica se o valor é maior que 0 e menor ou igual ao saldo antes de sacar.
     *
     * @param valor O valor a ser sacado.
     * @return      Verdadeiro se o saque foi bem-sucedido, falso caso contrário.
     */
    public boolean saca(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
            support.firePropertyChange("saldo", null, this.saldo);
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

    /**
     * Este é um método abstrato que deve ser implementado nas subclasses.
     * Ele é usado para calcular a remuneração da conta.
     */
    public abstract void remunera();
}