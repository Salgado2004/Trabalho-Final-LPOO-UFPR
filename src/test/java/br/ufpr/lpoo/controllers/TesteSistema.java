package br.ufpr.lpoo.controllers;
import br.ufpr.lpoo.models.Cliente;
import br.ufpr.lpoo.models.Endereco;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TesteSistema {
    @Test
    public void adicionarCliente() {
        Endereco endereco = new Endereco("Rua Carlos Maia", "Cajuru", "123", "Curitiba");
        Cliente cliente = new Cliente("João Dória", "Silva", endereco, "400.855.360-67", "123456789");
        Sistema.cadastrarCliente(cliente);

        assertEquals(1, Sistema.getClientes().size());
        assertEquals(cliente, Sistema.getClientes().get(0));
    }

}
