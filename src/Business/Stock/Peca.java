package Business.Stock;

import java.util.ArrayList;

public class Peca {
    private int id;
    private String categoria;
    private ArrayList<Integer> dependencias;
    private ArrayList<Integer> incompatibilidades;

    public int getId() {
        return id;
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

    public ArrayList<Integer> getDependencias() {
        return dependencias;
    }

    public void setDependencias(ArrayList<Integer> dependencias) {
        this.dependencias = dependencias;
    }

    public ArrayList<Integer> getIncompatibilidades() {
        return incompatibilidades;
    }

    public void setIncompatibilidades(ArrayList<Integer> incompatibilidades) {
        this.incompatibilidades = incompatibilidades;
    }
}
