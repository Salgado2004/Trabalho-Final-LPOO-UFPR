package br.ufpr.lpoo.views;

import br.ufpr.lpoo.models.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;

/**
 * Esta classe é um modelo de tabela para a visualização ManterCliente.
 * Ela estende AbstractTableModel e define as colunas e os dados da tabela.
 */
public class ManterClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes;
    private final String[] columns = {"Nome", "Sobrenome", "Endereço", "CPF", "RG"};

    /**
     * Construtor para a classe ManterClienteTableModel.
     * Define os valores iniciais para as variáveis de instância.
     *
     * @param clientes A lista de clientes a ser exibida na tabela.
     */
    public ManterClienteTableModel(List<Cliente> clientes){
        this.clientes = clientes;
    }

    public void updateList(List<Cliente> lista){
        this.clientes = lista;
        this.fireTableDataChanged();
    }

    public Cliente getClientAt(int index){
        return this.clientes.get(index);
    }

    /**
     * Este método retorna o número de linhas da tabela.
     *
     * @return O número de linhas da tabela.
     */
    @Override
    public int getRowCount() {
        return clientes.size();
    }

    /**
     * Este método retorna o número de colunas da tabela.
     *
     * @return O número de colunas da tabela.
     */
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * Este método retorna o valor na célula especificada.
     *
     * @param rowIndex    O índice da linha da célula.
     * @param columnIndex O índice da coluna da célula.
     * @return O valor na célula especificada.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cliente.getNome();
            case 1 -> cliente.getSobrenome();
            case 2 -> (cliente.getEndereco().getLogradouro() + ", " + cliente.getEndereco().getNumero());
            case 3 -> cliente.getCpf();
            case 4 -> cliente.getRg();
            default -> null;
        };
    }

    /**
     * Este método retorna o nome da coluna especificada.
     *
     * @param column O índice da coluna.
     * @return O nome da coluna especificada.
     */
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    /**
     * Este método retorna a classe do valor na coluna especificada.
     *
     * @param columnIndex O índice da coluna.
     * @return A classe do valor na coluna especificada.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(getValueAt(0, columnIndex) != null)
            return getValueAt(0, columnIndex).getClass();
        return Object.class;
    }

    /**
     * Esse método ordena a lista de clientes de acordo com o comparador fornecido.
     * @param comparador O comparador a ser usado para ordenar a lista de clientes.
     */
    public void sortClientes(Comparator comparador){
        clientes.sort(comparador);
        fireTableDataChanged();
    }

}