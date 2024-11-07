package br.ufpr.lpoo.models.connection;

import java.util.List;

public interface Dao<T> {
    void create(T object) throws Exception;
    void update(T object) throws Exception;
    void delete(T object) throws Exception;
    List<T> readAll() throws Exception;
}
