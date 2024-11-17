package br.ufpr.lpoo;

import br.ufpr.lpoo.models.*;
import br.ufpr.lpoo.utils.Imagens;
import br.ufpr.lpoo.views.Home;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Classe que controla a navegação entre as telas e o cadastro de clientes e contas
 * Age como um controlador de rotas e de dados
 */
public class Sistema {
    private static final JFrame frame = new JFrame("Banco TADS");
    private static final Stack<Tela> navegacao = new Stack<>();

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
