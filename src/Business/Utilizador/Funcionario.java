package Business.Utilizador;

public class Funcionario {
    private int id;
    private String nif;
    private String nome;
    private String tipo;
    private String passe;

    public Funcionario(){
        this.id = 0;
        this.nif = "";
        this.nome = "";
        this.tipo = "";
        this.passe = "";
    }

    public Funcionario(String nif, String nome, String passe) {
        this.id = 0;
        this.nif = nif;
        this.nome = nome;
        this.passe = passe;
        this.tipo = "Funcionario";
    }

    public Funcionario(int id, String pass) {
        this.id = id;
        this.passe = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPasse(String Passe) {
        this.passe = Passe;
    }

    public boolean isPassword(String passe) {
        return this.passe.equals(passe);
    }

    public String getPasse() {
        return passe;
    }
}
