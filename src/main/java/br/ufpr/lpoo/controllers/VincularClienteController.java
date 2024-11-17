package br.ufpr.lpoo.controllers;

import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.connection.ClienteDao;
import br.ufpr.lpoo.models.connection.DaoFactory;
import br.ufpr.lpoo.models.connection.DaoType;
import br.ufpr.lpoo.utils.FactoryConta;
import br.ufpr.lpoo.views.VincularCliente;
import br.ufpr.lpoo.views.VincularTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static br.ufpr.lpoo.models.connection.DaoType.MYSQL;

public class VincularClienteController {

    private final VincularCliente view;
    private final VincularTableModel tabelaModel;
    private static final DaoType type = MYSQL;
    private String tipoSelecionado;
    private Cliente clienteSelecionado;

    public VincularClienteController(VincularCliente view){
        this.view = view;
        this.tabelaModel = new VincularTableModel(this.getClientes());
        this.view.initUIComponents();
        this.view.setController(this.tabelaModel);
    }

    /**
     * Método que retorna a lista de clientes
     * @return List<Cliente>
     */
    public List<Cliente> getClientes(){
        try {
            ClienteDao clienteDao = DaoFactory.getClienteDao(type);
            return clienteDao.readAll();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void abrirConta(ArrayList<Double> campos) throws Exception {
        FactoryConta factory = new FactoryConta();
        factory.abrirConta(this.tipoSelecionado, this.clienteSelecionado, campos);

        this.tabelaModel.updateList(this.getClientes());
    }

    public void deleteConta() throws Exception {
        ClienteDao clienteDao = DaoFactory.getClienteDao(type);
        clienteDao.removeConta(this.clienteSelecionado);

        this.tabelaModel.updateList(this.getClientes());
    }

    public Cliente getClienteSelecionado() {
        return this.clienteSelecionado;
    }

    public void selectTipo(String tipo){
        this.tipoSelecionado = tipo;
        this.view.loadFormAbertura(tipo);
    }

    public void selectCliente(int index){
        if (index == -1) return;
        this.clienteSelecionado = this.tabelaModel.getClientAt(index);
        this.view.switchForms(this.clienteSelecionado.getConta());
    }

    /**
     * Valida se todos os campos necessários estão preenchidos corretamente e, se estiver, retorna um array com os valores dos campos.
     *
     * @param campos ArrayList de campos de texto a serem verificados.
     * @return ArrayList com os valores dos campos.
     * @throws NumberFormatException    Se um dos campos não for um número válido.
     * @throws IllegalArgumentException Se um dos campos estiver vazio.
     */
    public ArrayList<Double> validaCampos(ArrayList<JTextField> campos) {
        ArrayList<Double> valores = new ArrayList<>();
        for (JTextField campo : campos) {
            if (!campo.isVisible()) continue;
            if (campo.getText().isEmpty()) {
                throw new IllegalArgumentException("Preencha todos os campos");
            }
            try {
                valores.add(Double.parseDouble(campo.getText()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Valor '" + campo.getText() + "' inválido");
            }
        }
        return valores;
    }
}
