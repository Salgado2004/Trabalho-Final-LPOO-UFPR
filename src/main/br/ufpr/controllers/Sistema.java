package main.br.ufpr.controllers;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.Conta;
import main.br.ufpr.models.Tela;
import main.br.ufpr.views.Home;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Classe que controla a navegação entre as telas e o cadastro de clientes e contas
 * Age como um controlador de rotas e de dados
 */
public class Sistema {
    private static final JFrame frame = new JFrame("Banco TADS");
    private static final Stack<Tela> navegacao = new Stack<>();
    static final List <Cliente> clientes = new ArrayList<>();
    static final List <Conta> contas = new ArrayList<>();

    /**
     * Método que seta o conteúdo do frame para a tela que está no topo da pilha de navegação
     */
    public static void navigate(){
        frame.setContentPane(navegacao.peek().getFrame());
        frame.revalidate();
    }

    /**
     * Adiciona a tela passada como parâmetro na pilha de navegação e chama o método navigate()
     * @param frame
     * @see Sistema#navigate()
     */
    public static void navigate(Tela frame){
        navegacao.push(frame);
        navigate();
    }

    /**
     * Remove a tela do topo da pilha de navegação e chama o método navigate()
     * @see Sistema#navigate()
     */
    public static void goBack(){
        navegacao.pop();
        navigate();
    }

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

    public static void main(String[] args){
        Home home = new Home();
        navegacao.push(home);
        Dimension dimension = new Dimension(650,450);
        frame.setMinimumSize(dimension);
        frame.setBounds(100, 100, 650,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        navigate();
        frame.setVisible(true);
    }

}
