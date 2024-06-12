package main.br.ufpr.views;

import main.br.ufpr.models.Cliente;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é um modelo de tabela para a visualização ManterCliente.
 * Ela estende AbstractTableModel e define as colunas e os dados da tabela.
 */
public class ManterClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes = new ArrayList<>();
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

}