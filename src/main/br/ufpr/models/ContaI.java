package main.br.ufpr.models;

public interface ContaI {
    public boolean deposita(double valor) ;
    public boolean saca(double valor) ;
    public Cliente getDono() ;
    public int getNumero() ;
    public double getSaldo() ;
    public void remunera() ;
}
