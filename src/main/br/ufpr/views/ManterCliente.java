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
    private JButton buscarButton;
    private JTable tabelaClientes;
    private JTextField textFieldNumero;
    private JButton inserirButton;
    private JButton editarButton;
    private JButton excluirButton;
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

                areTextFieldsFilled(textFieldNome, "nome");
                areTextFieldsFilled(textFieldSobrenome, "sobrenome");
                areTextFieldsFilled(textFieldLogradouro, "logradouro");
                areTextFieldsFilled(textFieldBairro, "bairro");
                areTextFieldsFilled(textFieldCidade, "cidade");
                areTextFieldsFilled(textFieldNumero, "numero");
                areTextFieldsFilled(textFieldCPF, "CPF");
                areTextFieldsFilled(textFieldRG, "RG");

                cpf = cpf.replaceAll("[^0-9]", "");

                if (isInteger(numero)) {
                    JOptionPane.showMessageDialog(null, "Número Inválido.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (cpf.equals("")) {
                    JOptionPane.showMessageDialog(null, "CPF não pode ser vazio.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (isCpfExistente(cpf)) {
                    JOptionPane.showMessageDialog(null, "CPF já cadastrado.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!validaCpf(cpf)) {
                    JOptionPane.showMessageDialog(null, "CPF inválido.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
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


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cpf = textFieldCPF.getText();

                cpf = cpf.replaceAll("[^0-9]", "");

                if (cpf.equals("")) {
                    JOptionPane.showMessageDialog(null, "Insira um CPF para buscar.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!isCpfExistente(cpf)) {
                    JOptionPane.showMessageDialog(null, "CPF não cadastrado.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }


                for (Cliente cliente : Sistema.getClientes()) {
                    if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                        textFieldNome.setText(cliente.getNome());
                        textFieldSobrenome.setText(cliente.getSobrenome());
                        textFieldLogradouro.setText(cliente.getEndereco().getLogradouro());
                        textFieldBairro.setText(cliente.getEndereco().getBairro());
                        textFieldCidade.setText(cliente.getEndereco().getCidade());
                        textFieldNumero.setText(Integer.toString(cliente.getEndereco().getNumero()));
                        textFieldRG.setText(cliente.getRg());
                    }
                }

            }
        });

        editarButton.addActionListener(new ActionListener() {
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


                areTextFieldsFilled(textFieldNome, "nome");
                areTextFieldsFilled(textFieldSobrenome, "sobrenome");
                areTextFieldsFilled(textFieldLogradouro, "logradouro");
                areTextFieldsFilled(textFieldBairro, "bairro");
                areTextFieldsFilled(textFieldCidade, "cidade");
                areTextFieldsFilled(textFieldNumero, "numero");
                areTextFieldsFilled(textFieldCPF, "CPF");
                areTextFieldsFilled(textFieldRG, "RG");

                if (isInteger(numero)) {
                    JOptionPane.showMessageDialog(null, "Número Inválido.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (cpf.equals("")) {
                    JOptionPane.showMessageDialog(null, "CPF não pode ser vazio.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!validaCpf(cpf)) {
                    JOptionPane.showMessageDialog(null, "CPF inválido.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (Cliente cliente : Sistema.getClientes()) {
                    if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                        cliente.setNome(nome);
                        cliente.setSobrenome(sobreNome);
                        Endereco endereco = new Endereco(logradouro, bairro, numero, cidade);
                        cliente.setEndereco(endereco);
                        cliente.setRg(rg);
                    }
                }

                JOptionPane.showMessageDialog(null, "Informações Editadas.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);

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

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = textFieldCPF.getText();
                Sistema.getClientes().removeIf(cliente -> cliente.getCpf().equalsIgnoreCase(cpf));

                JOptionPane.showMessageDialog(null, "CPF " + cpf + " removido.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);

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

    public static boolean isInteger(String str) {
        if (str == null) {
            return true;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public static boolean areTextFieldsFilled(JTextField textField, String campo) {
        if (textField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campo " + campo + " está vazio.\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validaCpf(String cpf) {
        int soma = 0, resto = 0;

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.matches("[0-9]{11}") && !cpf.matches("^(\\d)\\1{10}")) {
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * (10 - i);
            }
            resto = 11 - (soma % 11);
            if (resto == 10 || resto == 11) {
                resto = 0;
            }
            if (resto != Integer.parseInt(cpf.substring(9, 10))) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * (11 - i);
            }
            resto = 11 - (soma % 11);
            if (resto == 10 || resto == 11) {
                resto = 0;
            }
            if (resto != Integer.parseInt(cpf.substring(10, 11))) {
                return false;
            }

            return true;
        }
        return false;
    }

    public JPanel getFrame() {
        return frame;
    }

}
