package br.ufpr.lpoo.models;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        nome = nome.toUpperCase();

        if (!validaNome(nome)) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        sobrenome = sobrenome.toUpperCase();
        if (!validaNome(sobrenome)) {
            throw new IllegalArgumentException("Sobrenome inválido");
        }
        this.sobrenome = sobrenome;
    }


    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

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
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * (10 - i);
            }
            resto = 11 - (soma % 11);
            if (resto == 10 || resto == 11) {
                resto = 0;
            }
            if (resto != Integer.parseInt(cpf.substring(9, 10))) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * (11 - i);
            }
            resto = 11 - (soma % 11);
            if (resto == 10 || resto == 11) {
                resto = 0;
            }
            if (resto != Integer.parseInt(cpf.substring(10, 11))) {
                return false;
            }

            return true;
        }
        return false;
    }
}