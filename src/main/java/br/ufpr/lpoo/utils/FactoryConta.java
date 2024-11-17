package br.ufpr.lpoo.utils;

import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.ContaCorrente;
import br.ufpr.lpoo.models.ContaInvestimento;
import br.ufpr.lpoo.models.connection.*;

import java.util.ArrayList;

import static br.ufpr.lpoo.models.connection.DaoType.MYSQL;

public class FactoryConta {
    Conta conta;
    private static final DaoType type = MYSQL;

    public void abrirConta(String tipo, Cliente dono, ArrayList<Double> valoresIniciais) throws Exception {
        if(tipo.equals("Conta Corrente")){
            conta = new ContaCorrente(dono, valoresIniciais.get(0), valoresIniciais.get(1));
        } else if(tipo.equals("Conta Investimento")){
            conta = new ContaInvestimento(dono, valoresIniciais.get(0), valoresIniciais.get(1), valoresIniciais.get(2));
        }
        ContaDao contaDao = DaoFactory.getContaDao(type);
        contaDao.create(conta);
        dono.setConta(this.conta);
        ClienteDao clienteDao = DaoFactory.getClienteDao(type);
        clienteDao.addConta(dono);
    }
}
