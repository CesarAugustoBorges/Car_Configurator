package Business;

import Business.Encomenda.Encomenda;
import Business.Encomenda.LinhaDeEncomenda;
import Business.Encomenda.LinhaDeEncomendaPeca;
import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import Business.Stock.StockInteger;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import Data.*;
import javafx.util.Pair;

import java.util.*;

public class Sistema {
    private DAOFacede facade = new DAOFacede();
    private Encomenda enc = new Encomenda();

    public Funcionario getFuncionario(int id) throws Exception{
        try{
            if(facade.constainsUtilizador(id))
                return facade.getUtilizador(id);
        } catch(Exception e){
            throw new Exception("Utililizador não encontrado: " + id);
        }
        return null;
    }

    public void putFuncionario(int id, String password) throws Exception{
        try{
            Funcionario novo = new Funcionario(id, password);
            facade.putUtilizador(novo);
        } catch (Exception e){
            throw new Exception("Funcionário não foi inserido: " + id);
        }
    }

    public Map<Integer,String> getStock() throws Exception{
        try{
            return facade.getStock();
        } catch (Exception e){
            throw new Exception("A informação do stock não foi concedida");
        }
    }

    public Pair<Integer, Integer> getInfoOfPeca(int id) throws Exception {
        try{
            return facade.getInfoOfPeca(id);
        } catch(Exception e){
            throw new Exception("Não foi encontrada informação da peça: " + id);
        }

    }

    public boolean containsPeca(int id) throws Exception {
        try{
            return facade.containsPeca(id);
        }catch (Exception e){
            throw  new Exception("Erro ao verificar a peça: " + id);
        }
    }

    public Peca getPeca(int id) throws Exception {
        try{
            return facade.getPeca(id);
        } catch(Exception e){
            throw  new Exception("Erro ao buscar informação sobre a peça: " + id);
        }
    }

    public PacoteDeConfiguracao getPacote(int id) throws  Exception {
        try{
            return facade.getPacote(id);
        }catch (Exception e){
            throw new Exception("Erro ao buscar informação sobre o pacote: " + id);
        }
    }
    // Metodo que verifica as credenciais do utilizador.
    // Deve retornar 0 se for Admin, 1 se for gestor, 2 se for cliente, -1 para credenciais erradas.
    public int login(String user, String password) throws Exception {
        int userId = Integer.parseInt(user);
        Funcionario f;
        try{
            if(!facade.constainsUtilizador(userId))
                return -1;
            f = facade.getUtilizador(userId);
        } catch(Exception e){
            throw new Exception("Erro em obter dados da base de dados");
        }

        if(f.isPassword(password))
            switch (f.getTipo()){
                case "Funcionario": return 2;
                case "Gestor": return 1;
                case "Admin" : return 0;
                default: break;
            }
        return -1;
    }

    public void addEncomenda() throws Exception  {
        facade.addEncomenda(this.enc);
    }

    public void rejeitarEncomenda(int id)throws Exception  {
        facade.removeEncomenda(id);
    }

    public void encomendarPeca(int id, int quantia) throws Exception{
        if(!facade.containsStock(id))
            throw new Exception("Stock não existe");
        int quantidade = facade.getQuantidadeAtualStock(id);
        int quantidadeMaxima = facade.getQuantidadeMaximaStock(id);
        if(quantidade + quantia <= quantidadeMaxima || quantia <= 0)
            throw new Exception("Quantidade excedida");
        facade.setQuantidadeAtualStock(id, quantia + quantidade);
    }

    public List<Pair<Integer,String>> getLsEIncompativeisComPeca(int id) throws Exception {
        Peca p = facade.getPeca(id);
        List<Pair<Integer,String>> incom = this.enc.getLEIncompativeisCom(p);
        return incom;
    }

    public List<Pair<Integer,String>> getLsEIncompativeisComPacote(int id) throws Exception  {
        PacoteDeConfiguracao p = facade.getPacote(id);
        List<Pair<Integer,String>> incom = this.enc.getLEIncompativeisCom(p);
        return incom;
    }

    public void removeLsE(List<Integer> ids){
        for(Integer id: ids)
            this.enc.removeLinhaEncomenda(id);
    }

    public void addPecas(List<Integer> ids) throws Exception {
        for(Integer id : ids)
            enc.addPeca(facade.getPeca(id), 1);
    }

    public List<Integer> getPecasObrigatorias(int id) throws Exception {
        Peca p = facade.getPeca(id);
        return this.enc.getPecasObrigatorias(p);
    }

    public void addPecasObrigatorias(int id) throws Exception {
        List<Integer> listaDependencias = facade.getDependenciasPeca(id);
        for(Integer p: listaDependencias) {
            Peca peca = facade.getPeca(id);
            this.enc.addPeca(peca, 1);
        }
    }

    public void addPeca(int id, int quantidade) throws Exception{
        Peca peca = getPeca(id);
        else this.enc.addPeca(peca, quantidade);
    }


    public void addPacote(int id) throws  Exception{
        PacoteDeConfiguracao pacote = getPacote(id);
        this.enc.addPacote(pacote);
    }

    ///////////////////////////////////////////
    ///////////// Remover Pacote //////////////
    ///////////////////////////////////////////

    public List<Pair<Integer, String>> getLsEDependentesPacote(int IdPacote) throws Exception {
        PacoteDeConfiguracao p = facade.getPacote(IdPacote);
        return this.enc.getLsEDependentes(p);
    }


