package test.br.ufpr.models;
import main.br.ufpr.models.*;
import org.junit.Test;

import static org.junit.Assert.*;


public class TesteConta {
    @Test
   public void testeSaqueCorrente(){
        Endereco endereco = new Endereco("Av. das Torres", "Nao Sei", "132", "Curitiba");
        Cliente dono = new Cliente("Tester", "Teste", endereco,"77191328070","232215492");
        ContaCorrente conta = new ContaCorrente(1,dono,1000,600);

        assertTrue(conta.saca(200));
        assertFalse(conta.saca(750));

        conta.setLimite(10000);

        assertFalse(conta.saca(2000));
    }

    @Test
    public void testeDepositaCorrente(){
        Endereco endereco = new Endereco("Av. das Torres", "Nao Sei", "132", "Curitiba");
        Cliente dono = new Cliente("Tester", "Teste", endereco,"77191328070","232215492");
        ContaCorrente conta = new ContaCorrente(1,dono,1000,600);

        assertTrue(conta.deposita(200));
        assertFalse(conta.deposita(0));
        assertEquals(1200, conta.getSaldo(),0);

    }
}
