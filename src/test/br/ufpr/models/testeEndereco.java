package test.br.ufpr.models;
import main.br.ufpr.models.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class testeEndereco {
    @Test
    public void EnderecoValido() {
        Endereco endereco = new Endereco("Rua A", "Centro", "123", "Cidade A");
        assertEquals("RUA A", endereco.getLogradouro());
        assertEquals("CENTRO", endereco.getBairro());
        assertEquals(123, endereco.getNumero());
        assertEquals("CIDADE A", endereco.getCidade());
    }

    @Test(expected = IllegalArgumentException.class)
    public void numeroNegativo() {
        new Endereco("Rua B", "Centro", "-1", "Cidade B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void caracterNoNumero() {
        new Endereco("Rua B", "Centro", "abc", "Cidade B");
    }
}
