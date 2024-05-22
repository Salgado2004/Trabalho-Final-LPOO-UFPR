package main.br.ufpr.models;

public class Endereco {
    public String logradouro;
    public String bairro;
    public int numero;
    public String cidade;

    public Endereco(String logradouro, String bairro, String numeroS, String cidade) {
        this.numero = Integer.parseInt(numeroS);
        if (numero <= 0 || !isNumeroValido(numero)) {
            throw new IllegalArgumentException("Numero Invalido");
        }
        this.logradouro = logradouro.toUpperCase();
        this.bairro = bairro.toUpperCase();
        this.cidade = cidade.toUpperCase();
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

    // Método privado para verificar se o número é válido
    private boolean isNumeroValido(int numero) {
        try {
            Integer.parseInt(String.valueOf(numero));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

