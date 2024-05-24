package main.br.ufpr.views;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home implements Tela {
    private JPanel frame;
    private JButton clientesButton;
    private JButton contasButton;
    private JButton transactionsButton;

    public Home() {
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManterCliente frame = new ManterCliente();
                Sistema.navigate(frame);
            }
        });
        contasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VincularCliente frame = new VincularCliente();
                Sistema.navigate(frame);
            }
        });
        transactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManipularConta frame = new ManipularConta();
                Sistema.navigate(frame);
            }
        });
    }

    public JPanel getFrame() {
        return frame;
    }
}
