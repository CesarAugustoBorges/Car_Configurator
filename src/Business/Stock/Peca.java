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

    public Peca(){
    }

    public Peca(Peca p){
        this.id = p.getId();
        this.preco = p.getPreco();
        this.descricao = p.getDescricao();
        this.setDependencias(p.getDependencias());
        this.setIncompatibilidades(p.getIncompatibilidades());
    }

    public Peca(int id, float preco, String descricao, String categoria, ArrayList<Integer> dependencias, ArrayList<Integer> incompatibilidades){
        this.id = id;
        this.preco = preco;
        this.descricao = descricao;
        this.setIncompatibilidades(incompatibilidades);
        this.setDependencias(dependencias);
    }

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
        this.dependencias = new ArrayList<>();
        for(Integer i : dependencias)
            this.dependencias.add(i);
    }

    public ArrayList<Integer> getIncompatibilidades() {
        ArrayList<Integer> res = new ArrayList<>();
        for(Integer i : this.incompatibilidades)
            res.add(i);
        return res;
    }

    public void setIncompatibilidades(ArrayList<Integer> incompatibilidades) {
        this.incompatibilidades = new ArrayList<>();
        for(Integer i : incompatibilidades)
            this.incompatibilidades.add(i);
    }

    public boolean incompativelCom(Peca p){
        return incompativelCom(p.getId());
    }

    public boolean incompativelCom(Integer p){
        return incompatibilidades.contains(Integer.valueOf(p));
    }


    public Peca clone(){
        return new Peca(this);
    }
}
