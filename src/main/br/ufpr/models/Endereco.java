package main.br.ufpr.models;

/**
 * Esta classe representa um Endereço no sistema.
 * Inclui informações como logradouro, bairro, número e cidade.
 */
public class Endereco {
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

    // Os getters para as variáveis de instância estão aqui.

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
}