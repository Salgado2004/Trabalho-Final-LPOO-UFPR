package main.br.ufpr.models.comparables;

import main.br.ufpr.models.Cliente;

import java.util.Comparator;

/**
 * Esta classe é um comparador de objetos Cliente.
 * Ela define um método de comparação com base no sobrenome do cliente.
 */
public class SobrenomeComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        Cliente c1 = (Cliente) o1;
        Cliente c2 = (Cliente) o2;
        return c1.getSobrenome().compareTo(c2.getSobrenome());
    }
}
