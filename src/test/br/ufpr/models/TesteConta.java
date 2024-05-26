package test.br.ufpr.models;

import main.br.ufpr.models.*;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void testeSaqueCorrente() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(1, dono, 1000, 600);

        assertTrue(conta.saca(200));
        assertFalse(conta.saca(750));

        conta.setLimite(10000);

        assertFalse(conta.saca(2000));
    }

    @Test
    public void testeDepositaCorrente() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(1, dono, 1000, 600);

        assertTrue(conta.deposita(200));
        assertFalse(conta.deposita(0));
        assertEquals(1200, conta.getSaldo(), 0);
    }

    @Test
    public void testeRemuneraCorrente() {
        Cliente dono = criarCliente();
        ContaCorrente conta = new ContaCorrente(1, dono, 1000, 600);

        conta.remunera();
        assertEquals(1010, conta.getSaldo(), 0);
    }

    @Test
    public void testeSaqueInvestimento() {
        Cliente dono = criarCliente();
        ContaInvestimento conta = new ContaInvestimento(1, dono, 1000, 500, 1000);

        assertTrue(conta.saca(200));
        assertFalse(conta.saca(1000));
    }

    @Test
    public void testeDepositaInvestimento() {
        Cliente dono = criarCliente();
        ContaInvestimento conta = new ContaInvestimento(1, dono, 1000, 500, 1000);

        assertTrue(conta.deposita(200));
        assertFalse(conta.deposita(0));
        assertEquals(1200, conta.getSaldo(), 0);
    }

    @Test
    public void testeRemuneraInvestimento() {
        Cliente dono = criarCliente();
        ContaInvestimento conta = new ContaInvestimento(1, dono, 1000, 500, 1000);

        conta.remunera();
        assertEquals(1020, conta.getSaldo(), 0);
    }
}
