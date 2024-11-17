package br.ufpr.lpoo.controllers;

import br.ufpr.lpoo.utils.Imagens;
import br.ufpr.lpoo.views.dialog.ConfirmDialog;
import br.ufpr.lpoo.views.dialog.MessageDialog;

import java.awt.*;

/**
 * Classe que contém métodos para exibir mensagens de erro, aviso, sucesso e confirmação
 */
public class MensagensController {

    private static boolean response;

    /**
     * Exibe uma mensagem de erro
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void erro(Component origin, String mensagem){
        MessageDialog dialog = new MessageDialog();
        dialog.initDialog(origin, mensagem, "Erro", Imagens.ERRO.icon());
        dialog.open();
    }

    /**
     * Exibe uma mensagem de aviso
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void aviso(Component origin, String mensagem){
        MessageDialog dialog = new MessageDialog();
        dialog.initDialog(origin, mensagem, "Aviso", Imagens.WARNING.icon());
        dialog.open();
    }

    /**
     * Exibe uma mensagem de sucesso
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void sucesso(Component origin, String mensagem){
        MessageDialog dialog = new MessageDialog();
        dialog.initDialog(origin, mensagem, "Sucesso", Imagens.SUCCESS.icon());
        dialog.open();
    }

    /**
     * Exibe uma mensagem de carregamento
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     */
    public static void carregando(Component origin, String mensagem){
        MessageDialog dialog = new MessageDialog();
        dialog.initDialog(origin, mensagem, "Carregando...", Imagens.LOADING.icon());
        dialog.open();
    }

    /**
     * Exibe uma mensagem de confirmação
     * @param origin O componente que originou a mensagem
     * @param mensagem A mensagem a ser exibida
     * @return true se o usuário confirmar a ação, false caso contrário
     */
    public static boolean confirmar(Component origin, String mensagem){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.initDialog(origin, mensagem);
        dialog.open();
        return response;
    }

    public static void toogleResponse(boolean arg){
        response = arg;
    }
}
