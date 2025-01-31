package br.ufpr.lpoo.views;

import br.ufpr.lpoo.Sistema;
import br.ufpr.lpoo.controllers.*;
import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Endereco;
import br.ufpr.lpoo.models.Tela;
import br.ufpr.lpoo.models.comparables.*;
import br.ufpr.lpoo.utils.Imagens;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Comparator;
import java.util.Locale;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Esta classe representa a tela de manutenção do cliente.
 * Ela implementa a interface Tela e define os campos de entrada e botões para inserir, editar, excluir e buscar clientes.
 */
public class ManterCliente implements Tela {
    private JPanel frame;
    private JButton voltarButton;
    private final ManterClienteController controller;
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
    private JScrollPane scrollPanel;
    private JComboBox comboOrdem;
    private JTextField pesquisa;
    private JButton clearButton;
    private Comparator comparator;


    /**
     * Construtor para a classe ManterCliente.
     * Define os valores iniciais para as variáveis de instância e adiciona os ouvintes de ação aos botões.
     */
    public ManterCliente() {
        this.controller = new ManterClienteController(this);
    }

    @Override
    public void initUIComponents() {
        this.excluirButton.setIcon(Imagens.DELETE.icon());
        this.editarButton.setIcon(Imagens.EDIT.icon());
        this.buscarButton.setIcon(Imagens.SEARCH.icon());
        this.inserirButton.setIcon(Imagens.ADD.icon());

        this.scrollPanel.getViewport().setBackground(new Color(5, 28, 59));
        this.tabelaClientes.getTableHeader().setBackground(new Color(225, 248, 255));
    }

    public void setController(ManterClienteTableModel tabelaModel) {
        tabelaClientes.setModel(tabelaModel);
        comparator = new NomeComparator();
        tabelaModel.sortClientes(comparator);

        voltarButton.addActionListener(e -> Sistema.goBack());

        clearButton.addActionListener(e -> clearForm());

        inserirButton.addActionListener(e -> {
            try {
                Cliente c = loadForm();
                controller.cadastrarCliente(c);
                MensagensController.sucesso(frame, "Cliente cadastrado com sucesso");
                clearForm();
            } catch (SQLIntegrityConstraintViolationException ex) {
                System.out.println(ex.getMessage());
                if (ex.getMessage().contains("Duplicate entry")) {
                    MensagensController.erro(frame, "CPF já cadastrado");
                }
            } catch (Exception ex) {
                MensagensController.erro(frame, ex.getMessage());
            }
        });

        editarButton.addActionListener(e -> {
            try {
                Cliente cliente = loadForm();
                controller.updateCliente(cliente);
                MensagensController.sucesso(frame, "Informações editadas\n");
                clearForm();
            } catch (Exception ex) {
                MensagensController.erro(frame, "Não foi possível editar: " + ex.getMessage());
            }
        });

        excluirButton.addActionListener(e -> {
            String cpf = textFieldCPF.getText();
            if (MensagensController.confirmar(frame, "Certeza que deseja excluir o cliente?")) {
                try {
                    controller.deleteCliente(cpf);
                    MensagensController.sucesso(frame, "CPF '" + cpf + "' removido\n");
                } catch (RuntimeException ex) {
                    MensagensController.erro(frame, "Ocorreu um erro ao remover o cliente: " + ex.getMessage());
                }
            }
        });

        buscarButton.addActionListener(e -> {
            try {
                String query = pesquisa.getText();
                controller.buscarClientes(query);
            } catch (RuntimeException ex) {
                MensagensController.aviso(frame, ex.getMessage());
            }
        });

        comboOrdem.addItemListener(e -> {
            if (comboOrdem.getSelectedIndex() == 1) {
                comparator = new SobrenomeComparator();
            } else {
                comparator = new NomeComparator();
            }
            tabelaModel.sortClientes(comparator);
        });

        tabelaClientes.getSelectionModel().addListSelectionListener(e -> controller.selectCliente(tabelaClientes.getSelectedRow()));
    }

