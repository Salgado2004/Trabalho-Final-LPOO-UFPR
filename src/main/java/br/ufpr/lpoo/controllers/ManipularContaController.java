package br.ufpr.lpoo.controllers;

import br.ufpr.lpoo.models.Conta;
import br.ufpr.lpoo.models.ContaCorrente;
import br.ufpr.lpoo.models.connection.ContaDao;
import br.ufpr.lpoo.models.connection.DaoFactory;
import br.ufpr.lpoo.models.connection.DaoType;
import br.ufpr.lpoo.utils.Observer;
import br.ufpr.lpoo.views.ManipularConta;

import java.beans.PropertyChangeEvent;

import static br.ufpr.lpoo.models.connection.DaoType.MYSQL;

public class ManipularContaController implements Observer {

    private Conta conta;
    private final ManipularConta view;
    private static final DaoType type = MYSQL;

    public ManipularContaController(ManipularConta view) {
        this.view = view;
        this.view.initUIComponents();
        this.view.setController();
    }

    public void buscarConta(String cpf) throws Exception {
        this.conta = DaoFactory.getContaDao(type).getByCliente(cpf);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        this.view.loadConta(conta);
        conta.addObserver(this);
    }

    public void realizaSaque(double valor) throws Exception {
        if (conta.saca(valor)) {
            ContaDao contaDao = DaoFactory.getContaDao(type);
            contaDao.update(conta);
        } else {
            throw new IllegalArgumentException(conta.getClass() == ContaCorrente.class ? "Saldo/limite insuficiente" : "O valor restante é inferior ao montante mínimo");
        }
    }

    public void realizaDeposito(double valor) throws Exception {
        if (conta.deposita(valor)) {
            ContaDao contaDao = DaoFactory.getContaDao(type);
            contaDao.update(conta);
        } else {
            throw new IllegalArgumentException("Depósito mínimo não atingido");
        }
    }

    public void realizaInvestimento(){
        if (conta.getSaldo() > 0) {
            conta.remunera();
            try {
                ContaDao contaDao = DaoFactory.getContaDao(type);
                contaDao.update(conta);
            } catch (Exception ex) {
                throw new RuntimeException( "Erro ao realizar investimento");
            }
        } else {
            throw new IllegalArgumentException( "Saldo insuficiente para investir");
        }
    }


    /**
     * Método que atualiza o saldo da conta
     *
     * @param evt Evento de mudança de propriedade
     * @see Observer
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("saldo")) {
            this.view.updateSaldo(this.conta.getSaldo());
        }
    }
}
