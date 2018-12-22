package Business;

import Business.Encomenda.Encomenda;
import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import Business.Stock.StockInteger;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import Data.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Métodos a usar na view //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////
    ////////////////// Login //////////////////
    ///////////////////////////////////////////

    // Metodo que verifica as credenciais do utilizador.
    // Deve retornar 0 se for Admin, 1 se for gestor, 2 se for cliente, -1 para credenciais erradas.
    public int login(String user, String password){
        int userId = Integer.parseInt(user);
        if(!facade.constainsUtilizador(userId))
            return -1;
        Funcionario f = facade.getUtilizador(userId);
        if(f.isPassword(password))
            switch (f.getTipo()){
                case "Funcionario": return 2;
                case "Gestor": return 1;
                case "Admin" : return 0;
                default: break;
            }
        return -1;
    }

    ///////////////////////////////////////////
    ////////// Encomenendar Veículo ////////////
    ///////////////////////////////////////////
    //public void addEncomenda() {
    //    facade.addEncomenda(this.enc);
    //}

    ///////////////////////////////////////////
    ////////////// Adiciona Peca //////////////
    ///////////////////////////////////////////

    public List<Pair<Integer,String>> getLEIncompativeisComPeca(int id) {
        Peca p = facade.getPeca(id);
        List<Pair<Integer,String>> incom = this.enc.getLEIncompativeisCom(p);
        return incom;
    }

    public void removeLsE(List<Integer> ids){
        this.enc.removeLsE(ids);
    }

    public void addPecas(List<Integer> ids){
        List<Peca> pecas = new ArrayList<>();
        for(Integer id : ids)
            pecas.add(facade.getPeca(id));
        enc.addPecas(pecas);
    }
    /*
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
    ///////////// Remover Pacote //////////////
    ///////////////////////////////////////////

    public List<Pair<Integer, String>> getLsEDependentesPacote(int IdPacote){
        PacoteDeConfiguracao p = facade.getPacote(int idPacote);
        return this.enc.getLsEDependentes(p);
    }


    ///////////////////////////////////////////
    ///////////// Remover Funcionario//////////
    ///////////////////////////////////////////

    //Devolver um Boolean!!
    public void removerFuncionario(int id){
        if(facade.constainsUtilizador(id))
            facade.removerUtilizador(id);
    }

    ///////////////////////////////////////////
    /////////// Adicionar Funcionario//////////
    ///////////////////////////////////////////

    //Devolver um Boolean!!
    public boolean adicionarFuncionario(int id, String password) throws Exception{
        if(facade.constainsUtilizador(id))
            return false;
        Funcionario f = new Funcionario(id, password);
        facade.addUtilizador(f);
        return true;
    }

    ///////////////////////////////////////////
    ///////////// Imprimir fatura /////////////
    ///////////////////////////////////////////

    public String imprimirFatura(int clienteId, String Nif) throws Exception {
        if(facade.constainsCliente(clienteId))
            throw new Exception("Cliente não existe");
        Cliente c = facade.getCliente(clienteId);
        if(!c.getNif().equals(Nif))
            throw new Exception("Nif não é do Cliente");

        String fatura = this.enc.getFatura();
        float preco = this.enc.getPreco();
        fatura += "Preço total : " + Float.toString(preco) + "\n";
        return fatura;
    }
    */
}
