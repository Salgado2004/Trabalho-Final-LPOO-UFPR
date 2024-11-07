package br.ufpr.lpoo.controllers;

import br.ufpr.lpoo.models.*;
import br.ufpr.lpoo.utils.Imagens;
import br.ufpr.lpoo.views.Home;

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

    /**
     * Método que cadastra um cliente na lista de clientes
     * @param cliente
     */
    public static void cadastrarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    /**
     * Método que cadastra uma conta na lista de contas
     * @param conta
     */
    public static void cadastrarConta(Conta conta){
        contas.add(conta);
    }

    /**
     * Método que retorna a lista de clientes
     * @return List<Cliente>
     */
    public static List<Cliente> getClientes(){
        return clientes;
    }

    /**
     * Método que retorna a lista de contas
     * @return List<Conta>
     */
    public static List<Conta> getContas(){
        return contas;
    }

    /**
     * Método main que inicializa a aplicação
     *
     * Cria uma instância da classe Home e a adiciona na pilha de navegação
     * Define o ícone da aplicação como a imagem MAIN
     * Define o tamanho mínimo da janela como 700x500
     * Define a posição da janela como centralizada
     * Define a operação padrão de fechamento da janela como EXIT_ON_CLOSE
     * Chama o método navigate()
     * Define a janela como visível
     * Inicializa 3 clientes e os cadastra
     */
    public static void main(String[] args){
        Home home = new Home();
        navegacao.push(home);
        Dimension dimension = new Dimension(700,500);
        Image image = Imagens.MAIN.image();
        frame.setIconImage(image);
        frame.setMinimumSize(dimension);
        frame.setBounds(100, 100, 650,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        navigate();
        frame.setVisible(true);

        Endereco endereco1 = new Endereco("Rua A", "Prado Velho", "250", "Curitiba");
        Cliente c1 = new Cliente("Pedro", "Souza", endereco1, "948.312.270-80", "129711280");
        cadastrarCliente(c1);

        Endereco endereco2 = new Endereco("Rua B", "Cajuru", "1134", "Curitiba");
        Cliente c2 = new Cliente("Alisson", "Santos", endereco2, "885.434.710-87", "119264210");
        cadastrarCliente(c2);

        Endereco endereco3 = new Endereco("Rua C", "Jardim Amélia", "65", "Pinhais");
        Cliente c3 = new Cliente("Leonardo", "Salgado", endereco3, "632.872.080-71", "129321481");
        cadastrarCliente(c3);
    }

}
