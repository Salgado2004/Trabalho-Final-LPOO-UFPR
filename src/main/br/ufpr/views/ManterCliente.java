/**
 * Classe responsável pela manutenção dos clientes no sistema.
 * Permite operações de inserção, busca, edição e exclusão de clientes.
 */
public class ManterCliente {
    private JTable tabelaClientes;
    private TabelaModel tabelaModel;
    private JButton voltarButton;
    private JButton inserirButton;
    private JButton buscarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JTextField textFieldNome;
    private JTextField textFieldSobrenome;
    private JTextField textFieldLogradouro;
    private JTextField textFieldBairro;
    private JTextField textFieldCidade;
    private JTextField textFieldNumero;
    private JTextField textFieldCPF;
    private JTextField textFieldRG;
    private JPanel frame;

    /**
     * Construtor da classe ManterCliente.
     * Configura a tabela de clientes e adiciona listeners aos botões de ação.
     */
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
    }

    /**
     * Verifica se o CPF já está cadastrado no sistema.
     * @param cpf CPF a ser verificado.
     * @return true se o CPF já estiver cadastrado, false caso contrário.
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
     * Verifica se uma string é um número inteiro.
     * @param str String a ser verificada.
     * @return true se a string não for um número inteiro, false caso contrário.
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
     * Verifica se os campos de texto estão preenchidos.
     * @param textField Campo de texto a ser verificado.
     * @param campo Nome do campo para exibir mensagem de aviso.
     * @return true se o campo estiver preenchido, false caso contrário.
     */
    public static boolean areTextFieldsFilled(JTextField textField, String campo) {
        if (textField.getText().trim().isEmpty()) {
            Mensagens.aviso(null, "Campo " + campo + " está vazio\n");
            return false;
        }
        return true;
    }

    /**
     * Valida o CPF.
     * @param cpf CPF a ser validado.
     * @return true se o CPF for válido, false caso contrário.
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
     * Retorna o painel principal da tela.
     * @return JPanel
     */
    public JPanel getFrame() {
        return frame;
    }
}
