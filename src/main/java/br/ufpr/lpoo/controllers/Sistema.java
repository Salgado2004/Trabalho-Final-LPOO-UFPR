package br.ufpr.lpoo.controllers;

import br.ufpr.lpoo.models.*;
import br.ufpr.lpoo.models.connection.ClienteDao;
import br.ufpr.lpoo.models.connection.DaoFactory;
import br.ufpr.lpoo.models.connection.DaoType;
import br.ufpr.lpoo.utils.Imagens;
import br.ufpr.lpoo.views.Home;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static br.ufpr.lpoo.models.connection.DaoType.MYSQL;

/**
 * Classe que controla a navegação entre as telas e o cadastro de clientes e contas
 * Age como um controlador de rotas e de dados
 */
public class Sistema {
    private static final JFrame frame = new JFrame("Banco TADS");
    private static final Stack<Tela> navegacao = new Stack<>();
    static final List <Cliente> clientes = new ArrayList<>();
    static final List <Conta> contas = new ArrayList<>();
    private static final DaoType type = MYSQL;

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
    public static void cadastrarCliente(Cliente cliente) throws Exception {
        ClienteDao clienteDao = DaoFactory.getClienteDao(type);
        clienteDao.create(cliente);
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
        try {
            ClienteDao clienteDao = DaoFactory.getClienteDao(type);
            return clienteDao.readAll();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Método que retorna a lista de clientes filtrada pela query
     * @param query Um trecho do nome, sobrenome, ou CPF inteiro
     * @return List<Cliente>
     */
    public static List<Cliente> getClientes(String query){
        try {
            ClienteDao clienteDao = DaoFactory.getClienteDao(type);
            return clienteDao.search(query);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static void updateCliente(Cliente cliente) throws Exception {
            ClienteDao clienteDao = DaoFactory.getClienteDao(type);
            clienteDao.update(cliente);
    }

    public static void deleteCliente(Cliente cliente) throws Exception {
        ClienteDao clienteDao = DaoFactory.getClienteDao(type);
        clienteDao.delete(cliente);
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
     */
    public static void main(String[] args){
        Home home = new Home();
        navegacao.push(home);
        Dimension dimension = new Dimension(800,550);
        Image image = Imagens.MAIN.image();
        frame.setIconImage(image);
        frame.setMinimumSize(dimension);
        frame.setBounds(100, 100, 650,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        navigate();
        frame.setVisible(true);
    }

}
