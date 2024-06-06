package main.br.ufpr.controllers;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Enum que contém os caminhos das imagens utilizadas no projeto
 */
public enum Imagens {
    MAIN("icon.png"),
    LOGO("logo.jpg"),
    EYE_OPEN("eye_open.png"),
    EYE_CLOSED("eye_closed.png"),
    SEARCH("search.png"),
    DELETE("delete.png"),
    DEPOSITO("deposito.png"),
    SAQUE("saque.png"),
    REMUNERA("remunera.png"),
    INVESTIMENTO("investir.png"),
    CONTAS("contas.png"),
    CLIENTES("clientes.png"),
    TRANSACTIONS("transactions.png"),
    ERRO("error.png"),
    WARNING("warning.png"),
    SUCCESS("sucess.png"),
    LOADING("load.png");

    private String path;

    /**
     * Construtor para o enum Imagens.
     *
     * @param path O caminho para a imagem. Este caminho é relativo ao diretório /assets.
     */
    Imagens(String path) {
        this.path = path;
    }

    /**
     * Retorna um ImageIcon a partir do caminho da imagem especificado.
     *
     * Este método tenta carregar a imagem do diretório /assets usando o caminho especificado.
     * Se a imagem for carregada com sucesso, um novo ImageIcon é criado e retornado.
     * Se ocorrer um erro durante o carregamento da imagem, uma mensagem de erro é impressa no console e o método retorna null.
     *
     * @return ImageIcon se a imagem for carregada com sucesso, null caso contrário.
     */
    public ImageIcon icon() {
        try{
            InputStream is = getClass().getResourceAsStream("/assets/"+path);
            Image image = ImageIO.read(is);
            return new ImageIcon(image);
        } catch (IOException e) {
            System.out.println("Erro ao carregar icone "+ path);
            return null;
        }
    }

    /**
     * Retorna um ImageIcon a partir do caminho da imagem especificado.
     *
     * Este método tenta carregar a imagem do diretório /assets usando o caminho especificado.
     * Se a imagem for carregada com sucesso, um novo ImageIcon é criado com as dimensões especificadas e retornado.
     * Se ocorrer um erro durante o carregamento da imagem, uma mensagem de erro é impressa no console e o método retorna null.
     *
     * @param width A largura da imagem.
     * @param height A altura da imagem.
     * @return ImageIcon se a imagem for carregada com sucesso, null caso contrário.
     */
    public ImageIcon icon(int width, int height) {
        try{
            InputStream is = getClass().getResourceAsStream("/assets/"+path);
            Image image = ImageIO.read(is);
            return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            System.out.println("Erro ao carregar icone "+ path);
            return null;
        }
    }

    /**
     * Retorna uma Image a partir do caminho da imagem especificado.
     *
     * Este método tenta carregar a imagem do diretório /assets usando o caminho especificado.
     * Se a imagem for carregada com sucesso, a imagem é retornada.
     * Se ocorrer um erro durante o carregamento da imagem, uma mensagem de erro é impressa no console e o método retorna null.
     *
     * @return Image se a imagem for carregada com sucesso, null caso contrário.
     */
    public Image image() {
        try{
            InputStream is = getClass().getResourceAsStream("/assets/"+path);
            return ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagem "+ path);
            return null;
        }
    }
}
