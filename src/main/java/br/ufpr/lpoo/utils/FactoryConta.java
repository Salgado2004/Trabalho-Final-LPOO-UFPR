package br.ufpr.lpoo.utils;

import br.ufpr.lpoo.controllers.Sistema;
import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.ContaCorrente;
import br.ufpr.lpoo.models.ContaInvestimento;

import java.util.ArrayList;

public class FactoryConta {
    Conta conta;

    public void abrirConta(String tipo, Cliente dono, ArrayList<Double> valoresIniciais){
        if(tipo.equals("Conta Corrente")){
            conta = new ContaCorrente(dono, valoresIniciais.get(0), valoresIniciais.get(1));
        } else if(tipo.equals("Conta Investimento")){
            conta = new ContaInvestimento(dono, valoresIniciais.get(0), valoresIniciais.get(1), valoresIniciais.get(2));
        }
        dono.setConta(this.conta);
        Sistema.cadastrarConta(this.conta);
    }
}
