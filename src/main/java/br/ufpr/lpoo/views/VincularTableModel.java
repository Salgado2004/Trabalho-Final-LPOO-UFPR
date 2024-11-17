package br.ufpr.lpoo.views;

import br.ufpr.lpoo.models.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Esta classe é um modelo de tabela para a visualização Vincular.
 * Ela estende AbstractTableModel e define as colunas e os dados da tabela.
 */
public class VincularTableModel extends AbstractTableModel {
    private List<Cliente> clientes;
    private final String[] colunas = {"Nome", "CPF", "Tipo da Conta", "Conta"};

    /**
     * Construtor para a classe VincularTableModel.
     * Define os valores iniciais para as variáveis de instância.
     *
     * @param clientes A lista de clientes a ser exibida na tabela.
     */
    public VincularTableModel(List<Cliente> clientes) {
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
        return colunas.length;
    }

    /**
     * Este método retorna o nome da coluna especificada.
     *
     * @param coluna O índice da coluna.
     * @return O nome da coluna especificada.
     */
    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
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
                return cliente.getNome() + " " + cliente.getSobrenome();
            case 1:
                return cliente.getCpf();
            case 2:
                if (cliente.getConta() == null)
                    return "Sem conta";
                else
                    return cliente.getConta().getClass().getSimpleName().replace("Conta", "Conta ");
            case 3:
                if (cliente.getConta() == null)
                    return "-";
                else {
                    return cliente.getConta().getNumero();
                }
            default:
                return null;
        }
    }

    /**
     * Este método retorna a classe do valor na coluna especificada.
     *
     * @param columnIndex O índice da coluna.
     * @return A classe do valor na coluna especificada.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null)
            return getValueAt(0, columnIndex).getClass();
        return Object.class;
    }
}