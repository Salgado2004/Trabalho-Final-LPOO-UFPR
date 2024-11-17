package br.ufpr.lpoo.models.connection.mysql;

import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.Endereco;
import br.ufpr.lpoo.models.connection.ClienteDao;
import br.ufpr.lpoo.models.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoMysql implements ClienteDao {

    @Override
    public Cliente getByCpf(String cpf) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement sql = con.prepareStatement("SELECT * FROM cliente JOIN endereco ON endereco.id = cliente.idEndereco WHERE cpf = ?;")) {
            sql.setString(1, cpf);
            ResultSet list = sql.executeQuery();
            if (list.next()) {
                Endereco e = new Endereco(list.getString("logradouro"), list.getString("bairro"), list.getString("numero"), list.getString("cidade"));
                return new Cliente(list.getString("nome"), list.getString("sobrenome"), e, list.getString("cpf"), list.getString("rg"));
            }
            list.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Cliente> search(String query) throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement sql = con.prepareStatement("SELECT * FROM cliente JOIN endereco ON endereco.id = cliente.idEndereco WHERE cpf = ? OR nome LIKE '%" + query + "%' OR sobrenome LIKE '%" + query + "%';")) {
            sql.setString(1, query);
            ResultSet list = sql.executeQuery();
            while (list.next()) {
                Endereco e = new Endereco(list.getInt("idEndereco"), list.getString("logradouro"), list.getString("bairro"), list.getString("numero"), list.getString("cidade"));
                Cliente c = new Cliente(list.getString("nome"), list.getString("sobrenome"), e, list.getString("cpf"), list.getString("rg"));
                if (list.getString("numeroConta") != null) {
                    Conta conta = new ContaDaoMysql().getByNumero(list.getString("numeroConta"));
                    if (conta != null) {
                        c.setConta(conta);
                    }
                }
                clientes.add(c);
            }
            list.close();
        }
        return clientes;
    }

    @Override
    public void addConta(Cliente cliente) throws Exception {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement sql = con.prepareStatement("UPDATE cliente SET numeroConta = ? WHERE cpf = ?")) {
            sql.setInt(1, cliente.getConta().getNumero());
            sql.setString(2, cliente.getCpf());
            sql.executeUpdate();
        }
    }

    @Override
    public void removeConta(Cliente cliente) throws Exception {
        try (Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement sql = con.prepareStatement("UPDATE cliente SET numeroConta = NULL WHERE cpf = ?")) {
                sql.setString(1, cliente.getCpf());
                sql.executeUpdate();
            }
            con.commit();

            switch (cliente.getConta().getClass().getSimpleName()) {
                case "ContaCorrente" -> new ContaCorrenteDaoMysql().delete(cliente.getConta());
                case "ContaInvestimento" -> new ContaInvestimentoDaoMysql().delete(cliente.getConta());
            }

            cliente.setConta(null);
        }
    }

    @Override
    public void create(Cliente cliente) throws Exception {
        try (Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement sql = con.prepareStatement("INSERT INTO endereco (logradouro, bairro, numero, cidade) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS)) {
                sql.setString(1, cliente.getEndereco().getLogradouro());
                sql.setString(2, cliente.getEndereco().getBairro());
                sql.setInt(3, cliente.getEndereco().getNumero());
                sql.setString(4, cliente.getEndereco().getCidade());
                sql.executeUpdate();
                ResultSet rs = sql.getGeneratedKeys();
                if (rs.next()) {
                    cliente.getEndereco().setId(rs.getInt(1));
                } else {
                    throw new RuntimeException("Erro na criação de endereço");
                }
            }

            try (PreparedStatement sql = con.prepareStatement("INSERT INTO cliente (cpf, nome, sobrenome, rg, idEndereco) VALUES (?,?,?,?,?);");) {
                sql.setString(1, cliente.getCpf());
                sql.setString(2, cliente.getNome());
                sql.setString(3, cliente.getSobrenome());
                sql.setString(4, cliente.getRg());
                sql.setInt(5, cliente.getEndereco().getId());
                sql.executeUpdate();
            }
            
            con.commit();
        }
    }

    @Override
    public void update(Cliente cliente) throws Exception {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement sql = con.prepareStatement("UPDATE cliente SET nome = ?, sobrenome = ?, rg = ? WHERE cpf = ?")) {
            sql.setString(1, cliente.getNome());
            sql.setString(2, cliente.getSobrenome());
            sql.setString(3, cliente.getRg());
            sql.setString(4, cliente.getCpf());
            sql.executeUpdate();
        }
    }

    @Override
    public void delete(Cliente cliente) throws Exception {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement sql = con.prepareStatement("DELETE FROM cliente WHERE cpf = ?")) {
            sql.setString(1, cliente.getCpf());
            sql.executeUpdate();
        }
    }

    @Override
    public List<Cliente> readAll() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement sql = con.prepareStatement("SELECT * FROM cliente JOIN endereco ON endereco.id = cliente.idEndereco;")) {
            ResultSet list = sql.executeQuery();
            while (list.next()) {
                Endereco e = new Endereco(list.getInt("idEndereco"), list.getString("logradouro"), list.getString("bairro"), list.getString("numero"), list.getString("cidade"));
                Cliente c = new Cliente(list.getString("nome"), list.getString("sobrenome"), e, list.getString("cpf"), list.getString("rg"));
                if (list.getString("numeroConta") != null) {
                    Conta conta = new ContaDaoMysql().getByNumero(list.getString("numeroConta"));
                    if (conta != null) {
                        c.setConta(conta);
                    }
                }
                clientes.add(c);
            }
            list.close();
        }
        return clientes;
    }
}
