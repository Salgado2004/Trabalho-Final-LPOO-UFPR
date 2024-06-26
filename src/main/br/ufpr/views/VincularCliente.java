package main.br.ufpr.views;

import main.br.ufpr.controllers.Mensagens;
import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.controllers.Imagens;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.ContaCorrente;
import main.br.ufpr.models.ContaInvestimento;
import main.br.ufpr.models.Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Classe responsável por vincular um cliente a uma conta.
 * Implementa a interface Tela.
 */
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
    private JButton gerenciarContaButton;
    private JButton excluirButton;
    private VincularTableModel tabelaModel = new VincularTableModel(Sistema.getClientes());
    private Cliente clienteSelecionado;
    private ContaCorrente corrente;
    private ContaInvestimento investimento;
    private JScrollPane scrollPanel;
    private int rowSelecionada;
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



        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (tabela.getSelectedRow() != -1) {
                clienteSelecionado = Sistema.getClientes().get(tabela.getSelectedRow());
                rowSelecionada = tabela.getSelectedRow();
                tipoConta.setEnabled(false);
                excluirButton.setVisible(true);
                gerenciarContaButton.setVisible(true);
                salvarButton.setVisible(false);
                switch (clienteSelecionado.getConta() != null? clienteSelecionado.getConta().getClass().getSimpleName() : "") {
                    case "ContaCorrente":
                        tipoConta.setSelectedItem("Conta Corrente");
                        corrente = (ContaCorrente) clienteSelecionado.getConta();
                        textField1.setText(String.valueOf(corrente.getDepositoInicial()));
                        textField2.setText(String.valueOf(corrente.getLimite()));
                        break;
                    case "ContaInvestimento":
                        tipoConta.setSelectedItem("Conta Investimento");
                        investimento = (ContaInvestimento) clienteSelecionado.getConta();
                        textField1.setText(String.valueOf(investimento.getDepositoInicial()));
                        textField2.setText(String.valueOf(investimento.getMontanteMinimo()));
                        textField3.setText(String.valueOf(investimento.getDepositoMinimo()));
                        break;
                    default:
                        tipoConta.setSelectedItem("");
                        textField1.setText("");
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

                switch (tipoConta.getSelectedItem().toString()) {
                    case "Conta Corrente":
                        if(verificaCampo(textField1) && verificaCampo(textField2)) {
                            corrente = new ContaCorrente(
                                    Sistema.getClientes().get(rowSelecionada),
                                    Double.parseDouble(textField1.getText()),
                                    Double.parseDouble(textField2.getText())
                            );
                            Sistema.cadastrarConta(corrente);
                            clienteSelecionado.setConta(corrente);
                            Mensagens.sucesso(frame, "Conta cadastrada com sucesso!");
                            formulario.setVisible(false);
                            tipoConta.setEnabled(false);
                        }
                        break;
                    case "Conta Investimento":
                        if(verificaCampo(textField1) && verificaCampo(textField2) && verificaCampo(textField3)) {
                            investimento = new ContaInvestimento(
                                    Sistema.getClientes().get(rowSelecionada),
                                    Double.parseDouble(textField1.getText()),
                                    Double.parseDouble(textField3.getText()),
                                    Double.parseDouble(textField2.getText())
                            );
                            Sistema.cadastrarConta(investimento);
                            clienteSelecionado.setConta(investimento);
                            Mensagens.sucesso(frame, "Conta cadastrada com sucesso!");
                            formulario.setVisible(false);
                            tipoConta.setEnabled(false);
                        }
                        break;
                }
                tabelaModel.fireTableDataChanged();
                corrente = null;
                investimento = null;
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
                JOptionPane.showMessageDialog(frame, "Conta excluída com sucesso");
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
     * Verifica se um campo de texto está preenchido e se o valor pode ser convertido para um número.
     *
     * @param textField O campo de texto a ser verificado.
     * @return Verdadeiro se o campo de texto está preenchido e o valor pode ser convertido para um número, falso caso contrário.
     */
    private boolean verificaCampo(JTextField textField){
        if (!textField.getText().isEmpty()){
            try {
                Double.parseDouble(textField.getText());
                return true;
            } catch (NumberFormatException e){
                Mensagens.aviso(frame, "Preencha os campos corretamente");
                return false;
            }
        } else {
            Mensagens.aviso(frame, "Preencha todos os campos");
            return false;
        }
    }
}
