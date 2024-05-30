package main.br.ufpr.views;

import main.br.ufpr.controllers.Mensagens;
import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.Conta;
import main.br.ufpr.models.ContaCorrente;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.*;

public class ManipularConta implements Tela {
    private Conta conta;
    private JPanel frame;
    private JButton voltarButton;
    private JTextField cpfCliente;
    private JPanel dadosConta;
    private JButton buscarButton;
    private JPanel boxSaldo;
    private JLabel saldo;
    private JCheckBox mostrarSaldo;
    private JTabbedPane tabbedPane1;
    private JPanel sacar;
    private JPanel depositar;
    private JPanel investir;
    private JTextField valorSaque;
    private JButton saqueButton;
    private JTextField valorDeposito;
    private JButton depositoButton;
    private JButton investirButton;
    private JLabel tipoConta;
    private JLabel rendimento;
    private JLabel numeroConta;

    public ManipularConta() {
        ImageIcon eye_open = new ImageIcon("assets/eye_open.png");
        ImageIcon eye_closed = new ImageIcon("assets/eye_closed.png");
        ImageIcon search = new ImageIcon("assets/search.png");
        ImageIcon deposito = new ImageIcon("assets/deposito.png");
        ImageIcon saque = new ImageIcon("assets/saque.png");
        ImageIcon remunera = new ImageIcon("assets/remunera.png");
        ImageIcon investimento = new ImageIcon("assets/investir.png");

        buscarButton.setIcon(search);
        mostrarSaldo.setIcon(eye_open);
        mostrarSaldo.setSelectedIcon(eye_closed);
        tabbedPane1.setIconAt(0, saque);
        tabbedPane1.setIconAt(1, deposito);
        tabbedPane1.setIconAt(2, remunera);
        investirButton.setIcon(investimento);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sistema.goBack();
            }
        });
        mostrarSaldo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                saldo.setVisible(mostrarSaldo.isSelected());
            }
        });
        buscarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!cpfCliente.getText().isEmpty()) {
                    String cpf = cpfCliente.getText();
                    conta = pesquisaContaPorCliente(cpf);
                    if(conta != null) {
                        loadConta();
                        dadosConta.setVisible(true);
                    }
                }
            }
        });
        saqueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    double valor = Double.parseDouble(valorSaque.getText());
                    if(conta.saca(valor)){
                        Mensagens.sucesso(dadosConta, "Saque realizado com sucesso");
                        loadConta();
                    } else {
                        Mensagens.aviso(dadosConta, conta.getClass() == ContaCorrente.class ? "Saldo/limite insuficiente" : "O valor restante é inferior ao montante mínimo");
                    }
                } catch (NumberFormatException ex){
                    Mensagens.erro(dadosConta, "Valor inserido inválido");

                }
            }
        });

        depositoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    double valor = Double.parseDouble(valorDeposito.getText());
                    if(conta.deposita(valor)) {
                        Mensagens.sucesso(dadosConta, "Depósito realizado com sucesso");
                        loadConta();
                    } else {
                        Mensagens.aviso(dadosConta, "Depósito mínimo não atingido");
                    }
                } catch (NumberFormatException ex){
                    Mensagens.erro(dadosConta, "Valor inserido inválido");
                }
            }
        });

        investirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                conta.remunera();
                Mensagens.sucesso(dadosConta, "Investimento realizado com sucesso");
                loadConta();
            }
        });
    }

    public ManipularConta(Cliente cliente){
        this();
        conta = cliente.getConta();
        cpfCliente.setText(cliente.getCpf());
        loadConta();
        dadosConta.setVisible(true);
    }

    private Conta pesquisaContaPorCliente(String cpfCliente){
        cpfCliente = cpfCliente.replaceAll("[^0-9]", "");
        for(Cliente c : Sistema.getClientes()){
            if(c.getCpf().equals(cpfCliente)){
                if (c.getConta() != null) {
                    return c.getConta();
                } else{
                    Mensagens.aviso(dadosConta, "O cliente pesquisado não possui uma conta");
                    return null;
                }
            }
        }
        Mensagens.aviso(dadosConta, "Esse cliente não foi encontrado");
        return null;
    }

    private void loadConta(){
        Mensagens.carregando(dadosConta, "Carregando dados da conta...");
        saldo.setText("R$ " + String.format("%.2f", conta.getSaldo()).replace(".", ",") );
        numeroConta.setText("Conta Nº: "+ String.format("%6d", conta.getNumero()));
        if(conta.getClass() == ContaCorrente.class){
            tipoConta.setText("Sua conta é do tipo: Conta Corrente");
            rendimento.setText("Seu rendimento é de: 101%");
        } else {
            tipoConta.setText("Sua conta é do tipo: Conta de Investimento");
            rendimento.setText("Seu rendimento é de: 102%");
        }
    }

    public JPanel getFrame() {
        return frame;
    }
}
