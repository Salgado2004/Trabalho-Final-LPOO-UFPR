package main.br.ufpr.views;

import main.br.ufpr.controllers.Imagens;
import main.br.ufpr.controllers.Mensagens;
import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.Conta;
import main.br.ufpr.models.ContaCorrente;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.event.*;

/**
 * Classe que representa a tela de manipulação de conta
 * Realiza transações como saque, depósito e investimento
 * @see Tela
 */
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
    private JLabel warningLimite;

    /**
     * Construtor da classe ManipularConta
     * Adiciona os ícones aos botões e painéis
     * Adiciona os listeners aos botões
     */
    public ManipularConta() {
        buscarButton.setIcon(Imagens.SEARCH.icon());
        mostrarSaldo.setIcon(Imagens.EYE_OPEN.icon());
        mostrarSaldo.setSelectedIcon(Imagens.EYE_CLOSED.icon());
        tabbedPane1.setIconAt(0, Imagens.SAQUE.icon());
        tabbedPane1.setIconAt(1, Imagens.DEPOSITO.icon());
        tabbedPane1.setIconAt(2, Imagens.REMUNERA.icon());
        investirButton.setIcon(Imagens.INVESTIMENTO.icon());

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
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        saqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        investirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(conta.getSaldo() > 0){
                    conta.remunera();
                    Mensagens.sucesso(dadosConta, "Investimento realizado com sucesso");
                    loadConta();
                } else {
                    Mensagens.aviso(dadosConta, "Saldo insuficiente para investir");
                }
            }
        });
    }

    /**
     * Construtor da classe ManipularConta
     * Serve para instanciar a classe e carregar os dados da conta de um cliente
     * @param cliente Cliente que será manipulado
     */
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
        warningLimite.setVisible(conta.getSaldo() < 0);
        if(conta.getClass() == ContaCorrente.class){
            tipoConta.setText("Sua conta é do tipo: Conta Corrente");
            rendimento.setText("Seu rendimento é de: 101%");
        } else {
            tipoConta.setText("Sua conta é do tipo: Conta de Investimento");
            rendimento.setText("Seu rendimento é de: 102%");
        }
    }

    /**
     * Retorna o painel principal da tela
     * @return JPanel
     * @see Tela
     */
    public JPanel getFrame() {
        return frame;
    }
}
