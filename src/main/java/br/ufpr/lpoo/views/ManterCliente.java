package br.ufpr.lpoo.views;

import br.ufpr.lpoo.controllers.*;
import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Endereco;
import br.ufpr.lpoo.models.Tela;
import br.ufpr.lpoo.models.comparables.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.Locale;

/**
 * Esta classe representa a tela de manutenção do cliente.
 * Ela implementa a interface Tela e define os campos de entrada e botões para inserir, editar, excluir e buscar clientes.
 */
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
    private JScrollPane scrollPanel;
    private JComboBox comboOrdem;
    private Comparator comparator;
    private ManterClienteTableModel tabelaModel = new ManterClienteTableModel(Sistema.getClientes());


    /**
     * Construtor para a classe ManterCliente.
     * Define os valores iniciais para as variáveis de instância e adiciona os ouvintes de ação aos botões.
     */
    public ManterCliente() {
        this.initUIComponents();
        tabelaClientes.setModel(tabelaModel);
        tabelaClientes.setColumnModel(tabelaClientes.getColumnModel());

        comparator = new NomeComparator();
        tabelaModel.sortClientes(comparator);

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
                    Mensagens.erro(null, "Número Inválido\n");
                    return;
                }

                if (cpf.equals("")) {
                    Mensagens.erro(null, "Campo CPF não pode estar vazio\n");
                    return;
                }
                if (isCpfExistente(cpf)) {
                    Mensagens.erro(null, "CPF já cadastrado\n");
                    return;
                }

                if (!validaCpf(cpf)) {
                    Mensagens.erro(null, "CPF inválido\n");
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
                    Mensagens.aviso(null, "Insira um CPF para buscar\n");
                    return;
                }

                if (!isCpfExistente(cpf)) {
                    Mensagens.erro(null, "CPF não cadastrado\n");
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
                    Mensagens.erro(null, "Número Inválido\n");
                    return;
                }

                if (cpf.equals("")) {
                    Mensagens.aviso(null, "CPF não pode ser vazio\n");
                    return;
                }

                if (!validaCpf(cpf)) {
                    Mensagens.erro(null, "CPF Inválido\n");
                    return;
                }

                cpf = cpf.replaceAll("[^0-9]", "");

                for (Cliente cliente : Sistema.getClientes()) {
                    if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                        cliente.setNome(nome);
                        cliente.setSobrenome(sobreNome);
                        Endereco endereco = new Endereco(logradouro, bairro, numero, cidade);
                        cliente.setEndereco(endereco);
                        cliente.setRg(rg);
                    }
                }

                Mensagens.sucesso(null, "Informações editadas\n");

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

                cpf = cpf.replaceAll("[^0-9]", "");

                String finalCpf = cpf;

                Sistema.getClientes().removeIf(cliente -> cliente.getCpf().equalsIgnoreCase(finalCpf));

                Mensagens.sucesso(null, "CPF " + cpf + " removido\n");

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
        comboOrdem.addComponentListener(new ComponentAdapter() {
        });
        comboOrdem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboOrdem.getSelectedIndex() == 1) {
                    comparator = new SobrenomeComparator();
                } else {
                    comparator = new NomeComparator();
                }
                tabelaModel.sortClientes(comparator);
            }
        });
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

    /**
     * Verifica se um CPF já existe na lista de clientes.
     *
     * @param cpf O CPF a ser verificado.
     * @return Verdadeiro se o CPF já existe, falso caso contrário.
     */
    private boolean isCpfExistente(String cpf) {
        for (Cliente cliente : Sistema.getClientes()) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se uma string pode ser convertida para um número inteiro.
     *
     * @param str A string a ser verificada.
     * @return Verdadeiro se a string pode ser convertida para um número inteiro, falso caso contrário.
     */
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

    /**
     * Verifica se um campo de texto está preenchido.
     *
     * @param textField O campo de texto a ser verificado.
     * @param campo     O nome do campo a ser verificado.
     * @return Verdadeiro se o campo de texto está preenchido, falso caso contrário.
     */
    public static boolean areTextFieldsFilled(JTextField textField, String campo) {
        if (textField.getText().trim().isEmpty()) {
            Mensagens.aviso(null, "Campo " + campo + " está vazio\n");
            return false;
        }
        return true;
    }

    /**
     * Valida um CPF.
     *
     * @param cpf O CPF a ser validado.
     * @return Verdadeiro se o CPF é válido, falso caso contrário.
     */
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

    /**
     * Este método retorna o frame da tela.
     *
     * @return O frame da tela.
     */
    public JPanel getFrame() {
        return frame;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
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
        frame.setLayout(new GridLayoutManager(7, 4, new Insets(8, 8, 8, 8), -1, -1));
        frame.setBackground(new Color(-16442309));
        frame.setEnabled(false);
        frame.setForeground(new Color(-1967873));
        frame.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1967873)), "Clientes", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-1967873)));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-1967873));
        label1.setText("Nome:");
        frame.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-1967873));
        label2.setText("Sobrenome:");
        frame.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldNome = new JTextField();
        textFieldNome.setBackground(new Color(-2500135));
        textFieldNome.setForeground(new Color(-16777216));
        frame.add(textFieldNome, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldSobrenome = new JTextField();
        textFieldSobrenome.setBackground(new Color(-2500135));
        textFieldSobrenome.setForeground(new Color(-16777216));
        frame.add(textFieldSobrenome, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-16442309));
        panel1.setEnabled(false);
        panel1.setForeground(new Color(-1967873));
        frame.add(panel1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1967873)), "Endereço", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel1.getFont()), new Color(-1967873)));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-1967873));
        label3.setText("Logradouro:");
        panel1.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 17), null, 0, false));
        textFieldLogradouro = new JTextField();
        textFieldLogradouro.setBackground(new Color(-2500135));
        textFieldLogradouro.setForeground(new Color(-16777216));
        panel1.add(textFieldLogradouro, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-1967873));
        label4.setText("Número:");
        panel1.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 17), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-1967873));
        label5.setText("Bairro:");
        panel1.add(label5, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldBairro = new JTextField();
        textFieldBairro.setBackground(new Color(-2500135));
        textFieldBairro.setForeground(new Color(-16777216));
        panel1.add(textFieldBairro, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-1967873));
        label6.setText("Cidade:");
        panel1.add(label6, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldCidade = new JTextField();
        textFieldCidade.setBackground(new Color(-2500135));
        textFieldCidade.setForeground(new Color(-16777216));
        panel1.add(textFieldCidade, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldNumero = new JTextField();
        textFieldNumero.setBackground(new Color(-2500135));
        textFieldNumero.setForeground(new Color(-16777216));
        panel1.add(textFieldNumero, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-1967873));
        label7.setText("CPF:");
        frame.add(label7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldCPF = new JTextField();
        textFieldCPF.setBackground(new Color(-2500135));
        textFieldCPF.setForeground(new Color(-16777216));
        frame.add(textFieldCPF, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setForeground(new Color(-1967873));
        label8.setText("RG:");
        frame.add(label8, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldRG = new JTextField();
        textFieldRG.setBackground(new Color(-2500135));
        textFieldRG.setForeground(new Color(-16777216));
        frame.add(textFieldRG, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buscarButton = new JButton();
        buscarButton.setBackground(new Color(-11487489));
        buscarButton.setText("Buscar");
        frame.add(buscarButton, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPanel = new JScrollPane();
        scrollPanel.setBackground(new Color(-14134075));
        scrollPanel.setEnabled(true);
        frame.add(scrollPanel, new GridConstraints(5, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        inserirButton = new JButton();
        inserirButton.setBackground(new Color(-11487489));
        inserirButton.setText("Inserir");
        frame.add(inserirButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voltarButton = new JButton();
        voltarButton.setBackground(new Color(-11487489));
        voltarButton.setText("Voltar");
        frame.add(voltarButton, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setForeground(new Color(-1967873));
        label9.setText("Clientes cadastrados");
        frame.add(label9, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editarButton = new JButton();
        editarButton.setBackground(new Color(-11487489));
        editarButton.setText("Editar");
        frame.add(editarButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        excluirButton = new JButton();
        excluirButton.setBackground(new Color(-11487489));
        excluirButton.setText("Excluir");
        frame.add(excluirButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboOrdem = new JComboBox();
        comboOrdem.setBackground(new Color(-2500135));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("nome");
        defaultComboBoxModel1.addElement("sobrenome");
        comboOrdem.setModel(defaultComboBoxModel1);
        frame.add(comboOrdem, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setForeground(new Color(-1967873));
        label10.setText("Ordenar por");
        frame.add(label10, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
