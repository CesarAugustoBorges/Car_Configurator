package Business.Encomenda;

public class LinhaDeEncomendaPeca extends LinhaDeEncomenda {
    public LinhaDeEncomendaPeca(LinhaDeEncomendaPeca le) {
        super(le);
    }

    public LinhaDeEncomendaPeca clone() {
        return new LinhaDeEncomendaPeca(this);
    }
}
