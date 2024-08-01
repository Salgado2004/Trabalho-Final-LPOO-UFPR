package main.br.ufpr.views;

import main.br.ufpr.controllers.Mensagens;
import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.controllers.Imagens;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.ContaCorrente;
import main.br.ufpr.models.ContaInvestimento;
import main.br.ufpr.models.Tela;
import main.br.ufpr.services.FactoryConta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Classe responsável por vincular um cliente a uma conta.
 * Implementa a interface Tela.
 */
public class VincularCliente implements Tela {
    private JPanel frame;
    private JButton voltarButton;
    private JComboBox tipoConta;
    private JTextField depositoInicial;
    private JTextField textField2;
    private JTextField textField3;
    private JButton salvarButton;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTable tabela;
    private JPanel formulario;
    private JButton gerenciarContaButton;
    private JButton excluirButton;
    private VincularTableModel tabelaModel = new VincularTableModel(Sistema.getClientes());
    private Cliente clienteSelecionado;
    private ContaCorrente corrente;
    private ContaInvestimento investimento;
    private JScrollPane scrollPanel;

    /**
     * Construtor da classe VincularCliente.
     * Inicializa os componentes da interface e define os listeners dos botões.
     */
    public VincularCliente() {

        excluirButton.setIcon(Imagens.DELETE.icon());
        gerenciarContaButton.setIcon(Imagens.DEPOSITO.icon());

        tabela.setModel(tabelaModel);
        tabela.setColumnModel(tabela.getColumnModel());

        scrollPanel.getViewport().setBackground(new Color(5,28,59));
        tabela.getTableHeader().setBackground(new Color(225,248,255));

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sistema.goBack();
            }
        });

        tipoConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                label1.setText("Depósito Inicial:");

                switch (Objects.requireNonNull(tipoConta.getSelectedItem()).toString()) {
                    case "Conta Corrente":
                        formulario.setVisible(true);
                        label2.setText("Limite:");
                        label3.setVisible(false);
                        textField3.setVisible(false);
                        break;
                    case "Conta Investimento":
                        formulario.setVisible(true);
                        label3.setVisible(true);
                        textField3.setVisible(true);
                        label2.setText("Montante Mínimo:");
                        label3.setText("Depósito Mínimo:");
                        break;
                    default:
                        formulario.setVisible(false);
                        break;
                }
            }
        });



        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (tabela.getSelectedRow() != -1) {
                clienteSelecionado = Sistema.getClientes().get(tabela.getSelectedRow());
                tipoConta.setEnabled(false);
                excluirButton.setVisible(true);
                gerenciarContaButton.setVisible(true);
                salvarButton.setVisible(false);
                switch (clienteSelecionado.getConta() != null? clienteSelecionado.getConta().getClass().getSimpleName() : "") {
                    case "ContaCorrente":
                        tipoConta.setSelectedItem("Conta Corrente");
                        corrente = (ContaCorrente) clienteSelecionado.getConta();
                        depositoInicial.setText(String.valueOf(corrente.getDepositoInicial()));
                        textField2.setText(String.valueOf(corrente.getLimite()));
                        break;
                    case "ContaInvestimento":
                        tipoConta.setSelectedItem("Conta Investimento");
                        investimento = (ContaInvestimento) clienteSelecionado.getConta();
                        depositoInicial.setText(String.valueOf(investimento.getDepositoInicial()));
                        textField2.setText(String.valueOf(investimento.getMontanteMinimo()));
                        textField3.setText(String.valueOf(investimento.getDepositoMinimo()));
                        break;
                    default:
                        tipoConta.setSelectedItem("");
                        depositoInicial.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        excluirButton.setVisible(false);
                        gerenciarContaButton.setVisible(false);
                        salvarButton.setVisible(true);
                        tipoConta.setEnabled(true);
                        break;
                }
            }
        });


        salvarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                FactoryConta factory = new FactoryConta();
                ArrayList<JTextField> campos = new ArrayList<>(Arrays.asList(depositoInicial, textField2, textField3));

                try {
                    factory.abrirConta(tipoConta.getSelectedItem().toString(), clienteSelecionado, validaCampos(campos));
                    Mensagens.sucesso(frame, "Conta cadastrada com sucesso!");
                    formulario.setVisible(false);
                    tipoConta.setEnabled(false);
                    tabelaModel.fireTableDataChanged();

                } catch (NumberFormatException ex){
                    Mensagens.erro(frame, ex.getMessage());
                } catch (IllegalArgumentException ex){
                    Mensagens.aviso(frame, ex.getMessage());
                }
            }
        });

        gerenciarContaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sistema.navigate(new ManipularConta(clienteSelecionado));
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!Mensagens.confirmar(frame, "Tem certeza que deseja excluir a conta?")) return;
                Sistema.getContas().remove(clienteSelecionado.getConta());
                clienteSelecionado.setConta(null);
                tabelaModel.fireTableDataChanged();
                Mensagens.sucesso(frame, "Conta excluída com sucesso");
                tipoConta.setSelectedIndex(0);

            }
        });
    }
    /**
     * Retorna o painel principal da tela.
     *
     * @return O painel principal da tela.
     */
    public JPanel getFrame() {
        return frame;
    }

    /**
     * Valida se todos os campos necessários estão preenchidos corretamente e, se estiver, retorna um array com os valores dos campos.
     *
     * @param campos ArrayList de campos de texto a serem verificados.
     * @return ArrayList com os valores dos campos.
     * @throws NumberFormatException Se um dos campos não for um número válido.
     * @throws IllegalArgumentException Se um dos campos estiver vazio.
     */
    private ArrayList<Double> validaCampos(ArrayList<JTextField> campos){
        ArrayList<Double> valores = new ArrayList<>();
        for (JTextField campo : campos){
            if (!campo.isVisible()) continue;
            if (campo.getText().isEmpty()){
                throw new IllegalArgumentException("Preencha todos os campos");
            }
            try {
                valores.add(Double.parseDouble(campo.getText()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Valor '" + campo.getText() + "' inválido");
            }
        }
        return valores;
    }
}
