/**
 * Classe Home.
 * Esta classe representa a tela inicial do sistema. Ela implementa a interface Tela.
 *
 * A classe possui os seguintes componentes:
 * - frame: o painel principal da tela.
 * - clientesButton: botão para navegar para a tela de gerenciamento de clientes.
 * - contasButton: botão para navegar para a tela de vinculação de clientes.
 * - transactionsButton: botão para navegar para a tela de manipulação de contas.
 *
 * A classe possui os seguintes métodos:
 * - getFrame(): retorna o painel principal da tela.
 */
public class Home implements Tela {
    private JPanel frame;
    private JButton clientesButton;
    private JButton contasButton;
    private JButton transactionsButton;

    /**
     * Construtor da classe Home.
     * Inicializa os componentes da tela e define os listeners dos botões.
     */
    public Home() {
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManterCliente frame = new ManterCliente();
                Sistema.navigate(frame);
            }
        });
        contasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VincularCliente frame = new VincularCliente();
                Sistema.navigate(frame);
            }
        });
        transactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManipularConta frame = new ManipularConta();
                Sistema.navigate(frame);
            }
        });
    }

    /**
     * Método para obter o painel principal da tela.
     *
     * @return o painel principal da tela
     */
    public JPanel getFrame() {
        return frame;
    }
}