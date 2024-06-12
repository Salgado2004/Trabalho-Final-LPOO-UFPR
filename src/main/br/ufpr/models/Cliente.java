package main.br.ufpr.models;
import java.text.Normalizer;

/**
 * Esta classe representa um cliente no sistema.
 * Inclui informações como nome, sobrenome, endereço, CPF, RG e conta.
 */
public class Cliente {
    private String nome;
    private String sobrenome;
    private Endereco endereco;
    private final String cpf;
    private String rg;
    private Conta conta;

    /**
     * Construtor para a classe Cliente.
     * Valida o CPF e define os valores iniciais para as variáveis de instância.
     *
     * @param nome      O primeiro nome do cliente.
     * @param sobrenome O sobrenome do cliente.
     * @param endereco  O endereço do cliente.
     * @param cpf       O CPF do cliente.
     * @param rg        O RG do cliente.
     */
    public Cliente(String nome, String sobrenome, Endereco endereco, String cpf, String rg) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (!validaCpf(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        setNome(nome);
        setSobrenome(sobrenome);
        setEndereco(endereco);
        setRg(rg);

        this.cpf = cpf;
    }

    // Os getters e setters para as variáveis de instância estão aqui.

    /**
     * Este método valida o nome do cliente.
     * Verifica se o nome contém apenas letras maiúsculas e espaços, e se o comprimento está entre 1 e 40.
     *
     * @param nome O nome a ser validado.
     * @return     Verdadeiro se o nome for válido, falso caso contrário.
     */
    private boolean validaNome(String nome) {
        boolean bool = nome.matches("[A-ZÁ-Ú ]{1,40}");
        return bool;
    }

    /**
     * Este método valida o CPF do cliente.
     * Verifica se o CPF é uma sequência de 11 dígitos e se todos os dígitos não são iguais.
     * Também verifica os dois dígitos de verificação no final do CPF.
     *
     * @param cpf O CPF a ser validado.
     * @return    Verdadeiro se o CPF for válido, falso caso contrário.
     */
    private boolean validaCpf(String cpf) {
        int soma = 0, resto = 0;

        if(cpf.matches("[0-9]{11}") && !cpf.matches("^(\\d)\\1{10}")){
            // A lógica de validação do CPF está aqui.
        }
        return false;
    }
}