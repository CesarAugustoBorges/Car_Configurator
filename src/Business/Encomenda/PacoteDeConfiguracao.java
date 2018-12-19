package Business.Encomenda;

import Business.Stock.Peca;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PacoteDeConfiguracao {
    private int id;
    private float preco;
    private String descricao;
    private ArrayList<Peca> pecas;

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(ArrayList<Peca> pecas) {
        this.pecas = pecas;
    }

    public Set<Integer> getIncompatibilidades(){
        HashSet<Integer> res = new HashSet<>();
        for(Peca p: pecas) {
            ArrayList<Integer> incom = p.getIncompatibilidades();
            for(Integer inc : incom) res.add(inc);
        }
        return res;
    }

    public boolean hasPeca(int id){
        for(Peca p: pecas)
            if(p.getId() == id)
                return  true;
        return false;
    }
}