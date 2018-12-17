package Business.Encomenda;

import Business.Stock.Peca;

import java.util.ArrayList;

public class PacoteDeConfiguracao {
    private int id;
    private ArrayList<Peca> pecas;

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
}
