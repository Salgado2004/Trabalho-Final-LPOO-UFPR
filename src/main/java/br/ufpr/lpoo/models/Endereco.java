package br.ufpr.lpoo.models;

import java.util.Objects;

/**
 * Esta classe representa um Endereço no sistema.
 * Inclui informações como logradouro, bairro, número e cidade.
 */
public class Endereco {
    private int id;
    public String logradouro;
    public String bairro;
    public int numero;
    public String cidade;

    /**
     * Construtor para a classe Endereco.
     * Define os valores iniciais para as variáveis de instância.
     *
     * @param logradouro O logradouro do endereço.
     * @param bairro     O bairro do endereço.
     * @param numeroS    O número do endereço como uma String.
     * @param cidade     A cidade do endereço.
     */
    public Endereco(String logradouro, String bairro, String numeroS, String cidade) {
        this.numero = Integer.parseInt(numeroS);
        if (numero <= 0 || !isNumeroValido(numero)) {
            throw new IllegalArgumentException("Numero Invalido");
        }
        this.logradouro = logradouro.toUpperCase();
        this.bairro = bairro.toUpperCase();
        this.cidade = cidade.toUpperCase();
    }

    public Endereco(int id, String logradouro, String bairro, String numeroS, String cidade) {
        this(logradouro, bairro, numeroS, cidade);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public int getNumero() {
        return numero;
    }

    public String getCidade() {
        return cidade;
    }

    /**
     * Este método privado verifica se o número é válido.
     * Tenta converter o número para um inteiro e retorna verdadeiro se for bem-sucedido, falso caso contrário.
     *
     * @param numero O número a ser verificado.
     * @return       Verdadeiro se o número for válido, falso caso contrário.
     */
    private boolean isNumeroValido(int numero) {
        try {
            Integer.parseInt(String.valueOf(numero));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return id == endereco.id && numero == endereco.numero && Objects.equals(logradouro, endereco.logradouro) && Objects.equals(bairro, endereco.bairro) && Objects.equals(cidade, endereco.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logradouro, bairro, numero, cidade);
    }
}