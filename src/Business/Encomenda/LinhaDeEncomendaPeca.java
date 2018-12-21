package Business.Encomenda;

import Business.Stock.Peca;

import java.util.Objects;

public class LinhaDeEncomendaPeca extends LinhaDeEncomenda {
    private Peca peca;

    public LinhaDeEncomendaPeca(LinhaDeEncomendaPeca le) {
        super(le);
    }

    public LinhaDeEncomendaPeca(int id, int quantidade, Peca p) {
        super(id, quantidade);
        this.peca = p;
        this.setPreco(p.getPreco());
    }

    @Override
    public LinhaDeEncomendaPeca clone() {
        return new LinhaDeEncomendaPeca(this);
    }

    public boolean hasSameProduct(LinhaDeEncomenda le) {
        if(le instanceof LinhaDeEncomendaPeca) {
            LinhaDeEncomendaPeca lep = (LinhaDeEncomendaPeca) le;
            if(lep.getIdPeca() == this.peca.getId())
                return true;
        }
        return false;

    }


    public boolean hasPeca(int p) {
        return peca.getId() == p;
    }

    public int getIdPeca() {
        return peca.getId();
    }

    public String getDescricao(){
        return peca.getDescricao();
    }
}
