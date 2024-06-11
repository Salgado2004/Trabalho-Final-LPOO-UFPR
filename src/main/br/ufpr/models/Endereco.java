/**
 * Classe Endereco.
 * Esta classe representa um endereço em um sistema.
 *
 * A classe possui os seguintes métodos:
 * - getLogradouro(): retorna o logradouro do endereço.
 * - getBairro(): retorna o bairro do endereço.
 * - getNumero(): retorna o número do endereço.
 * - getCidade(): retorna a cidade do endereço.
 *
 * A classe possui um método privado isNumeroValido(int numero) para verificar se o número do endereço é válido.
 */
public class Endereco {
    public String logradouro;
    public String bairro;
    public int numero;
    public String cidade;

    /**
     * Construtor da classe Endereco.
     *
     * @param logradouro o logradouro do endereço
     * @param bairro o bairro do endereço
     * @param numeroS o número do endereço como uma string
     * @param cidade a cidade do endereço
     * @throws IllegalArgumentException se o número do endereço for inválido
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

    /**
     * Método para obter o logradouro do endereço.
     *
     * @return o logradouro do endereço
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Método para obter o bairro do endereço.
     *
     * @return o bairro do endereço
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Método para obter o número do endereço.
     *
     * @return o número do endereço
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Método para obter a cidade do endereço.
     *
     * @return a cidade do endereço
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Método privado para verificar se o número do endereço é válido.
     *
     * @param numero o número do endereço
     * @return true se o número for válido, caso contrário retorna false
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