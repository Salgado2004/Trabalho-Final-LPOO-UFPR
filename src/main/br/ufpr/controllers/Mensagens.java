package main.br.ufpr.controllers;

import javax.swing.*;
import java.awt.*;

public class Mensagens {
    private static final ImageIcon erroIcon = new ImageIcon("assets/error.png");
    private static final ImageIcon warningIcon = new ImageIcon("assets/warning.png");
    private static final ImageIcon successIcon = new ImageIcon("assets/sucess.png");
    private static final ImageIcon loadingIcon = new ImageIcon("assets/load.png");

    public static void erro(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Erro", JOptionPane.ERROR_MESSAGE, erroIcon);
    }

    public static void aviso(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE, warningIcon);
    }

    public static void sucesso(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE, successIcon);
    }

    public static void carregando(Component origin, String mensagem){
        JOptionPane.showMessageDialog(origin, mensagem, "Carregando", JOptionPane.INFORMATION_MESSAGE, loadingIcon);
    }

    public static boolean confirmar(Component origin, String mensagem){
        return JOptionPane.showConfirmDialog(origin, mensagem, "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
