package Business;

import Business.Encomenda.Encomenda;
import Business.Stock.Peca;
import Business.Stock.StockInteger;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import Data.*;
import javafx.util.Pair;

import java.util.List;

public class Sistema {
    private DAOFacede facade = new DAOFacede();
    private Encomenda enc = new Encomenda();

    public Funcionario getFuncionario(int id) {
        if(facade.constainsUtilizador(id))
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Métodos a usar na view //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////
    ////////////////// Login //////////////////
    ///////////////////////////////////////////

    public boolean login(String user, String pass) {
        return facade.validaUtilizador(user, pass);
    }

    ///////////////////////////////////////////
    ////////////// Adiciona Peca //////////////
    ///////////////////////////////////////////

    public String checkStatusWhenAddingPeca(int id, int quantidade) {
        Peca peca = facade.getPeca(id);
        return this.enc.checkStatusWhenAdding(peca);
    }

    public void removeIncomapativeisPeca(int id) {
        List<Integer> listaIncompatibilidades = facade.getIncompatibilidadesPeca(id);
        this.enc.removePecas(listaIncompatibilidades);
    }

    public void addPecasObrigatorias(int id){
        List<Integer> listaDependencias = facade.getDependenciasPeca(id);
        for(Integer p: listaDependencias) {
            Peca peca = facade.getPeca(id);
            this.enc.addPeca(peca, 1);
        }
    }

    public void addPeca(int id, int quantidade){
        Peca peca = facade.getPeca(id);
        this.enc.addPeca(peca, quantidade);
    }

    ///////////////////////////////////////////
    ///////////// Remover Funcionario//////////
    ///////////////////////////////////////////

    public void removerFuncionario(int id){
        if(facade.constainsUtilizador(id))
            facade.removerFuncionario(id);
    }

    ///////////////////////////////////////////
    /////////// Adicionar Funcionario//////////
    ///////////////////////////////////////////

    public void adicionarFuncionario(int id, String password) throws Exception{
        if(facade.constainsUtilizador(id))
            throw new Exception("Utilizador já existe.");
        Funcionario f = new Funcionario(id, password);
        facade.
    }

}
