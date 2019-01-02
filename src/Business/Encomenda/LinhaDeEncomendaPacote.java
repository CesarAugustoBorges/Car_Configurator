package Business.Encomenda;

import Business.Stock.Peca;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinhaDeEncomendaPacote extends LinhaDeEncomenda {
    private PacoteDeConfiguracao pacoteDeConfiguracao;


    public LinhaDeEncomendaPacote(){
        super();
    }

    public LinhaDeEncomendaPacote(LinhaDeEncomendaPacote le){
        new LinhaDeEncomendaPacote(le.getId(), le.getQuantidade(), le.getPacoteDeConfiguracao().clone());
    }

    public LinhaDeEncomendaPacote(int id, int quantidade, PacoteDeConfiguracao p) {
        super(id, quantidade);
        this.pacoteDeConfiguracao = p.clone();
        this.setPreco(p.getPreco());
    }

    public PacoteDeConfiguracao getPacoteDeConfiguracao() {
        return pacoteDeConfiguracao.clone();
    }

    public void setPacoteDeConfiguracao(PacoteDeConfiguracao pacoteDeConfiguracao) {
        this.pacoteDeConfiguracao = pacoteDeConfiguracao;
    }

    public LinhaDeEncomendaPacote clone() {
        return new LinhaDeEncomendaPacote(this);
    }

    public boolean hasPeca(int id) {
        return pacoteDeConfiguracao.hasPeca(id);
    }

    public boolean hasSameProduct(LinhaDeEncomenda le) {
        if( le instanceof LinhaDeEncomendaPacote) {
            LinhaDeEncomendaPacote lep = (LinhaDeEncomendaPacote) le;
            if(lep.getPacoteDeConfiguracao().getId() == this.getPacoteDeConfiguracao().getId())
                return true;
        }
        return false;
    }

    public String getDescricao() {
        return pacoteDeConfiguracao.getDescricao();
    }

    public boolean dependeDe(Peca peca){
        return pacoteDeConfiguracao.getDependencias().contains(Integer.valueOf(peca.getId()));
    }

    public boolean dependeDe(PacoteDeConfiguracao pacote){
        for(Integer p : pacote.getPecasIds())
            if(this.pacoteDeConfiguracao.getDependencias().contains(Integer.valueOf(p)))
                return true;
        return false;
    }

    public Set<String> getCategorias(){
        Set<String> res = new HashSet<>();
        for(Peca p : pacoteDeConfiguracao.getPecas().keySet())
            res.add(p.getCategoria());
        return res;
    }

    public boolean hasCategoria(String categoria) {
        return getCategorias().contains(categoria);
    }
}
