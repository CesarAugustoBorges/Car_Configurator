package Business;

import Business.Encomenda.Encomenda;
import Business.Encomenda.LinhaDeEncomenda;
import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import Business.Stock.StockInteger;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import Data.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Map<Integer,String> getStock() {
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
    ////////// Encomendar Veículo ////////////
    ///////////////////////////////////////////
    //public void addEncomenda() {
    //    facade.addEncomenda(this.enc);
    //}

    ///////////////////////////////////////////
    ////////// Rejeitar Encomenda ////////////
    ///////////////////////////////////////////
    //public void rejeitarEncomenda(id) {
    //    facade.removeEncomenda(id);
    //}

    ///////////////////////////////////////////
    //////////// Encomendar pecas /////////////
    ///////////////////////////////////////////


    public void encomendarPeca(int id, int quantia) throws Exception{
        if(!facade.containsStock(id))
            throw new Exception("Stock não existe");
        int quantidade = facade.getQuantidadeAtualStock(id);
        int quantidadeMaxima = facade.getQuantidadeMaximaStock(id);
        if(quantidade + quantia <= quantidadeMaxima || quantia <= 0)
            throw new Exception("Quantidade excedida");
        facade.setQuantidadeAtualStock(id, quantia + quantidade);
    }

    ///////////////////////////////////////////
    ////////////// Adiciona Peca //////////////
    ///////////////////////////////////////////

    public List<Pair<Integer,String>> getLsEIncompativeisComPeca(int id) {
        Peca p = facade.getPeca(id);
        List<Pair<Integer,String>> incom = this.enc.getLEIncompativeisCom(p);
        return incom;
    }

    public List<Pair<Integer,String>> getLsEIncompativeisComPacote(int id) {
        PacoteDeConfiguracao p = facade.getPacote(id);
        List<Pair<Integer,String>> incom = this.enc.getLEIncompativeisCom(p);
        return incom;
    }

    public void removeLsE(List<Integer> ids){
        for(Integer id: ids)
            this.enc.removeLinhaEncomenda(id);
    }

    public void addPecas(List<Integer> ids){
        for(Integer id : ids)
            enc.addPeca(facade.getPeca(id), 1);
    }

    public List<Integer> getPecasObrigatorias(int id){
        Peca p = facade.getPeca(id);
        return this.enc.getPecasObrigatorias(p);
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
        PacoteDeConfiguracao p = facade.getPacote(IdPacote);
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
        facade.putUtilizador(f);
        return true;
    }

    ///////////////////////////////////////////
    ///////////// Imprimir fatura /////////////
    ///////////////////////////////////////////

    public String imprimirFatura(int clienteId, String Nif) throws Exception {
        if(facade.containsCliente(clienteId))
            throw new Exception("Cliente não existe");
        Cliente c = facade.getCliente(clienteId);
        if(!c.getNif().equals(Nif))
            throw new Exception("Nif não é do Cliente");

        String fatura = this.enc.getFatura();
        float preco = this.enc.getPreco();
        fatura += "Preço total : " + Float.toString(preco) + "\n";
        return fatura;
    }


    public Map<String, Pair<Integer,String>> getAllEncomendas(){
        return facade.getAllEncomendas();
    }

    public void removeLsEDependentesDePacote(List<Pair<Integer, Boolean>> les, int idPacote){
        PacoteDeConfiguracao pacote = facade.getPacote(idPacote);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEDependenteDe(p.getKey(), p.getValue(), pacote);
    }

    public void removeLsEIncompativeisComPacote(List<Pair<Integer, Boolean>> les, int idPacote){
        PacoteDeConfiguracao pacote = facade.getPacote(idPacote);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEIncompativelCom(p.getKey(), p.getValue(), pacote);
    }

    public void removeLsEDependentesDePeca(List<Pair<Integer, Boolean>> les, int idPeca){
        Peca peca = facade.getPeca(idPeca);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEDependenteDe(p.getKey(), p.getValue(), peca);
    }

    public void removeLsEIncompativeisComPeca(List<Pair<Integer, Boolean>> les, int idPeca){
        Peca peca = facade.getPeca(idPeca);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEIncompativelCom(p.getKey(), p.getValue(), peca);
    }

    public Pair<Integer, Integer> getStock(int id){
        int quantidadeAtual = facade.getQuantidadeAtualStock(id);
        int quantidadeMaxima = facade.getQuantidadeMaximaStock(id);
        Pair<Integer, Integer> res = new Pair<>(quantidadeAtual, quantidadeMaxima);
        return res;
    }

    public Pair<Integer, String> checkPacotes(){
        int nPacotes = facade.getNumberOfPacotes();
        for(int i = 0; i < nPacotes; i++){
            PacoteDeConfiguracao pacote = facade.getNthPacote(i);
            if(this.enc.canCreatePacote(pacote))
                return new Pair<>(pacote.getId(), pacote.getDescricao());
        }
        return null;
    }
}
