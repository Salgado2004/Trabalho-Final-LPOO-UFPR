package test.br.ufpr.views;

import main.br.ufpr.controllers.Sistema;
import main.br.ufpr.models.*;
import main.br.ufpr.views.VincularCliente;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;


public class TesteVincularCliente {

    @Before
    public void setUp() {
        Endereco endereco = new Endereco("Rua", "Bairro", "1", "Cidade");
        Cliente cliente1 = new Cliente("Maria", "Silva", endereco, "302.595.890-12", "12345678901");
        Sistema.cadastrarCliente(cliente1);
    }

    @After
    public void tearDown() {
        Sistema.getClientes().clear();
    }

    @Test
    public void testInstanciarTelaVazia() {
        Tela vincularCliente = new VincularCliente();
        JPanel frame = vincularCliente.getFrame();
        Component formulario = frame.getComponent(3);

        assertNotNull(frame);
        assertNotNull(formulario);
        assertFalse(formulario.isVisible());
    }

    @Test
    public void vincularContaCorrente() {
        VincularCliente vincularCliente = new VincularCliente();
        JPanel frame = vincularCliente.getFrame();
        JComboBox tipoConta = (JComboBox) frame.getComponent(1);
        JPanel formulario = (JPanel) frame.getComponent(3);
        JTextField depositoInicial = (JTextField) formulario.getComponent(8);
        JTextField limite = (JTextField) formulario.getComponent(7);
        JButton salvarButton = (JButton) formulario.getComponent(1);

        JScrollPane scrollPane = (JScrollPane) frame.getComponent(2);
        JTable tabela = (JTable) scrollPane.getViewport().getView();
        tabela.selectAll();

        tipoConta.setSelectedItem("Conta Corrente");
        depositoInicial.setText("1000");
        limite.setText("500");
        salvarButton.doClick();

        Cliente cliente = Sistema.getClientes().get(0);
        assertTrue(cliente.getConta() instanceof ContaCorrente);
        assertEquals(1000, cliente.getConta().getSaldo(), 0.01);
        assertEquals(500, ((ContaCorrente) cliente.getConta()).getLimite(), 0.01);
    }

    @Test
    public void vincularContaInvestimento() {
        VincularCliente vincularCliente = new VincularCliente();
        JPanel frame = vincularCliente.getFrame();
        JComboBox tipoConta = (JComboBox) frame.getComponent(1);
        JPanel formulario = (JPanel) frame.getComponent(3);
        JTextField depositoInicial = (JTextField) formulario.getComponent(8);
        JTextField montanteMinimo = (JTextField) formulario.getComponent(7);
        JTextField depositoMinimo = (JTextField) formulario.getComponent(6);
        JButton salvarButton = (JButton) formulario.getComponent(1);

        JScrollPane scrollPane = (JScrollPane) frame.getComponent(2);
        JTable tabela = (JTable) scrollPane.getViewport().getView();
        tabela.selectAll();

        tipoConta.setSelectedItem("Conta Investimento");
        depositoInicial.setText("1000");
        montanteMinimo.setText("500");
        depositoMinimo.setText("100");
        salvarButton.doClick();

        Cliente cliente = Sistema.getClientes().get(0);
        assertTrue(cliente.getConta() instanceof ContaInvestimento);
        assertEquals(1000, cliente.getConta().getSaldo(), 0.01);
        assertEquals(500, ((ContaInvestimento) cliente.getConta()).getMontanteMinimo(), 0.01);
    }

    @Test
    public void excluirConta() {
        VincularCliente vincularCliente = new VincularCliente();
        JPanel frame = vincularCliente.getFrame();
        JComboBox tipoConta = (JComboBox) frame.getComponent(1);
        JPanel formulario = (JPanel) frame.getComponent(3);
        JTextField depositoInicial = (JTextField) formulario.getComponent(8);
        JTextField montanteMinimo = (JTextField) formulario.getComponent(7);
        JTextField depositoMinimo = (JTextField) formulario.getComponent(6);
        JButton salvarButton = (JButton) formulario.getComponent(1);
        JButton excluirButton = (JButton) formulario.getComponent(0);

        JScrollPane scrollPane = (JScrollPane) frame.getComponent(2);
        JTable tabela = (JTable) scrollPane.getViewport().getView();
        tabela.selectAll();

        tipoConta.setSelectedItem("Conta Investimento");
        depositoInicial.setText("1000");
        montanteMinimo.setText("500");
        depositoMinimo.setText("100");
        salvarButton.doClick();

        tabela.selectAll();

        excluirButton.doClick();

        Cliente cliente = Sistema.getClientes().get(0);
        assertNull(cliente.getConta());
    }

    @Test
    public void testRedirecionarParaManipularConta() {
            VincularCliente vincularCliente = new VincularCliente();
            JPanel frame = vincularCliente.getFrame();
            JComboBox tipoConta = (JComboBox) frame.getComponent(1);
            JPanel formulario = (JPanel) frame.getComponent(3);
            JTextField depositoInicial = (JTextField) formulario.getComponent(8);
            JTextField montanteMinimo = (JTextField) formulario.getComponent(7);
            JTextField depositoMinimo = (JTextField) formulario.getComponent(6);
            JButton salvarButton = (JButton) formulario.getComponent(1);
            JButton gerenciarButton = (JButton) formulario.getComponent(2);

            JScrollPane scrollPane = (JScrollPane) frame.getComponent(2);
            JTable tabela = (JTable) scrollPane.getViewport().getView();
            tabela.selectAll();

            tipoConta.setSelectedItem("Conta Investimento");
            depositoInicial.setText("1000");
            montanteMinimo.setText("500");
            depositoMinimo.setText("100");
            salvarButton.doClick();

            tabela.selectAll();
            gerenciarButton.doClick();


        }
}