    public void loadCliente(Cliente cliente) {
        textFieldNome.setText(cliente.getNome());
        textFieldCPF.setText(cliente.getCpf());
        textFieldSobrenome.setText(cliente.getSobrenome());
        textFieldLogradouro.setText(cliente.getEndereco().getLogradouro());
        textFieldBairro.setText(cliente.getEndereco().getBairro());
        textFieldCidade.setText(cliente.getEndereco().getCidade());
        textFieldNumero.setText(Integer.toString(cliente.getEndereco().getNumero()));
        textFieldRG.setText(cliente.getRg());
    }

    public Cliente loadForm() {
        String nome = textFieldNome.getText();
        String sobreNome = textFieldSobrenome.getText();
        String logradouro = textFieldLogradouro.getText();
        String bairro = textFieldBairro.getText();
        String cidade = textFieldCidade.getText();
        String numero = textFieldNumero.getText();
        String cpf = textFieldCPF.getText();
        cpf = cpf.replaceAll("[^0-9]", "");
        String rg = textFieldRG.getText();

        this.areTextFieldsFilled(textFieldNome, "nome");
        this.areTextFieldsFilled(textFieldSobrenome, "sobrenome");
        this.areTextFieldsFilled(textFieldLogradouro, "logradouro");
        this.areTextFieldsFilled(textFieldBairro, "bairro");
        this.areTextFieldsFilled(textFieldCidade, "cidade");
        this.areTextFieldsFilled(textFieldRG, "RG");

        Endereco endereco = new Endereco(logradouro, bairro, numero, cidade);
        return new Cliente(nome, sobreNome, endereco, cpf, rg);
    }

    public void clearForm() {
        textFieldNome.setText("");
        textFieldSobrenome.setText("");
        textFieldLogradouro.setText("");
        textFieldBairro.setText("");
        textFieldCidade.setText("");
        textFieldNumero.setText("");
        textFieldCPF.setText("");
        textFieldRG.setText("");
    }

    /**
     * Verifica se um campo de texto está preenchido.
     *
     * @param textField O campo de texto a ser verificado.
     * @param campo     O nome do campo a ser verificado.
     */
    public void areTextFieldsFilled(JTextField textField, String campo) {
        if (textField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Campo " + campo + " não pode estar vazio\n");
        }
    }

