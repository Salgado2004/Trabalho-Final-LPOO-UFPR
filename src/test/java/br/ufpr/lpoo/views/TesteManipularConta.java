package br.ufpr.lpoo.views;

import br.ufpr.lpoo.controllers.Sistema;
import br.ufpr.lpoo.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class TesteManipularConta {

    @Before
    public void setUp() {
        Endereco endereco = new Endereco("Rua", "Bairro", "1", "Cidade");
        Cliente cliente = new Cliente("João", "Silva", endereco, "095.218.850-33", "12345678900");
        Cliente cliente1 = new Cliente("Maria", "Silva", endereco, "302.595.890-12", "12345678901");
        Conta conta = new ContaCorrente(12345, cliente, 1000.0, 0.0);
        Sistema.cadastrarCliente(cliente);
        Sistema.cadastrarCliente(cliente1);
        cliente.setConta(conta);
    }

    @After
    public void tearDown() {
        Sistema.getClientes().clear();
    }

    @Test
    public void testInstanciarTelaVazia() {
        Tela manipularConta = new ManipularConta();
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);

        assertNotNull(frame);
        assertNotNull(dadosConta);
        assertEquals("javax.swing.JPanel", dadosConta.getClass().getName());
        assertFalse(dadosConta.isVisible());
    }

    @Test
    public void testinstanciarTelaComConta(){
        Tela manipularConta = new ManipularConta(Sistema.getClientes().get(0));
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTextField cpfCliente = (JTextField) frame.getComponent(1);

        assertNotNull(frame);
        assertNotNull(dadosConta);
        assertTrue(dadosConta.isVisible());
        assertEquals("09521885033", cpfCliente.getText());
    }

    @Test
    public void testPesquisarContaPorCpf1(){
        Tela manipularConta = new ManipularConta();
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTextField cpfCliente = (JTextField) frame.getComponent(1);
        JButton buscarButton = (JButton) frame.getComponent(5);
        cpfCliente.setText("123.456.789-00");
        buscarButton.doClick();

        assertNotNull(frame);
        assertNotNull(dadosConta);
        assertFalse(dadosConta.isVisible());
    }

    @Test
    public void testPesquisarContaPorCpf2(){
        Tela manipularConta = new ManipularConta();
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTextField cpfCliente = (JTextField) frame.getComponent(1);
        JButton buscarButton = (JButton) frame.getComponent(5);
        cpfCliente.setText("095.218.850-33");
        buscarButton.doClick();

        assertNotNull(frame);
        assertNotNull(dadosConta);
        assertTrue(dadosConta.isVisible());
    }

    @Test
    public void testPesquisarContaPorCpf3(){
        Tela manipularConta = new ManipularConta();
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTextField cpfCliente = (JTextField) frame.getComponent(1);
        JButton buscarButton = (JButton) frame.getComponent(5);
        cpfCliente.setText("302.595.890-12");
        buscarButton.doClick();

        assertNotNull(frame);
        assertNotNull(dadosConta);
        assertFalse(dadosConta.isVisible());
    }

    @Test
    public void testSaque1(){
        Tela manipularConta = new ManipularConta(Sistema.getClientes().get(0));
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) dadosConta).getComponent(3);
        JPanel sacar = (JPanel) tabbedPane.getComponent(0);
        JTextField valorSaque = (JTextField) sacar.getComponent(2);
        JButton saqueButton = (JButton) sacar.getComponent(4);
        JLabel saldo = (JLabel) ((JPanel) ((JPanel) dadosConta).getComponent(1)).getComponent(0);
        valorSaque.setText("500");
        saqueButton.doClick();

        assertEquals("R$ 500,00", saldo.getText());
    }

    @Test
    public void testSaque2(){
        Tela manipularConta = new ManipularConta(Sistema.getClientes().get(0));
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) dadosConta).getComponent(3);
        JPanel sacar = (JPanel) tabbedPane.getComponent(0);
        JTextField valorSaque = (JTextField) sacar.getComponent(2);
        JButton saqueButton = (JButton) sacar.getComponent(4);
        JLabel saldo = (JLabel) ((JPanel) ((JPanel) dadosConta).getComponent(1)).getComponent(0);
        valorSaque.setText("1500");
        saqueButton.doClick();

        assertEquals("R$ 1000,00", saldo.getText());
    }

    @Test
    public void remunera(){
        Tela manipularConta = new ManipularConta(Sistema.getClientes().get(0));
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) dadosConta).getComponent(3);
        JPanel investir = (JPanel) tabbedPane.getComponent(2);
        JButton investirButton = (JButton) investir.getComponent(3);
        JLabel saldo = (JLabel) ((JPanel) ((JPanel) dadosConta).getComponent(1)).getComponent(0);
        investirButton.doClick();

        assertEquals("R$ 1010,00", saldo.getText());
    }

    @Test
    public void testDeposito1(){
        Tela manipularConta = new ManipularConta(Sistema.getClientes().get(0));
        JPanel frame = manipularConta.getFrame();
        Component dadosConta = frame.getComponent(4);
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) dadosConta).getComponent(3);
        JPanel depositar = (JPanel) tabbedPane.getComponent(1);
        JTextField valorDeposito = (JTextField) depositar.getComponent(2);
        JButton depositoButton = (JButton) depositar.getComponent(4);
        JLabel saldo = (JLabel) ((JPanel) ((JPanel) dadosConta).getComponent(1)).getComponent(0);
        valorDeposito.setText("500");
        depositoButton.doClick();

        assertEquals("R$ 1500,00", saldo.getText());
    }


}
