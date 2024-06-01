package main.br.ufpr.views;

import main.br.ufpr.models.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VincularTableModel extends AbstractTableModel {
    private final List<Cliente> clientes;
    private final String[] colunas = {"Nome", "CPF", "Tipo da Conta", "Conta"};


    public VincularTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null)
            return getValueAt(0, columnIndex).getClass();
        return Object.class;
    }


}
