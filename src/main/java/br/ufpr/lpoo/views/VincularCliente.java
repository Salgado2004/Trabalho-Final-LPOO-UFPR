package br.ufpr.lpoo.views;

import br.ufpr.lpoo.controllers.*;
import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.ContaCorrente;
import br.ufpr.lpoo.models.ContaInvestimento;
import br.ufpr.lpoo.models.Tela;
import br.ufpr.lpoo.utils.FactoryConta;
import br.ufpr.lpoo.utils.Imagens;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
        this.initUIComponents();

        tabela.setModel(tabelaModel);
        tabela.setColumnModel(tabela.getColumnModel());

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
                switch (clienteSelecionado.getConta() != null ? clienteSelecionado.getConta().getClass().getSimpleName() : "") {
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

                } catch (NumberFormatException ex) {
                    Mensagens.erro(frame, ex.getMessage());
                } catch (IllegalArgumentException ex) {
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

                if (!Mensagens.confirmar(frame, "Tem certeza que deseja excluir a conta?")) return;
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

    @Override
    public void initUIComponents() {
        excluirButton.setIcon(Imagens.DELETE.icon());
        gerenciarContaButton.setIcon(Imagens.DEPOSITO.icon());
        scrollPanel.getViewport().setBackground(new Color(5, 28, 59));
        tabela.getTableHeader().setBackground(new Color(225, 248, 255));
    }

    /**
     * Valida se todos os campos necessários estão preenchidos corretamente e, se estiver, retorna um array com os valores dos campos.
     *
     * @param campos ArrayList de campos de texto a serem verificados.
     * @return ArrayList com os valores dos campos.
     * @throws NumberFormatException    Se um dos campos não for um número válido.
     * @throws IllegalArgumentException Se um dos campos estiver vazio.
     */
    private ArrayList<Double> validaCampos(ArrayList<JTextField> campos) {
        ArrayList<Double> valores = new ArrayList<>();
        for (JTextField campo : campos) {
            if (!campo.isVisible()) continue;
            if (campo.getText().isEmpty()) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        frame = new JPanel();
        frame.setLayout(new GridLayoutManager(5, 3, new Insets(8, 8, 8, 8), -1, -1));
        frame.setBackground(new Color(-16442309));
        frame.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1967873)), "Contas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-1967873)));
        final JLabel label4 = new JLabel();
        label4.setBackground(new Color(-1967873));
        label4.setForeground(new Color(-1967873));
        label4.setText("Tipo de conta:");
        frame.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tipoConta = new JComboBox();
        tipoConta.setBackground(new Color(-2500135));
        tipoConta.setEnabled(false);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("Conta Corrente");
        defaultComboBoxModel1.addElement("Conta Investimento");
        tipoConta.setModel(defaultComboBoxModel1);
        tipoConta.setToolTipText("Você deve selecionar um cliente.");
        frame.add(tipoConta, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(189, 34), null, 0, false));
        scrollPanel = new JScrollPane();
        scrollPanel.setBackground(new Color(-12611640));
        frame.add(scrollPanel, new GridConstraints(1, 0, 3, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16442309)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tabela = new JTable();
        tabela.setBackground(new Color(-11487489));
        tabela.setForeground(new Color(-16777216));
        tabela.setGridColor(new Color(-16442309));
        tabela.setVisible(true);
        scrollPanel.setViewportView(tabela);
        formulario = new JPanel();
        formulario.setLayout(new GridLayoutManager(5, 3, new Insets(5, 5, 5, 5), -1, -1));
        formulario.setBackground(new Color(-16442309));
        formulario.setVisible(false);
        frame.add(formulario, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        excluirButton = new JButton();
        excluirButton.setBackground(new Color(-11487489));
        excluirButton.setText("Excluir");
        formulario.add(excluirButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        salvarButton = new JButton();
        salvarButton.setBackground(new Color(-11487489));
        salvarButton.setText("Salvar");
        formulario.add(salvarButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gerenciarContaButton = new JButton();
        gerenciarContaButton.setBackground(new Color(-11487489));
        gerenciarContaButton.setText("Gerenciar conta");
        formulario.add(gerenciarContaButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setForeground(new Color(-1967873));
        label3.setText("Label");
        label3.setVisible(true);
        formulario.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2 = new JLabel();
        label2.setForeground(new Color(-1967873));
        label2.setText("Limite:");
        label2.setVisible(true);
        formulario.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label1 = new JLabel();
        label1.setForeground(new Color(-1967873));
        label1.setText("Depósito Inicial:");
        formulario.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        textField3.setBackground(new Color(-2500135));
        textField3.setVisible(true);
        formulario.add(textField3, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField2 = new JTextField();
        textField2.setBackground(new Color(-2500135));
        formulario.add(textField2, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        depositoInicial = new JTextField();
        depositoInicial.setBackground(new Color(-2500135));
        formulario.add(depositoInicial, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        voltarButton = new JButton();
        voltarButton.setBackground(new Color(-11487489));
        voltarButton.setText("Voltar");
        frame.add(voltarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return frame;
    }
}
