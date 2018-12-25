package Business.Encomenda;

import Business.Stock.Peca;

import java.util.*;
import java.util.stream.Collectors;

public class PacoteDeConfiguracao {
    private int id;
    private float preco;
    private String descricao;
    private Map<Peca, Integer> pecas;

    public PacoteDeConfiguracao(){

    }

    public PacoteDeConfiguracao(PacoteDeConfiguracao p){
        this.id = p.getId();
        this.preco = p.getPreco();
        this.descricao = p.getDescricao();
        this.setPecas(p.getPecas());
    }

    public PacoteDeConfiguracao(int id, float preco, String descricao, Map<Peca, Integer> pecas){
        this.id = id;
        this.preco = preco;
        this.descricao = descricao;
        this.setPecas(pecas);
    }

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

    public void setPecas(Map<Peca, Integer> pecas) {
        this.pecas = new HashMap<Peca, Integer>();
        for(Peca p : pecas.keySet())
            this.pecas.put(p, pecas.get(p));
    }

    public List<Integer> getPecasIds(){
        return this.pecas.keySet().stream().map(Peca::getId).collect(Collectors.toList());
    }

    public Map<Peca, Integer> getPecas(){
        Map<Peca, Integer> res = new HashMap<>();
        for(Peca p : pecas.keySet()) res.put(p.clone(), pecas.get(p));
        return res;
    }

    public Set<Integer> getIncompatibilidades(){
        HashSet<Integer> res = new HashSet<>();
        for(Peca p: pecas.keySet()) {
            ArrayList<Integer> incom = p.getIncompatibilidades();
            for(Integer inc : incom) res.add(inc);
        }
        return res;
    }

    public Set<Integer> getDependencias(){
        HashSet<Integer> res = new HashSet<>();
        for(Peca p: pecas.keySet()) {
            ArrayList<Integer> incom = p.getDependencias();
            for(Integer inc : incom) res.add(inc);
        }
        return res;
    }

    public boolean hasPeca(int id){
        for(Peca p: pecas.keySet())
            if(p.getId() == id)
                return  true;
        return false;
    }

    public boolean incompativelCom(Integer id){
        for(Peca peca : pecas.keySet())
            if(peca.incompativelCom(id))
                return true;
        return false;
    }

    public boolean incompativelCom(Peca p){
        for(Peca peca : pecas.keySet())
            if(peca.incompativelCom(p))
                return true;
        return false;
    }

    public PacoteDeConfiguracao clone(){
        return new PacoteDeConfiguracao(this);
    }
}
