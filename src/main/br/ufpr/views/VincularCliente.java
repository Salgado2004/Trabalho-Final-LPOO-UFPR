package main.br.ufpr.views;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.*;

public class VincularCliente implements Tela {
    private JPanel frame;
    private JButton voltarButton;
    private JComboBox tipoConta;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton salvarButton;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTable tabela;
    private VincularTableModel tabelaModel = new VincularTableModel(Sistema.getClientes());

    public VincularCliente() {

        tabela.setModel(tabelaModel);
        tabela.setAutoCreateColumnsFromModel(true);


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sistema.goBack();
            }
        });

        tipoConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tipoConta.getSelectedItem().equals("Conta Corrente")) {
                    label1.setText("Depósito Inicial:");
                    label2.setText("Limite:");
                    label3.setVisible(false);
                    textField3.setVisible(false);
                } else {
                    label3.setVisible(true);
                    textField3.setVisible(true);
                    label1.setText("Montante Mínimo:");
                    label2.setText("Depósito Mínimo:");
                    label3.setText("Depósito Inicial:");
                }
            }
        });




    }

    public JPanel getFrame() {
        return frame;
    }
}
