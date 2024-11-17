package br.ufpr.lpoo.models.connection;

import br.ufpr.lpoo.models.Endereco;

public interface EnderecoDao extends Dao<Endereco>{
    Endereco getByClientId(int id);
}
