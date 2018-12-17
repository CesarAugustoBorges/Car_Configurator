package Business.Encomenda;

public class LinhaDeEncomendaPacote extends LinhaDeEncomenda {
    private PacoteDeConfiguracao pacoteDeConfiguracao;

    public PacoteDeConfiguracao getPacoteDeConfiguracao() {
        return pacoteDeConfiguracao;
    }

    public void setPacoteDeConfiguracao(PacoteDeConfiguracao pacoteDeConfiguracao) {
        this.pacoteDeConfiguracao = pacoteDeConfiguracao;
    }

    public LinhaDeEncomendaPacote(LinhaDeEncomendaPacote le){
        super(le);
    }

    public LinhaDeEncomendaPacote clone() {
        return new LinhaDeEncomendaPacote(this);
    }
}
