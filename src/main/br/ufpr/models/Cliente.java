package main.br.ufpr.models;
import java.text.Normalizer;
public class Cliente {
    private String nome;
    private String sobrenome;
    private Endereco endereco;
    private final String cpf;
    private String rg;
    private Conta conta;

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

    private boolean validaNome(String nome) {
        boolean bool = nome.matches("[A-ZÁ-Ú ]{1,40}");
        return bool;
    }

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
