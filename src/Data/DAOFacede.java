package Data;

import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import javafx.util.Pair;

import java.util.List;

public class DAOFacede {

    private final static ClienteDAO  ClDAO = new ClienteDAO();
    private final static PeçaDAO PDAO = new PeçaDAO();
    private final static StockIntegerDAO StockDAO = new StockIntegerDAO();

    //devolve um Utilizador, Gestor ou Admin
    public Funcionario getUtilizador(int id) {//done
        return ClDAO.getUtilizador(id);
    }

    //devolve um Cliente
    public Cliente getCliente(int id){//done
        return ClDAO.getCliente(id);
    }
    //verifica se existe um Utilizador
    public boolean constainsUtilizador(int id){//done
        return ClDAO.constainsUtilizador(id);
    }
    //adiciona um novo utilizador
    public void putUtilizador(Funcionario user){
        ClDAO.putUtilizador(user);
    }

    // Devolve uma lista de pares, sendo o inteiro o id e a String o nome da peça.
    public List<Pair<Integer,String>> getStock(){
        return PDAO.getStock();
    }

    // Devolve a quantidade disponivel e máxima como um par
    public Pair<Integer,Integer> getInfoOfPeca(int id){
        return StockDAO.getInfoOfPeca(id);
    }

    //verifica se existe uma peça em stock
    public boolean containsPeca(int id){
        return StockDAO.containsPeca(id);
    }
}
