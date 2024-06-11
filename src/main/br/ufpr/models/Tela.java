/**
 * Interface Tela.
 * Esta interface define um contrato para classes que representam uma tela em um sistema de interface gráfica.
 *
 * A interface possui o seguinte método:
 * - getFrame(): retorna o painel (JPanel) da tela.
 */
public interface Tela {
    /**
     * Método para obter o painel (JPanel) da tela.
     *
     * @return o painel (JPanel) da tela
     */
    public JPanel getFrame();
}