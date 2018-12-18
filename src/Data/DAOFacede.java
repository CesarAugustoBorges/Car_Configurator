package Data;

import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;

public abstract class DAOFacede {
    //devolve um Utilizador, Gestor ou Admin
    public abstract Funcionario getUtilizador(int id);//done
    //devolve um Cliente
    public abstract Cliente getCliente(int id);//done
    //verifica se existe um Utilizador
    public abstract boolean constainsUtilizador(int id);
    //adiciona um novo utilizador
    public abstract void putUtilizador(Funcionario user);
    //public List<Pair<Integer,String>> getStock();  // Devolve uma lista de pares, sendo o inteiro o id e a String o nome da peça.

}
