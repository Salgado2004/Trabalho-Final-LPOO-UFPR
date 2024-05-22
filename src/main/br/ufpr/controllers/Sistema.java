package main.br.ufpr.controllers;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.Conta;
import java.util.ArrayList;
import java.util.List;
public class Sistema {
    static final List <Cliente> clientes = new ArrayList<>();
    static final List <Conta> contas = new ArrayList<>();

    public static void cadastrarCliente(Cliente cliente){
        clientes.add(cliente);
    }
    public static void cadastrarConta(Conta conta){
        contas.add(conta);
    }

    public static List<Cliente> getClientes(){
        return clientes;
    }
    public static List<Conta> getContas(){
        return contas;
    }

}
