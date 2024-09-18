package br.ufpr.lpoo.models;

import org.junit.Test;

public class TesteConta {

    private Endereco criarEndereco() {
        return new Endereco("Av. das Torres", "Nao Sei", "132", "Curitiba");
    }

    private Cliente criarCliente() {
        return new Cliente("Tester", "Teste", criarEndereco(), "77191328070", "232215492");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeNumeroNegativo() {
        Cliente dono = criarCliente();
        new ContaCorrente(-1, dono, 1000, 600);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeSaldoNegativo() {
        Cliente dono = criarCliente();
        new ContaCorrente(1, dono, -1000, 600);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeLimiteNegativo() {
        Cliente dono = criarCliente();
        new ContaCorrente(1, dono, 1000, -600);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoNegativo() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(1, dono, 1000, 600);
        conta.deposita(-200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeSaqueNegativo() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(1, dono, 1000, 600);
        conta.saca(-200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoMinimoNegativo() {
        Cliente dono = criarCliente();
        new ContaInvestimento(1, dono, 1000, -500, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeMontanteMinimoNegativo() {
        Cliente dono = criarCliente();
        new ContaInvestimento(1, dono, 1000, 500, -1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoMinimoZero() {
        Cliente dono = criarCliente();
        new ContaInvestimento(1, dono, 1000, 0, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeMontanteMinimoZero() {
        Cliente dono = criarCliente();
        new ContaInvestimento(1, dono, 1000, 500, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoMinimoMaiorQueSaldo() {
        Cliente dono = criarCliente();
        new ContaInvestimento(1, dono, 1000, 2000, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeMontanteMinimoMaiorQueSaldo() {
        Cliente dono = criarCliente();
        new ContaInvestimento(1, dono, 1000, 500, 2000);
    }

}
