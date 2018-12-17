package Business.Encomenda;

public abstract class LinhaDeEncomenda {
    protected int id;
    protected int preco;
    protected String descricao;
    protected int quantidade;

    public LinhaDeEncomenda(LinhaDeEncomenda le ) {
        this.id = le.getId();
        this.preco = le.getPreco();
        this.descricao = le.getDescricao();
        this.quantidade = le.getQuantidade();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public abstract LinhaDeEncomenda clone();
}
