package Business.Utilizador;

public class Cliente {

    private String  Name;
    private int Id;
    private String Nif;

    public Cliente(){
        this.Name = "";
        this.Nif = "";
    }

    public Cliente(String nome,String nif){
        this.Name = nome;
        this.Nif = nif;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNif() {
        return Nif;
    }

    public void setNif(String nif) {
        Nif = nif;
    }
}
