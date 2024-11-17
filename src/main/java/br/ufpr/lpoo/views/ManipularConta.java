package br.ufpr.lpoo.views;

import br.ufpr.lpoo.Sistema;
import br.ufpr.lpoo.controllers.*;
import br.ufpr.lpoo.models.*;
import br.ufpr.lpoo.models.connection.DaoType;
import br.ufpr.lpoo.utils.Imagens;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static br.ufpr.lpoo.models.connection.DaoType.MYSQL;

/**
 * Classe que representa a tela de manipulação de conta
 * Realiza transações como saque, depósito e investimento
 *
 * @see Tela
 */
public class ManipularConta implements Tela {
    private Conta conta;
    private JPanel frame;
    private ManipularContaController controller;
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
    private static final DaoType type = MYSQL;

    /**
     * Construtor da classe ManipularConta
     * Adiciona os ícones aos botões e painéis
     * Adiciona os listeners aos botões
     */
    public ManipularConta() {
        controller = new ManipularContaController(this);
    }

    public void setController() {
        voltarButton.addActionListener(e -> Sistema.goBack());

        buscarButton.addActionListener(e -> {
            if (cpfCliente.getText().isEmpty()) {
                MensagensController.aviso(dadosConta, "Digite um CPF para buscar");
            } else {
                String cpf = cpfCliente.getText();
                try {
                    this.controller.buscarConta(cpf);
                } catch (Exception ex) {
                    MensagensController.aviso(dadosConta, ex.getMessage());
                }
            }
        });

        mostrarSaldo.addItemListener(e -> saldo.setVisible(mostrarSaldo.isSelected()));

        saqueButton.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(valorSaque.getText());
                this.controller.realizaSaque(valor);
                MensagensController.sucesso(dadosConta, "Saque realizado com sucesso");
            } catch (NumberFormatException ex) {
                MensagensController.erro(dadosConta, "Valor inserido inválido");
            } catch (IllegalArgumentException ex) {
                MensagensController.aviso(dadosConta, ex.getMessage());
            } catch (Exception ex) {
                MensagensController.erro(dadosConta, "Erro ao realizar saque: " + ex.getMessage());
            }
        });

        depositoButton.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(valorDeposito.getText());
                this.controller.realizaDeposito(valor);
                MensagensController.sucesso(dadosConta, "Depósito realizado com sucesso");
            } catch (NumberFormatException ex) {
                MensagensController.erro(dadosConta, "Valor inserido inválido");
            } catch (IllegalArgumentException ex) {
                MensagensController.aviso(dadosConta, ex.getMessage());
            } catch (Exception ex) {
                MensagensController.erro(dadosConta, "Erro ao realizar depósito: " + ex.getMessage());
            }
        });

        investirButton.addActionListener(e -> {
            try {
                this.controller.realizaInvestimento();
                MensagensController.sucesso(dadosConta, "Investimento realizado com sucesso");
            } catch (IllegalArgumentException ex) {
                MensagensController.aviso(dadosConta, ex.getMessage());
            } catch (RuntimeException ex) {
                MensagensController.erro(dadosConta, ex.getMessage());
            }
        });
    }

    /**
     * Construtor da classe ManipularConta
     * Serve para instanciar a classe e carregar os dados da conta de um cliente
     *
     * @param cliente Cliente que será manipulado
     */
    public ManipularConta(Cliente cliente) {
        this();
        cpfCliente.setText(cliente.getCpf());
        try {
            this.controller.buscarConta(cliente.getCpf());
        } catch (Exception e) {
            MensagensController.aviso(dadosConta, e.getMessage());
        }
    }

    public void loadConta(Conta conta) {
        MensagensController.carregando(dadosConta, "Carregando dados da conta...");
        saldo.setText("R$ " + String.format("%.2f", conta.getSaldo()).replace(".", ","));
        numeroConta.setText("Conta Nº: " + String.format("%6d", conta.getNumero()));
        warningLimite.setVisible(conta.getSaldo() < 0);
        if (conta.getClass() == ContaCorrente.class) {
            tipoConta.setText("Sua conta é do tipo: Conta Corrente");
            rendimento.setText("Seu rendimento é de: 1% do montante total");
        } else {
            tipoConta.setText("Sua conta é do tipo: Conta de Investimento");
            rendimento.setText("Seu rendimento é de: 2% do montante total");
        }
        dadosConta.setVisible(true);
    }

    public void updateSaldo(double newSaldo) {
        saldo.setText("R$ " + String.format("%.2f", newSaldo).replace(".", ","));
        warningLimite.setVisible(newSaldo < 0);
    }

    /**
     * Retorna o painel principal da tela
     *
     * @return JPanel
     * @see Tela
     */
    public JPanel getFrame() {
        return frame;
    }

    @Override
    public void initUIComponents() {
        buscarButton.setIcon(Imagens.SEARCH.icon());
        mostrarSaldo.setIcon(Imagens.EYE_OPEN.icon());
        mostrarSaldo.setSelectedIcon(Imagens.EYE_CLOSED.icon());
        tabbedPane1.setIconAt(0, Imagens.SAQUE.icon());
        tabbedPane1.setIconAt(1, Imagens.DEPOSITO.icon());
        tabbedPane1.setIconAt(2, Imagens.REMUNERA.icon());
        investirButton.setIcon(Imagens.INVESTIMENTO.icon());
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
        frame.setLayout(new GridLayoutManager(3, 4, new Insets(8, 8, 8, 8), -1, -1));
        frame.setBackground(new Color(-16442309));
        frame.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1967873)), "Transações", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-1967873)));
        voltarButton = new JButton();
        voltarButton.setBackground(new Color(-11487489));
        voltarButton.setText("Voltar");
        frame.add(voltarButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cpfCliente = new JTextField();
        cpfCliente.setBackground(new Color(-2500135));
        frame.add(cpfCliente, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-1967873));
        label1.setText("CPF do cliente:");
        frame.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-1967873));
        label2.setText("Consultar conta");
        frame.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dadosConta = new JPanel();
        dadosConta.setLayout(new GridLayoutManager(3, 4, new Insets(8, 8, 8, 8), -1, -1));
        dadosConta.setBackground(new Color(-14134075));
        dadosConta.setEnabled(false);
        dadosConta.setOpaque(true);
        dadosConta.setVisible(false);
        frame.add(dadosConta, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dadosConta.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        numeroConta = new JLabel();
        numeroConta.setForeground(new Color(-1967873));
        numeroConta.setText("Conta Nº: 12345");
        dadosConta.add(numeroConta, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        boxSaldo = new JPanel();
        boxSaldo.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        boxSaldo.setBackground(new Color(-16442309));
        boxSaldo.setOpaque(true);
        boxSaldo.setVisible(true);
        dadosConta.add(boxSaldo, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(85, 20), new Dimension(85, 20), 0, false));
        saldo = new JLabel();
        saldo.setEnabled(true);
        saldo.setFocusable(true);
        saldo.setForeground(new Color(-1967873));
        saldo.setHorizontalAlignment(0);
        saldo.setHorizontalTextPosition(0);
        saldo.setName("");
        saldo.setOpaque(false);
        saldo.setText("R$8000,00");
        saldo.setVerifyInputWhenFocusTarget(true);
        saldo.setVisible(false);
        boxSaldo.add(saldo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mostrarSaldo = new JCheckBox();
        mostrarSaldo.setBackground(new Color(-14134075));
        mostrarSaldo.setText("");
        mostrarSaldo.setToolTipText("Ver saldo");
        dadosConta.add(mostrarSaldo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setBackground(new Color(-12611640));
        dadosConta.add(tabbedPane1, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        sacar = new JPanel();
        sacar.setLayout(new GridLayoutManager(3, 2, new Insets(4, 4, 4, 4), -1, -1));
        sacar.setBackground(new Color(-16442309));
        tabbedPane1.addTab("Sacar", sacar);
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-1967873));
        label3.setText("Informe o valor que você deseja sacar:");
        sacar.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        sacar.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        valorSaque = new JTextField();
        valorSaque.setBackground(new Color(-2500135));
        sacar.add(valorSaque, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        sacar.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        saqueButton = new JButton();
        saqueButton.setBackground(new Color(-11487489));
        saqueButton.setText("Realizar saque");
        sacar.add(saqueButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        depositar = new JPanel();
        depositar.setLayout(new GridLayoutManager(3, 2, new Insets(4, 4, 4, 4), -1, -1));
        depositar.setBackground(new Color(-16442309));
        tabbedPane1.addTab("Depositar", depositar);
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-1967873));
        label4.setText("Informe o valor que você deseja depositar:");
        depositar.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        depositar.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        valorDeposito = new JTextField();
        valorDeposito.setBackground(new Color(-2500135));
        depositar.add(valorDeposito, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        depositar.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        depositoButton = new JButton();
        depositoButton.setBackground(new Color(-11487489));
        depositoButton.setText("Realizar depósito");
        depositar.add(depositoButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        investir = new JPanel();
        investir.setLayout(new GridLayoutManager(5, 2, new Insets(4, 4, 4, 4), -1, -1));
        investir.setBackground(new Color(-16442309));
        tabbedPane1.addTab("Investir", investir);
        tipoConta = new JLabel();
        tipoConta.setBackground(new Color(-1967873));
        tipoConta.setForeground(new Color(-1967873));
        tipoConta.setText("Sua conta é do tipo: Investimento");
        investir.add(tipoConta, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        investir.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        rendimento = new JLabel();
        rendimento.setForeground(new Color(-1967873));
        rendimento.setText("Seu rendimento é de: 102%");
        investir.add(rendimento, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        investirButton = new JButton();
        investirButton.setBackground(new Color(-11487489));
        investirButton.setText("Investir agora");
        investir.add(investirButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        investir.add(spacer6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        investir.add(spacer7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        dadosConta.add(spacer8, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        warningLimite = new JLabel();
        warningLimite.setForeground(new Color(-2752502));
        warningLimite.setText("Usando limite");
        warningLimite.setToolTipText("O saldo atual da sua conta é R$0,00. Estamos considerando seu limite");
        warningLimite.setVisible(false);
        dadosConta.add(warningLimite, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buscarButton = new JButton();
        buscarButton.setBackground(new Color(-11487489));
        buscarButton.setText("Buscar");
        frame.add(buscarButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        frame.add(spacer9, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return frame;
    }

}
