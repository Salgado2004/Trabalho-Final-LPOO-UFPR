package br.ufpr.lpoo.models.connection.mysql;

import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.ContaCorrente;
import br.ufpr.lpoo.models.connection.ConnectionFactory;
import br.ufpr.lpoo.models.connection.ContaCorrenteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContaCorrenteDaoMysql implements ContaCorrenteDao {

    @Override
    public Conta getByNumero(String numero) {
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement sql = con.prepareStatement("SELECT * FROM conta INNER JOIN contacorrente ON conta.numero = contacorrente.numeroConta WHERE conta.numero = ?;")){
            sql.setInt(1, Integer.parseInt(numero));
            ResultSet rs = sql.executeQuery();
            if(rs.next()){
                Cliente dono = new ClienteDaoMysql().getByCpf(rs.getString("cpfCliente"));
                ContaCorrente conta = new ContaCorrente(dono, rs.getDouble("saldo"), rs.getDouble("limite"), rs.getDouble("depositoInicial"));
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
            PreparedStatement sql = con.prepareStatement("SELECT * FROM conta INNER JOIN contacorrente ON conta.numero = contacorrente.numeroConta WHERE conta.cpfCliente = ?;")){
            sql.setString(1, cpfCliente);
            ResultSet rs = sql.executeQuery();
            if(rs.next()){
                Cliente dono = new ClienteDaoMysql().getByCpf(rs.getString("cpfCliente"));
                ContaCorrente conta = new ContaCorrente(dono, rs.getDouble("saldo"), rs.getDouble("limite"), rs.getDouble("depositoInicial"));
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
            try(PreparedStatement sql = con.prepareStatement("INSERT INTO contacorrente (numeroConta, depositoInicial, limite) VALUES (?, ?, ?);")){
                sql.setInt(1, conta.getNumero());
                ContaCorrente contaCorrente = (ContaCorrente) conta;
                sql.setDouble(2, contaCorrente.getDepositoInicial());
                sql.setDouble(3, contaCorrente.getLimite());
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
            try(PreparedStatement sql = con.prepareStatement("UPDATE contacorrente SET limite = ? WHERE numeroConta = ?;")){
                sql.setDouble(1, ((ContaCorrente) conta).getLimite());
                sql.setInt(2, conta.getNumero());
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

            try(PreparedStatement sql = con.prepareStatement("DELETE FROM contacorrente WHERE numeroConta = ?;")){
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
        PreparedStatement sql = con.prepareStatement("SELECT * FROM conta INNER JOIN contacorrente ON conta.numero = contacorrente.numeroConta;")){
            ResultSet rs = sql.executeQuery();
            while(rs.next()){
                Cliente dono = new ClienteDaoMysql().getByCpf(rs.getString("cpfCliente"));
                ContaCorrente conta = new ContaCorrente(dono, rs.getDouble("saldo"), rs.getDouble("limite"), rs.getDouble("depositoInicial"));
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
