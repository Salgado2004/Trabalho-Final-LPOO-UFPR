package br.ufpr.lpoo.models;

import org.junit.Test;
import static org.junit.Assert.*;
public class TesteCliente {


    @Test
    public void instaciaCliente() {
        Endereco endereco = new Endereco("Rua Carlos Maia", "Cajuru", "123", "Curitiba");
        Cliente cliente = new Cliente("João Dória", "Silva", endereco, "400.855.360-67", "123456789");
        assertEquals("JOÃO DÓRIA", cliente.getNome());
        assertEquals("SILVA", cliente.getSobrenome());
        assertEquals("40085536067", cliente.getCpf());
        assertEquals("123456789", cliente.getRg());
    }

    @Test
    public void alteraCampos() {
        Endereco endereco = new Endereco("Rua Pedro Ivo", "Centro", "456", "Curitiba");
        Cliente cliente = new Cliente("João", "Silva", endereco, "10674940067", "123456789");
        cliente.setNome("Maria");
        cliente.setSobrenome("Pereira");
        cliente.setRg("987654321");

        assertEquals("MARIA", cliente.getNome());
        assertEquals("PEREIRA", cliente.getSobrenome());
        assertEquals("987654321", cliente.getRg());
    }

    @Test (expected = IllegalArgumentException.class)
    public void nomeInvalido() {
        Endereco endereco = new Endereco("Rua Carlos Maia", "Cajuru", "123", "Curitiba");
        Cliente cliente = new Cliente("João1", "Silva", endereco, "171.123.150-93", "123456789");
    }

    @Test (expected = IllegalArgumentException.class)
    public void sobrenomeInvalido() {
        Endereco endereco = new Endereco("Rua Pedro Ivo", "Centro", "456", "Curitiba");
        Cliente cliente = new Cliente("João", "Silva21", endereco, "945.091.120-76", "123456789");
    }

    @Test (expected = IllegalArgumentException.class)
    public void cpfInvalido() {
        Endereco endereco = new Endereco("Rua Carlos Maia", "Cajuru", "123", "Curitiba");
        Cliente cliente = new Cliente("João", "Silva", endereco, "11111111111", "123456789");
    }




}
