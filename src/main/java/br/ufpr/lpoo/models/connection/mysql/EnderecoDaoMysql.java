package br.ufpr.lpoo.models.connection.mysql;

import br.ufpr.lpoo.models.Endereco;
import br.ufpr.lpoo.models.connection.ConnectionFactory;
import br.ufpr.lpoo.models.connection.EnderecoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class EnderecoDaoMysql implements EnderecoDao {
    @Override
    public void create(Endereco endereco) throws Exception {
        throw new UnsupportedOperationException("Implementado no create to Cliente");
    }

    @Override
    public void update(Endereco endereco) throws Exception {
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement sql = con.prepareStatement("UPDATE endereco SET logradouro = ?, bairro = ?, numero = ?, cidade = ? WHERE id = ?;")) {
            con.setAutoCommit(false);
            sql.setString(1, endereco.getLogradouro());
            sql.setString(2, endereco.getBairro());
            sql.setInt(3, endereco.getNumero());
            sql.setString(4, endereco.getCidade());
            sql.setInt(5, endereco.getId());
            sql.executeUpdate();
            con.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Endereco endereco) throws Exception {
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement sql = con.prepareStatement("DELETE FROM endereco WHERE id = ?;")) {
            con.setAutoCommit(false);
            sql.setInt(1, endereco.getId());
            sql.executeUpdate();
            con.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Endereco> readAll() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Endereco getByClientId(int id) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
