package main.br.ufpr.views;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.ContaCorrente;
import main.br.ufpr.models.ContaInvestimento;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

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
    private JPanel formulario;
    private VincularTableModel tabelaModel = new VincularTableModel(Sistema.getClientes());
    private Cliente clienteSelecionado;
    private ContaCorrente corrente;
    private ContaInvestimento investimento;

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
                switch (Objects.requireNonNull(tipoConta.getSelectedItem()).toString()) {
                    case "Conta Corrente":
                        formulario.setVisible(true);
                        label1.setText("Depósito Inicial:");
                        label2.setText("Limite:");
                        label3.setVisible(false);
                        textField3.setVisible(false);
                        break;
                    case "Conta Investimento":
                        formulario.setVisible(true);
                        label3.setVisible(true);
                        textField3.setVisible(true);
                        label1.setText("Depósito Inicial:");
                        label2.setText("Montante Mínimo:");
                        label3.setText("Depósito Mínimo:");
                        break;
                    default:
                        formulario.setVisible(false);
                        break;
                }

            }
        });


        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabela.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(frame, "Selecione um cliente para vincular");
                    return;
                }
                switch (Objects.requireNonNull(tipoConta.getSelectedItem()).toString()) {
                    case "Conta Corrente":
                        corrente = new ContaCorrente(
                                1,
                                Sistema.getClientes().get(tabela.getSelectedRow()),
                                Double.parseDouble(textField1.getText()),
                                Double.parseDouble(textField2.getText())
                        );
                        Sistema.cadastrarConta(corrente);
                        clienteSelecionado.setConta(corrente);
                        break;
                    case "Conta Investimento":
                        investimento = new ContaInvestimento(
                                1,
                                Sistema.getClientes().get(tabela.getSelectedRow()),
                                Double.parseDouble(textField1.getText()),
                                Double.parseDouble(textField2.getText()),
                                Double.parseDouble(textField3.getText())
                        );
                        Sistema.cadastrarConta(investimento);
                        clienteSelecionado.setConta(investimento);
                        break;
                }
                JOptionPane.showMessageDialog(frame, "Conta cadastrada com sucesso");
            }
        });



        tabela.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                clienteSelecionado = Sistema.getClientes().get(tabela.getSelectedRow());
                if (clienteSelecionado.getConta() != null) {
                    tipoConta.setSelectedIndex(1);
                    textField1.setText(clienteSelecionado.getConta().getClass().getSimpleName());
                }
            }
        });
    }

    public JPanel getFrame() {
        return frame;
    }
}
