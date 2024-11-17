package br.ufpr.lpoo.models.connection.mysql;

import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.connection.ContaDao;

import java.util.ArrayList;
import java.util.List;

public class ContaDaoMysql implements ContaDao {
    @Override
    public Conta getByNumero(String numero) {
        Conta conta;
        try {
            ContaCorrenteDaoMysql contaCorrenteDaoMysql = new ContaCorrenteDaoMysql();
            conta = contaCorrenteDaoMysql.getByNumero(numero);
            if (conta == null) {
                ContaInvestimentoDaoMysql contaInvestimentoDaoMysql = new ContaInvestimentoDaoMysql();
                conta = contaInvestimentoDaoMysql.getByNumero(numero);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conta;
    }

    @Override
    public Conta getByCliente(String cpfCliente) {
        Conta conta;
        try {
            ContaCorrenteDaoMysql contaCorrenteDaoMysql = new ContaCorrenteDaoMysql();
            conta = contaCorrenteDaoMysql.getByCliente(cpfCliente);
            if (conta == null) {
                ContaInvestimentoDaoMysql contaInvestimentoDaoMysql = new ContaInvestimentoDaoMysql();
                conta = contaInvestimentoDaoMysql.getByCliente(cpfCliente);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conta;
    }

    @Override
    public void create(Conta object) throws Exception {
        switch (object.getClass().getSimpleName()) {
            case "ContaCorrente" -> new ContaCorrenteDaoMysql().create(object);
            case "ContaInvestimento" -> new ContaInvestimentoDaoMysql().create(object);
        }
    }

    @Override
    public void update(Conta conta) throws Exception {
        switch (conta.getClass().getSimpleName()) {
            case "ContaCorrente" -> new ContaCorrenteDaoMysql().update(conta);
            case "ContaInvestimento" -> new ContaInvestimentoDaoMysql().update(conta);
        }
    }

    @Override
    public void delete(Conta conta) throws Exception {
        switch (conta.getClass().getSimpleName()) {
            case "ContaCorrente" -> new ContaCorrenteDaoMysql().delete(conta);
            case "ContaInvestimento" -> new ContaInvestimentoDaoMysql().delete(conta);
        }
    }

    @Override
    public List<Conta> readAll() throws Exception {
        List<Conta> contas = new ArrayList<>();
        ContaCorrenteDaoMysql contaCorrenteDaoMysql = new ContaCorrenteDaoMysql();
        ContaInvestimentoDaoMysql contaInvestimentoDaoMysql = new ContaInvestimentoDaoMysql();
        contas.addAll(contaCorrenteDaoMysql.readAll());
        contas.addAll(contaInvestimentoDaoMysql.readAll());
        return contas;
    }
}
