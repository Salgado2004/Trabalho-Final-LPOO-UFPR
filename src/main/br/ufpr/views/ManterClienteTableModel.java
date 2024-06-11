/**
 * Classe ManterClienteTableModel.
 * Esta classe é um modelo de tabela personalizado para exibir uma lista de clientes em uma tabela.
 *
 * A classe estende AbstractTableModel e sobrescreve os seguintes métodos:
 * - getRowCount(): retorna o número de linhas na tabela.
 * - getColumnCount(): retorna o número de colunas na tabela.
 * - getValueAt(int rowIndex, int columnIndex): retorna o valor na célula especificada por rowIndex e columnIndex.
 * - getColumnName(int column): retorna o nome da coluna na posição especificada.
 * - getColumnClass(int columnIndex): retorna a classe que representa o tipo de coluna.
 */
public class ManterClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes = new ArrayList<>();
    private final String[] columns = {"Nome", "Sobrenome", "Endereço", "CPF", "RG"};

    /**
     * Construtor da classe ManterClienteTableModel.
     *
     * @param clientes a lista de clientes a ser exibida na tabela
     */
    public ManterClienteTableModel(List<Cliente> clientes){
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getNome();
            case 1:
                return cliente.getSobrenome();
            case 2:
                String adress = (cliente.getEndereco().getLogradouro() +", " + cliente.getEndereco().getNumero());
                return adress;
            case 3:
                return cliente.getCpf();
            case 4:
                return cliente.getRg();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(getValueAt(0, columnIndex) != null)
            return getValueAt(0, columnIndex).getClass();
        return Object.class;
    }
}