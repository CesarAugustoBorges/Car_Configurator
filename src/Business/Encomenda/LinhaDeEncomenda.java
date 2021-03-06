package Business.Encomenda;

import Business.Stock.Peca;

import java.util.List;
import java.util.Set;

public abstract class LinhaDeEncomenda {
    private int id;
    private float preco;
    private int quantidade;


    public LinhaDeEncomenda(){
        this.id = 0;
        this.preco = 0;
        this.quantidade = 0;
    }

    public LinhaDeEncomenda(LinhaDeEncomenda le ) {
        this.id = le.getId();
        this.preco = le.getPreco();
        this.quantidade = le.getQuantidade();
    }

    public LinhaDeEncomenda(int id, int quantidade){
        this.id = id;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPrecoTotal() {
        return preco*quantidade;
    }



    public abstract LinhaDeEncomenda clone();
    public abstract boolean hasSameProduct(LinhaDeEncomenda le);
    public abstract boolean hasPeca(int p);
    public abstract String getDescricao();
    public abstract boolean dependeDe(Peca p);
    public abstract boolean dependeDe(PacoteDeConfiguracao p);
    public abstract Set<String> getCategorias();
    public abstract boolean hasCategoria(String categoria);
}
