package br.ufpr.lpoo.controllers;

import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Endereco;
import br.ufpr.lpoo.models.connection.*;
import br.ufpr.lpoo.views.ManterCliente;
import br.ufpr.lpoo.views.ManterClienteTableModel;

import java.util.List;

import static br.ufpr.lpoo.models.connection.DaoType.MYSQL;

public class ManterClienteController {

    private Cliente cliente;
    private final ManterCliente view;
    private final ManterClienteTableModel tabelaModel;
    private static final DaoType type = MYSQL;

    public ManterClienteController(ManterCliente view){
        this.view = view;
        this.tabelaModel = new ManterClienteTableModel(this.getClientes());
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

    /**
     * Método que retorna a lista de clientes filtrada pela query
     * @param query Um trecho do nome, sobrenome, ou CPF inteiro
     * @return List<Cliente>
     */
    public List<Cliente> getClientes(String query){
        try {
            ClienteDao clienteDao = DaoFactory.getClienteDao(type);
            return clienteDao.search(query);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void buscarClientes(String query){

        query = query.replaceAll("/[^a-zA-Z0-9]/g", "");

        if (query.isEmpty()) {
            this.tabelaModel.updateList(this.getClientes());
            throw new RuntimeException("Insira um CPF ou um segmento de nome para buscar");
        }

        List<Cliente> clientes = this.getClientes(query);

        if (clientes.isEmpty()) {
            throw new RuntimeException("Nenhum cliente encontrado");
        }

        this.tabelaModel.updateList(clientes);
    }

    /**
     * Método que cadastra um cliente na lista de clientes
     * @param cliente
     */
    public void cadastrarCliente(Cliente cliente) throws Exception {
        ClienteDao clienteDao = DaoFactory.getClienteDao(type);
        clienteDao.create(cliente);

        this.tabelaModel.updateList(this.getClientes());
    }

    public void updateCliente(Cliente cliente) throws Exception {

        if (this.wasAddressEdited(cliente.getEndereco())) {
            this.updateEndereco(cliente.getEndereco());
        }

        ClienteDao clienteDao = DaoFactory.getClienteDao(type);
        clienteDao.update(cliente);

        this.tabelaModel.updateList(this.getClientes());
    }

    public void updateEndereco(Endereco endereco) throws Exception {
        EnderecoDao enderecoDao = DaoFactory.getEnderecoDao(type);
        enderecoDao.update(endereco);
    }

    public void deleteCliente(String cpf) {
        try {
            ClienteDao clienteDao = DaoFactory.getClienteDao(type);
            Cliente cliente = clienteDao.search(cpf).get(0);
            if (cliente.getConta() != null) {
                clienteDao.removeConta(cliente);
            }
            clienteDao.delete(cliente);
            EnderecoDao enderecoDao = DaoFactory.getEnderecoDao(type);
            enderecoDao.delete(cliente.getEndereco());
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("Cliente não encontrado");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        tabelaModel.updateList(this.getClientes());
        this.view.clearForm();
    }

    public void selectCliente(int index){
        if (index == -1) {
            this.cliente = null;
            this.view.clearForm();
        }
        else {
            this.cliente = this.tabelaModel.getClientAt(index);
            this.view.loadCliente(this.cliente);
        }
    }

    public boolean wasAddressEdited(Endereco endereco) {
        return !endereco.equals(cliente.getEndereco());
    }
}
