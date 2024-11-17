package br.ufpr.lpoo.models.connection.mysql;

import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.ContaCorrente;
import br.ufpr.lpoo.models.ContaInvestimento;
import br.ufpr.lpoo.models.connection.ConnectionFactory;
import br.ufpr.lpoo.models.connection.ContaInvestimentoDao;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContaInvestimentoDaoMysql implements ContaInvestimentoDao {
    @Override
    public Conta getByNumero(String numero) {
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement sql = con.prepareStatement("SELECT * FROM conta INNER JOIN containvestimento ON conta.numero = containvestimento.numeroConta WHERE conta.numero = ?;")){
            sql.setInt(1, Integer.parseInt(numero));
            ResultSet rs = sql.executeQuery();
            if(rs.next()){
                Cliente dono = new ClienteDaoMysql().getByCpf(rs.getString("cpfCliente"));
                ContaInvestimento conta = new ContaInvestimento(dono, rs.getDouble("saldo"), rs.getDouble("depositoMinimo"), rs.getDouble("montanteMinimo"), rs.getDouble("depositoInicial"));
                conta.setNumero(rs.getInt("numero"));
                return conta;
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Conta getByCliente(String cpfCliente) {
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement sql = con.prepareStatement("SELECT * FROM conta INNER JOIN containvestimento ON conta.numero = containvestimento.numeroConta WHERE conta.cpfCliente = ?;")){
            sql.setString(1, cpfCliente);
            ResultSet rs = sql.executeQuery();
            if(rs.next()){
                Cliente dono = new ClienteDaoMysql().getByCpf(rs.getString("cpfCliente"));
                ContaInvestimento conta = new ContaInvestimento(dono, rs.getDouble("saldo"), rs.getDouble("depositoMinimo"), rs.getDouble("montanteMinimo"), rs.getDouble("depositoInicial"));
                conta.setNumero(rs.getInt("numero"));
                return conta;
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public void create(Conta conta) throws Exception {
        try(Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);

            try(PreparedStatement sql = con.prepareStatement("INSERT INTO conta (cpfCliente, saldo) VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)){
                sql.setString(1, conta.getDono().getCpf());
                sql.setDouble(2, conta.getSaldo());
                sql.executeUpdate();
                ResultSet rs = sql.getGeneratedKeys();
                rs.next();
                conta.setNumero(rs.getInt(1));
            }
            try(PreparedStatement sql = con.prepareStatement("INSERT INTO containvestimento (numeroConta, depositoInicial, depositoMinimo, montanteMinimo) VALUES (?, ?, ?, ?);")){
                sql.setInt(1, conta.getNumero());
                ContaInvestimento contaInvestimento = (ContaInvestimento) conta;
                sql.setDouble(2, contaInvestimento.getDepositoInicial());
                sql.setDouble(3, contaInvestimento.getDepositoMinimo());
                sql.setDouble(4, contaInvestimento.getMontanteMinimo());
                sql.executeUpdate();
            }

            con.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Conta conta) throws Exception {
        try(Connection con = ConnectionFactory.getConnection()){
            con.setAutoCommit(false);

            try(PreparedStatement sql = con.prepareStatement("UPDATE conta SET saldo = ? WHERE numero = ?;")){
                sql.setDouble(1, conta.getSaldo());
                sql.setInt(2, conta.getNumero());
                sql.executeUpdate();
            }
            try(PreparedStatement sql = con.prepareStatement("UPDATE containvestimento SET depositoMinimo = ?, montanteMinimo = ? WHERE numeroConta = ?;")){
                sql.setDouble(1, ((ContaInvestimento) conta).getDepositoMinimo());
                sql.setDouble(2, ((ContaInvestimento) conta).getMontanteMinimo());
                sql.setInt(3, conta.getNumero());
                sql.executeUpdate();
            }

            con.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Conta conta) throws Exception {
        try(Connection con = ConnectionFactory.getConnection()){
            con.setAutoCommit(false);

            try(PreparedStatement sql = con.prepareStatement("DELETE FROM containvestimento WHERE numeroConta = ?;")){
                sql.setInt(1, conta.getNumero());
                sql.executeUpdate();
            }
            try(PreparedStatement sql = con.prepareStatement("DELETE FROM conta WHERE numero = ?;")){
                sql.setInt(1, conta.getNumero());
                sql.executeUpdate();
            }

            con.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Conta> readAll() throws Exception {
        List<Conta> contas = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement sql = con.prepareStatement("SELECT * FROM conta INNER JOIN containvestimento ON conta.numero = containvestimento.numeroConta;")){
            ResultSet rs = sql.executeQuery();
            while(rs.next()){
                Cliente dono = new ClienteDaoMysql().getByCpf(rs.getString("cpfCliente"));
                ContaInvestimento conta = new ContaInvestimento(dono, rs.getDouble("saldo"), rs.getDouble("depositoMinimo"), rs.getDouble("montanteMinimo"), rs.getDouble("depositoInicial"));
                conta.setNumero(rs.getInt("numero"));
                contas.add(conta);
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return contas;
    }
}