    ///////////////////////////////////////////
    ///////////// Remover Funcionario//////////
    ///////////////////////////////////////////

    //Devolver um Boolean!!
    public boolean removerFuncionario(int id) throws Exception {
        if(facade.constainsUtilizador(id)){
            facade.removerUtilizador(id);
            return true;
        }
        return false;
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
        if(!facade.containsCliente(clienteId))
            throw new Exception("Cliente não existe");
        Cliente c = facade.getCliente(clienteId);
        if(!c.getNif().equals(Nif))
            throw new Exception("Nif não é do Cliente");

        String fatura = this.enc.getFatura();
        fatura += "Nome: " + c.getName() + " --- NIF: " + c.getNif();
        return fatura;
    }


    public Map<String, Pair<Integer,String>> getAllEncomendas() throws Exception {
        try{
            return facade.getAllEncomendas();
        } catch (Exception e){
            throw  new Exception("Erro ao buscar os dados das encomendas");
        }

    }

    public void removeLsEDependentesDePacote(List<Pair<Integer, Boolean>> les, int idPacote) throws Exception {
        PacoteDeConfiguracao pacote = getPacote(idPacote);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEDependenteDe(p.getKey(), p.getValue(), pacote);
    }

    public void removeLsEIncompativeisComPacote(List<Pair<Integer, Boolean>> les, int idPacote) throws Exception {
        PacoteDeConfiguracao pacote = getPacote(idPacote);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEIncompativelCom(p.getKey(), p.getValue(), pacote);
    }

    public void removeLsEDependentesDePeca(List<Pair<Integer, Boolean>> les, int idPeca) throws Exception {
        Peca peca = getPeca(idPeca);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEDependenteDe(p.getKey(), p.getValue(), peca);
    }

    public void removeLsEIncompativeisComPeca(List<Pair<Integer, Boolean>> les, int idPeca) throws Exception {
        Peca peca = getPeca(idPeca);
        for(Pair<Integer, Boolean> p : les)
            this.enc.removeLEIncompativelCom(p.getKey(), p.getValue(), peca);
    }

    public Pair<Integer, Integer> getStock(int id) throws Exception {
        try{
            int quantidadeAtual = facade.getQuantidadeAtualStock(id);
            int quantidadeMaxima = facade.getQuantidadeMaximaStock(id);
            Pair<Integer, Integer> res = new Pair<>(quantidadeAtual, quantidadeMaxima);
            return res;
        }catch (Exception e){
            throw new Exception("Erro ao obter dados sobre o stock de: " + id);
        }
    }

    public static void main(String[] args) {
        try{
            Sistema sis = new Sistema();
            sis.addPeca(1,1);
            sis.addPeca(2, 1);
            sis.addPeca(3, 1);
            List<Integer> leToRemove = new ArrayList<>();
            leToRemove.add(5);
            sis.removeLsE(leToRemove);
            //sis.addPeca(1000,1);
            System.out.println(sis.imprimirFatura(1, "123456789"));
        } catch(Exception e){
            e.printStackTrace();
        }


        /*
        ArrayList<Integer> arrP1d = new ArrayList<>(), arrP1I = new ArrayList<>();
        arrP1d.add(2); arrP1d.add(4);
        Peca p1 = new Peca(1,10.12f,"motor-1.0", "motor", arrP1d, arrP1I);

        ArrayList<Integer> arrP2d = new ArrayList<>(), arrP2I = new ArrayList<>();
        Peca p2 = new Peca(2,12.20f,"volante-2.1", "extra", arrP2d, arrP2I);

        ArrayList<Integer> arrP3d = new ArrayList<>(), arrP3I = new ArrayList<>();
        Peca p3 = new Peca( 3, 25.20f, "radio", "extras", arrP3d, arrP3I);

        Map<Peca, Integer> arrPacote1 = new HashMap<>();
        arrPacote1.put(p1, 2); arrPacote1.put(p3, 1);
        PacoteDeConfiguracao pacote1 = new PacoteDeConfiguracao(1, 25.20f, "Pacote desportivo", arrPacote1);

        Map<Peca, Integer> arrPacote2 = new HashMap<>();
        arrPacote2.put(p2, 2);
        PacoteDeConfiguracao pacote2 = new PacoteDeConfiguracao(2, 23.20f, "Pacote smooths", arrPacote2);

        //LinhaDeEncomenda le1 = new LinhaDeEncomendaPeca(1, 1, p1);
        LinhaDeEncomenda le2 = new LinhaDeEncomendaPeca(2, 1, p2);
        //LinhaDeEncomenda le3 = new LinhaDeEncomendaPacote(3, 1, pacote1);
        //LinhaDeEncomenda le4 = new LinhaDeEncomendaPacote(4, 1, pacote2);

        ArrayList<LinhaDeEncomenda> arrLsE = new ArrayList<>();
        //arrLsE.add(le1);
        arrLsE.add(le2);
        //arrLsE.add(le3);
        //arrLsE.add(le4);
        Encomenda enc = new Encomenda(1, arrLsE);

        for(Integer i : enc.getPecasObrigatorias(p1))
            System.out.println("Peca obrigatorioa :" +i);
        //System.out.println("Se a le com a peca 1 depende da peca 2: " + le1.dependeDe(p2));
        //System.out.println("Se a le com o pacote 1 depende do pacote 2: " + le3.dependeDe(pacote2));

        enc.removeLEDependenteDe(3, true, pacote2);
        System.out.println(enc.getFatura());

        */
    }
}
