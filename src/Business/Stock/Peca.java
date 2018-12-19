package Business.Stock;

import java.util.ArrayList;
import java.util.Objects;

public class Peca {
    private int id;
    private float preco;
    private String descricao;
    private String categoria;
    private ArrayList<Integer> dependencias;
    private ArrayList<Integer> incompatibilidades;

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public ArrayList<Integer> getDependencias() {
        ArrayList<Integer> res = new ArrayList<>();
        for(Integer i : this.dependencias)
            res.add(i);
        return res;
    }

    public void setDependencias(ArrayList<Integer> dependencias) {
        this.dependencias = dependencias;
    }

    public ArrayList<Integer> getIncompatibilidades() {
        ArrayList<Integer> res = new ArrayList<>();
        for(Integer i : this.incompatibilidades)
            res.add(i);
        return res;
    }

    public void setIncompatibilidades(ArrayList<Integer> incompatibilidades) {
        this.incompatibilidades = incompatibilidades;
    }
}
