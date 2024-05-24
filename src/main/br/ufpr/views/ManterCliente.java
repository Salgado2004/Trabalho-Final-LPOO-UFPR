package main.br.ufpr.views;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManterCliente implements Tela {
    private JPanel frame;
    private JButton voltarButton;

    public ManterCliente() {
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sistema.goBack();
            }
        });
    }

    public JPanel getFrame() {
        return frame;
    }
}
