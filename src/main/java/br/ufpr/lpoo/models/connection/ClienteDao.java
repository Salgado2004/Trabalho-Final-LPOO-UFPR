package br.ufpr.lpoo.models.connection;

import br.ufpr.lpoo.models.Cliente;

import java.util.List;

public interface ClienteDao extends Dao<Cliente>{
    Cliente getByCpf(String cpf);
    List<Cliente> search(String query) throws Exception;
}
