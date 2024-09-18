package br.ufpr.lpoo.controllers;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que contém métodos para exibir mensagens de erro, aviso, sucesso e confirmação
 * Utiliza a classe JOptionPane para exibir as mensagens
 * @see JOptionPane
 */
public class Mensagens {

    /**
     * Exibe uma mensagem de erro
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void erro(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Erro", JOptionPane.ERROR_MESSAGE, Imagens.ERRO.icon());
    }

    /**
     * Exibe uma mensagem de aviso
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void aviso(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE, Imagens.WARNING.icon());
    }

    /**
     * Exibe uma mensagem de sucesso
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void sucesso(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE, Imagens.SUCCESS.icon());
    }

    /**
     * Exibe uma mensagem de carregamento
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void carregando(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Carregando", JOptionPane.INFORMATION_MESSAGE, Imagens.LOADING.icon());
    }

    /**
     * Exibe uma mensagem de confirmação
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     * @return true se o usuário confirmar a ação, false caso contrário
     */
    public static boolean confirmar(Component origin, String mensagem){
        return JOptionPane.showConfirmDialog(origin, mensagem, "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