    /**
     * Este método retorna o frame da tela.
     *
     * @return O frame da tela.
     */
    public JPanel getFrame() {
        return frame;
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
        frame.setLayout(new GridLayoutManager(6, 4, new Insets(8, 8, 8, 8), -1, -1));
        frame.setBackground(new Color(-16442309));
        frame.setEnabled(false);
        frame.setForeground(new Color(-1967873));
        frame.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1967873)), "Clientes", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-1967873)));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-16442309));
        panel1.setEnabled(false);
        panel1.setForeground(new Color(-1967873));
        frame.add(panel1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1967873)), "Endereço", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel1.getFont()), new Color(-1967873)));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-1967873));
        label1.setText("Logradouro:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 17), null, 0, false));
        textFieldLogradouro = new JTextField();
        textFieldLogradouro.setBackground(new Color(-2500135));
        textFieldLogradouro.setForeground(new Color(-16777216));
        panel1.add(textFieldLogradouro, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-1967873));
        label2.setText("Número:");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 17), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-1967873));
        label3.setText("Bairro:");
        panel1.add(label3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldBairro = new JTextField();
        textFieldBairro.setBackground(new Color(-2500135));
        textFieldBairro.setForeground(new Color(-16777216));
        panel1.add(textFieldBairro, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-1967873));
        label4.setText("Cidade:");
        panel1.add(label4, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldCidade = new JTextField();
        textFieldCidade.setBackground(new Color(-2500135));
        textFieldCidade.setForeground(new Color(-16777216));
        panel1.add(textFieldCidade, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldNumero = new JTextField();
        textFieldNumero.setBackground(new Color(-2500135));
        textFieldNumero.setForeground(new Color(-16777216));
        panel1.add(textFieldNumero, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        scrollPanel = new JScrollPane();
        scrollPanel.setBackground(new Color(-14134075));
        scrollPanel.setEnabled(true);
        frame.add(scrollPanel, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16442309)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tabelaClientes = new JTable();
        tabelaClientes.setBackground(new Color(-11487489));
        tabelaClientes.setEnabled(true);
        tabelaClientes.setForeground(new Color(-16777216));
        tabelaClientes.setGridColor(new Color(-16442309));
        tabelaClientes.setSelectionForeground(new Color(-14134075));
        tabelaClientes.setShowVerticalLines(false);
        tabelaClientes.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        tabelaClientes.putClientProperty("Table.isFileList", Boolean.FALSE);
        scrollPanel.setViewportView(tabelaClientes);
        voltarButton = new JButton();
        voltarButton.setBackground(new Color(-11487489));
        voltarButton.setText("Voltar");
        frame.add(voltarButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-1967873));
        label5.setText("Clientes cadastrados");
        frame.add(label5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-16442309));
        frame.add(panel2, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pesquisa = new JTextField();
        pesquisa.setBackground(new Color(-2500135));
        pesquisa.setForeground(new Color(-16777216));
        panel2.add(pesquisa, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buscarButton = new JButton();
        buscarButton.setBackground(new Color(-11487489));
        buscarButton.setText("Buscar");
        panel2.add(buscarButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-1967873));
        label6.setText("Ordenar por");
        panel2.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboOrdem = new JComboBox();
        comboOrdem.setBackground(new Color(-2500135));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("nome");
        defaultComboBoxModel1.addElement("sobrenome");
        comboOrdem.setModel(defaultComboBoxModel1);
        panel2.add(comboOrdem, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-16442309));
        frame.add(panel3, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        excluirButton = new JButton();
        excluirButton.setBackground(new Color(-11487489));
        excluirButton.setText("Excluir");
        panel3.add(excluirButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editarButton = new JButton();
        editarButton.setBackground(new Color(-11487489));
        editarButton.setText("Editar");
        panel3.add(editarButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-16442309));
        frame.add(panel4, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-1967873));
        label7.setText("Nome:");
        panel4.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldNome = new JTextField();
        textFieldNome.setBackground(new Color(-2500135));
        textFieldNome.setForeground(new Color(-16777216));
        panel4.add(textFieldNome, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setForeground(new Color(-1967873));
        label8.setText("Sobrenome:");
        panel4.add(label8, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldSobrenome = new JTextField();
        textFieldSobrenome.setBackground(new Color(-2500135));
        textFieldSobrenome.setForeground(new Color(-16777216));
        panel4.add(textFieldSobrenome, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setForeground(new Color(-1967873));
        label9.setText("CPF:");
        panel4.add(label9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldCPF = new JTextField();
        textFieldCPF.setBackground(new Color(-2500135));
        textFieldCPF.setDisabledTextColor(new Color(-16442309));
        textFieldCPF.setEditable(true);
        textFieldCPF.setEnabled(true);
        textFieldCPF.setForeground(new Color(-16777216));
        panel4.add(textFieldCPF, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setForeground(new Color(-1967873));
        label10.setText("RG:");
        panel4.add(label10, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldRG = new JTextField();
        textFieldRG.setBackground(new Color(-2500135));
        textFieldRG.setForeground(new Color(-16777216));
        panel4.add(textFieldRG, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-16442309));
        frame.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        inserirButton = new JButton();
        inserirButton.setBackground(new Color(-11487489));
        inserirButton.setText("Inserir");
        panel5.add(inserirButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearButton = new JButton();
        clearButton.setBackground(new Color(-11487489));
        clearButton.setText("Limpar");
        panel5.add(clearButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return frame;
    }

}
