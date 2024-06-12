package main.br.ufpr.views;

import main.br.ufpr.controllers.Imagens;
import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta classe representa a tela inicial do sistema.
 * Ela implementa a interface Tela e define os botões e ações da tela inicial.
 */
public class Home implements Tela {
    private JPanel frame;
    private JButton clientesButton;
    private JButton contasButton;
    private JButton transactionsButton;
    private JLabel logo;

    /**
     * Construtor para a classe Home.
     * Define os valores iniciais para as variáveis de instância e adiciona os ouvintes de ação aos botões.
     */
    public Home() {
        logo.setIcon(Imagens.LOGO.icon(300, 300));
        contasButton.setIcon(Imagens.CONTAS.icon());
        clientesButton.setIcon(Imagens.CLIENTES.icon());
        transactionsButton.setIcon(Imagens.TRANSACTIONS.icon());

        clientesButton.addActionListener(new ActionListener() {
            /**
             * Este método é chamado quando o botão Clientes é clicado.
             * Ele navega para a tela ManterCliente.
             *
             * @param e O evento de ação.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ManterCliente frame = new ManterCliente();
                Sistema.navigate(frame);
            }
        });
        contasButton.addActionListener(new ActionListener() {
            /**
             * Este método é chamado quando o botão Contas é clicado.
             * Ele navega para a tela VincularCliente.
             *
             * @param e O evento de ação.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                VincularCliente frame = new VincularCliente();
                Sistema.navigate(frame);
            }
        });
        transactionsButton.addActionListener(new ActionListener() {
            /**
             * Este método é chamado quando o botão Transações é clicado.
             * Ele navega para a tela ManipularConta.
             *
             * @param e O evento de ação.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ManipularConta frame = new ManipularConta();
                Sistema.navigate(frame);
            }
        });
    }

    /**
     * Este método retorna o frame da tela.
     *
     * @return O frame da tela.
     */
    public JPanel getFrame() {
        return frame;
    }
}