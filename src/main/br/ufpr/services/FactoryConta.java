package main.br.ufpr.services;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.Cliente;
import main.br.ufpr.models.Conta;
import main.br.ufpr.models.ContaCorrente;
import main.br.ufpr.models.ContaInvestimento;

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
