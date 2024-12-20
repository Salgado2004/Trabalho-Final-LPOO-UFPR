package br.ufpr.lpoo.models.connection;

import br.ufpr.lpoo.models.connection.mysql.*;

import javax.naming.OperationNotSupportedException;

public class DaoFactory {

    private DaoFactory(){}

    public static ClienteDao getClienteDao(DaoType type) throws OperationNotSupportedException {
        return switch (type) {
            case MYSQL -> new ClienteDaoMysql();
            case POSTGRES -> throw new OperationNotSupportedException("Postgres not implemented");
            case IN_MEMORY -> throw new OperationNotSupportedException("In memory not implemented");
        };
    }

    public static EnderecoDao getEnderecoDao(DaoType type) throws OperationNotSupportedException {
        return switch (type) {
            case MYSQL -> new EnderecoDaoMysql();
            case POSTGRES -> throw new OperationNotSupportedException("Postgres not implemented");
            case IN_MEMORY -> throw new OperationNotSupportedException("In memory not implemented");
        };
    }

    public static ContaDao getContaDao(DaoType type) throws OperationNotSupportedException {
        return switch (type) {
            case MYSQL -> new ContaDaoMysql();
            case POSTGRES -> throw new OperationNotSupportedException("Postgres not implemented");
            case IN_MEMORY -> throw new OperationNotSupportedException("In memory not implemented");
        };
    }
}
