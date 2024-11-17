package br.ufpr.lpoo.models;

import org.junit.Assert;
import org.junit.Test;

public class TesteConta {

    private Endereco criarEndereco() {
        return new Endereco("Av. das Torres", "Nao Sei", "132", "Curitiba");
    }

    private Cliente criarCliente() {
        return new Cliente("Tester", "Teste", criarEndereco(), "77191328070", "232215492");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeSaldoNegativo() {
        Cliente dono = criarCliente();
        try {
            new ContaCorrente(dono, -1000, 600);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("O saldo deve ser maior que 0.", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeLimiteNegativo() {
        Cliente dono = criarCliente();
        try {
            new ContaCorrente(dono, 1000, -600);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Limite deve ser maior ou igual a 0.", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testeDepositoNegativo() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(dono, 1000, 600);
        conta.deposita(-200);
        Assert.assertEquals(1000, conta.getSaldo(), 0.01);
    }

    @Test()
    public void testeSaqueNegativo() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(dono, 1000, 600);
        conta.saca(-200);
        Assert.assertEquals(1000, conta.getSaldo(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoMinimoNegativo() {
        Cliente dono = criarCliente();
        try {
            new ContaInvestimento(dono, 1000, -500, 1000);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Depósito mínimo e montante mínimo devem ser maiores que 0.", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeMontanteMinimoNegativo() {
        Cliente dono = criarCliente();
        try {
            new ContaInvestimento(dono, 1000, 500, -1000);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Depósito mínimo e montante mínimo devem ser maiores que 0.", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoMinimoZero() {
        Cliente dono = criarCliente();
        try {
            new ContaInvestimento(dono, 1000, 0, 1000);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Depósito mínimo e montante mínimo devem ser maiores que 0.", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeMontanteMinimoZero() {
        Cliente dono = criarCliente();
        try {
            new ContaInvestimento(dono, 1000, 500, 0);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Depósito mínimo e montante mínimo devem ser maiores que 0.", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeDepositoMinimoMaiorQueSaldo() {
        Cliente dono = criarCliente();
        try {
            new ContaInvestimento(dono, 1000, 2000, 1000);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Saldo inicial menor que o depósito mínimo.", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeMontanteMinimoMaiorQueSaldo() {
        Cliente dono = criarCliente();
        try {
            new ContaInvestimento(dono, 1000, 500, 2000);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Saldo inicial menor que o montante mínimo.", e.getMessage());
            throw e;
        }
    }

}
