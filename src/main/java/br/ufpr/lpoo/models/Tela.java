package br.ufpr.lpoo.models;

import javax.swing.JPanel;

/**
 * Esta é uma interface que define os métodos que uma tela deve implementar.
 * Os métodos incluem operações básicas como obter o frame da tela.
 */
public interface Tela {
    /**
     * Este método retorna o frame da tela.
     *
     * @return O frame da tela.
     */
    JPanel getFrame();
    void initUIComponents();
}