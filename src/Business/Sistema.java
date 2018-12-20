package Business;

import Business.Stock.StockInteger;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import Data.*;
import javafx.util.Pair;

import java.util.List;
/*
public class Sistema {
    private DAOFacade facade = new DAODacade();

    public Funcionario getFuncionario(int id) {
        if(facade.containsUtilizador(id))
            return facade.getUtilizador(id);
        return null;
    }

    public void putFuncionario(int id, String password) {
        Funcionario novo = new Funcionario(id, password);
        facade.putUtilizador(novo);
    }

    public List<Pair<Integer,String>> getStock() {
        return facade.getStock();
    }

    public Pair<Integer, Integer> getInfoOfPeca(int id) {
        return facade.getInfoOfPeca(id);
    }

    public boolean containsPeca(int id) {
        return facade.containsPeca(id);
    }

    // Metodo que verifica as credenciais do utilizador. Deve retornar 0 se for Admin, 1 se for gestor, 2 se for cliente, -1 para credenciais erradas. Luís Macedo
    public int login(String user, String password){
        return 0;
    }
}
*/