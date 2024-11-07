package br.ufpr.lpoo.models.connection;

import br.ufpr.lpoo.models.Cliente;

public interface ClienteDao extends Dao<Cliente>{
    Cliente getByCpf(String cpf);
}
