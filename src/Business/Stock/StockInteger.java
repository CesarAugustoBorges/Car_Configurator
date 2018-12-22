package Business.Stock;

public class StockInteger {
    private int quantidadeDisponivel;
    private int quantidadeMaxima;

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    public boolean addPecas(int quantidade) {
        if(quantidadeDisponivel + quantidade < quantidadeMaxima) {
            quantidadeDisponivel += quantidade;
            return true;
        }
        return false;
    }
}
