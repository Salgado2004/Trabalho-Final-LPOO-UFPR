/**
 * Classe Cliente que representa um cliente do banco.
 */
public class Cliente {
    private String nome;
    private String sobrenome;
    private Endereco endereco;
    private final String cpf;
    private String rg;
    private Conta conta;

    /**
     * Construtor da classe Cliente.
     * @param nome Nome do cliente.
     * @param sobrenome Sobrenome do cliente.
     * @param endereco Endereço do cliente.
     * @param cpf CPF do cliente.
     * @param rg RG do cliente.
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

    /**
     * Retorna o nome do cliente.
     * @return Nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     * @param nome Nome do cliente.
     */
    public void setNome(String nome) {
        nome = nome.toUpperCase();

        if (!validaNome(nome)) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    /**
     * Retorna o sobrenome do cliente.
     * @return Sobrenome do cliente.
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * Define o sobrenome do cliente.
     * @param sobrenome Sobrenome do cliente.
     */
    public void setSobrenome(String sobrenome) {
        sobrenome = sobrenome.toUpperCase();
        if (!validaNome(sobrenome)) {
            throw new IllegalArgumentException("Sobrenome inválido");
        }
        this.sobrenome = sobrenome;
    }

    /**
     * Retorna o endereço do cliente.
     * @return Endereço do cliente.
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente.
     * @param endereco Endereço do cliente.
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o CPF do cliente.
     * @return CPF do cliente.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna o RG do cliente.
     * @return RG do cliente.
     */
    public String getRg() {
        return rg;
    }

    /**
     * Define o RG do cliente.
     * @param rg RG do cliente.
     */
    public void setRg(String rg) {
        this.rg = rg;
    }

    /**
     * Retorna a conta do cliente.
     * @return Conta do cliente.
     */
    public Conta getConta() {
        return conta;
    }

    /**
     * Define a conta do cliente.
     * @param conta Conta do cliente.
     */
    public void setConta(Conta conta) {
        this.conta = conta;
    }

    /**
     * Valida o nome do cliente.
     * @param nome Nome do cliente.
     * @return Verdadeiro se o nome é válido, falso caso contrário.
     */
    private boolean validaNome(String nome) {
        boolean bool = nome.matches("[A-ZÁ-Ú ]{1,40}");
        return bool;
    }

    /**
     * Valida o CPF do cliente.
     * @param cpf CPF do cliente.
     * @return Verdadeiro se o CPF é válido, falso caso contrário.
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