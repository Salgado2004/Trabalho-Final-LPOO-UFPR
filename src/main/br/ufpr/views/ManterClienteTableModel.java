package main.br.ufpr.views;

import main.br.ufpr.models.Cliente;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ManterClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes = new ArrayList<>();
    private final String[] columns = {"Nome", "Sobrenome", "Endereço", "CPF", "RG"};

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
