package br.ufpr.lpoo.models.connection;

import br.ufpr.lpoo.models.Conta;

public interface ContaDao extends Dao<Conta> {
    Conta getByNumero(String numero);
    Conta getByCliente(String cpfCliente);
}