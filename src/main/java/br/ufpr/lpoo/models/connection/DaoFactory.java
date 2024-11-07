package br.ufpr.lpoo.models.connection;

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

    public static ContaCorrenteDao getContaCorrenteDao(DaoType type) throws OperationNotSupportedException {
        return switch (type) {
            case MYSQL -> new ContaCorrenteDaoMysql();
            case POSTGRES -> throw new OperationNotSupportedException("Postgres not implemented");
            case IN_MEMORY -> throw new OperationNotSupportedException("In memory not implemented");
        };
        return null;
    }

    public static ContaInvestimentoDao getContaInvestimentoDao(DaoType type) throws OperationNotSupportedException {
        return switch (type) {
            case MYSQL -> new ContaInvestimentoDaoMysql();
            case POSTGRES -> throw new OperationNotSupportedException("Postgres not implemented");
            case IN_MEMORY -> throw new OperationNotSupportedException("In memory not implemented");
        };
        return null;
    }
}
