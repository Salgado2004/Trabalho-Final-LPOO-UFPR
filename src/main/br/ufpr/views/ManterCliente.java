package main.br.ufpr.views;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.Endereco;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManterCliente implements Tela {
    private JPanel frame;
    private JButton voltarButton;
    private JTextField textFieldNome;
    private JTextField textFieldSobrenome;
    private JTextField textFieldLogradouro;
    private JTextField textFieldBairro;
    private JTextField textFieldCidade;
    private JTextField textFieldCPF;
    private JTextField textFieldRG;
    private JButton editarButton;
    private JTable tabelaClientes;
    private JTextField textFieldNumero;
    private JButton inserirButton;
    private ManterClienteTableModel tabelaModel = new ManterClienteTableModel(Sistema.getClientes());

    public ManterCliente() {
        tabelaClientes.setModel(tabelaModel);
        tabelaClientes.setColumnModel(tabelaClientes.getColumnModel());
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sistema.goBack();
            }
        });
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String sobreNome = textFieldSobrenome.getText();
                String logradouro = textFieldLogradouro.getText();
                String bairro = textFieldBairro.getText();
                String cidade = textFieldCidade.getText();
                String numero = textFieldNumero.getText();
                String cpf = textFieldCPF.getText();
                String rg = textFieldRG.getText();

                if (cpf.equals("")) {
                    JOptionPane.showMessageDialog(null, "CPF não pode ser vazio.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (isCpfExistente(cpf)){
                    JOptionPane.showMessageDialog(null, "CPF já cadastrado.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Endereco endereco = new Endereco(logradouro, bairro, numero, cidade);

                Cliente c = new Cliente(nome, sobreNome, endereco, cpf, rg);

                Sistema.cadastrarCliente(c);

                textFieldNome.setText("");
                textFieldSobrenome.setText("");
                textFieldLogradouro.setText("");
                textFieldBairro.setText("");
                textFieldCidade.setText("");
                textFieldNumero.setText("");
                textFieldCPF.setText("");
                textFieldRG.setText("");

                tabelaModel.fireTableDataChanged();
            }
        });



    }

    private boolean isCpfExistente(String cpf) {
        for (Cliente cliente : Sistema.getClientes()) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                return true;
            }
        }
        return false;
    }

    public JPanel getFrame() {
        return frame;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
