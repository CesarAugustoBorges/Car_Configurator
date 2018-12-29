package Business;

import Business.Encomenda.Encomenda;
import Business.Encomenda.LinhaDeEncomenda;
import Business.Encomenda.LinhaDeEncomendaPeca;
import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import Data.*;
import javafx.util.Pair;

import java.util.*;

public class Sistema {

    private DAOFacede facade = new DAOFacede();
    private Encomenda enc = new Encomenda();
    private List<String> categoriasObrigatorias;

    //Encomenda, tal como Peça não pode ter nomes repetidos

    //Métodos -> Importante para não estar a repetir

    /**
     *  public int getIdEncomenda(String nome) -> devolve o id através do nome da encomenda
     *  public void putFuncionario(int id, String password) -> adiciona um novo funcionario
     *  public Map<Integer,String> getStock() -> devolve o stock do sistema
     *  public Pair<Integer, Integer> getInfoOfPeca(int id) -> devolve o stock atual e maximo de uma peça
     *  public boolean containsPeca(int id) -> verifica se uma peça existe
     *  public Peca getPeca(int id)
     *  public PacoteDeConfiguracao getPacote(int id)
     *  public int login(String user, String password)
     *  public void setStatusEncomenda(int id,String x)
     *  public void addEncomenda()
     *  public void rejeitarEncomenda(int id)
     *  public void encomendarPeca(int id, int quantia)
     *  public List<Pair<Integer,String>> getLsEIncompativeisComPeca(int id)
     *  public List<Pair<Integer,String>> getLsEIncompativeisComPacote(int id)
     *  public void removeLsE(List<Integer> ids)
     *  public Pair<Integer, Integer> getStock(int id)
     *  public void removeLsEDependentesDePacote(List<Pair<Integer, Boolean>> les, int idPacote)
     *  public void removeLsEIncompativeisComPacote(List<Pair<Integer, Boolean>> les, int idPacote)
     *  public void removeLsEDependentesDePeca(List<Pair<Integer, Boolean>> les, int idPeca)
     *  public void removeLsEIncompativeisComPeca(List<Pair<Integer, Boolean>> les, int idPeca)
     *  public List<Peca> getPecaOfEncomenda(int x)
     *  public List<PacoteDeConfiguracao> getPacoteOfEncomenda(int x)
     *  public String imprimirFatura(int clienteId, String Nif)
     *  public Map<String, Pair<Integer,String>> getAllEncomendas()
     *  public Encomenda getEncomenda(int id)
     *  public List<Integer> getEncomendasDeCliente(int id)
     *
     *
     *  FALTAM MAIS FUNÇOES
     */

    public Sistema(){
        this.categoriasObrigatorias = new ArrayList<>();
        this.categoriasObrigatorias.add("Motor");
        this.categoriasObrigatorias.add("Pintura");
        this.categoriasObrigatorias.add("Roda");
        this.categoriasObrigatorias.add("Volante");
        this.categoriasObrigatorias.add("Porta");
        this.categoriasObrigatorias.add("Vidro");
    }

    public Map<String, Pair<Integer, String>> getAllPecas() throws Exception{
        try{
            return facade.getAllPecas();
        }catch (Exception e){
            throw new Exception("Não foi possivel buscar a informação das peças");
        }
    }

    public Map<String, Pair<Integer, List<String>>> getAllPacotes() throws Exception{
        try{
            return facade.getAllPacotes();
        }catch (Exception e){
            throw new Exception("Não foi possivel buscar a informação dos pacotes");
        }
    }



    public Map<String, Pair<Integer, String>> getPecaOfEncomenda(int x){
        try {
            return facade.getPeçasEncomenda(x);
        }
        catch (Exception e){
            System.out.println("Encomenda inexistente na base de dados");
        }
        return null;
    }

    public List<PacoteDeConfiguracao> getPacoteOfEncomenda(int x){
        try {
            return facade.getPacotesEncomenda(x);
        }catch (Exception e){
            System.out.println("Encomenda inexistente na base de dados");
        }
        return null;
    }



    public int getIdEncomenda(String nome){
        try{
            for(String x : facade.getAllEncomendas().keySet()){
                if(x.equals(nome)){
                    return facade.getAllEncomendas().get(nome).getKey();
                }

            }
        }catch (Exception e){
            System.out.println("Encomenda " + nome + " não existe");
        }
        return -1;
    }

    public int getIdPeça(String nome){
        try {
            for (Integer x : getStock().keySet()) {
                if(getPeca(x).getDescricao().equals(nome)){
                    return  x;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

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
        System.out.println(userId + " " + password);

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

   public void setStatusEncomenda(int id,String x) throws Exception {
        try {
            facade.setStatusEncomenda(id,x);
        }catch (Exception e){
            System.out.println("Encomenda inválida");
        }
   }


    public void addEncomenda() throws Exception{
        if(this.valid())
            facade.addEncomenda(this.enc);
    }

    public void rejeitarEncomenda(int id)throws Exception  {
        facade.removeEncomenda(id);
    }

    public void encomendarPeca(int id, int quantia) throws Exception{
        if(!facade.containsStock(id))
            throw new Exception("Stock não existe: " + id);
        int quantidade = facade.getQuantidadeAtualStock(id);
        int quantidadeMaxima = facade.getQuantidadeMaximaStock(id);
        if (quantidade + quantia <= quantidadeMaxima || quantia <= 0)
            throw new Exception("Quantidade excedida: " + id);
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
        for(Integer id : ids)
            this.enc.removeLinhaEncomenda(id);
    }

    public void addPecas(List<Integer> ids) throws Exception{
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
        this.enc.addPeca(peca, quantidade);
    }

    public void addPeca(Peca peca, int quantidade) throws Exception{
        this.enc.addPeca(peca, quantidade);
    }


    public void addPacote(int id) throws  Exception{
        PacoteDeConfiguracao pacote = getPacote(id);
        this.enc.addPacote(pacote);
    }


    public List<Pair<Integer, String>> getLsEDependentesPacote(int IdPacote) throws Exception {
        PacoteDeConfiguracao p = facade.getPacote(IdPacote);
        return this.enc.getLsEDependentes(p);
    }

    //Devolver um Boolean!!
    public boolean removerFuncionario(int id) throws Exception {
        if(facade.constainsUtilizador(id)){
            facade.removerUtilizador(id);
            return true;
        }
        return false;
    }

    //Devolver um Boolean!!
    public boolean adicionarFuncionario(String nome ,int id, String password , String tipo,String nif) throws Exception{

        if(facade.constainsUtilizador(id))
            return false;
        Funcionario f = new Funcionario(id,nome, password,tipo,nif);
        facade.putUtilizador(f);
        return true;
    }

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

    public Encomenda getEncomenda(int id) throws Exception{
        try {
            return facade.getEncomenda(id);
        }catch (Exception e){
            System.out.println("Encomenda inválida");
        }
        return null;
    }


    public List<Integer> getEncomendasDeCliente(int id) throws Exception{
        try{
            return facade.getEncomendasDeCliente(id);
        } catch (Exception e){
            throw  new Exception("Cliente + " + id + " não tem encomendas");
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


    public boolean valid() throws Exception{
        List<String> categorias = new ArrayList<>();
        for(String s : categoriasObrigatorias)
            categorias.add(s);
        return this.enc.valid(categorias);
    }

    public void aceitaEncomenda(int id) throws Exception {
        Encomenda encomenda = facade.getEncomenda(id);
        checkStockForEncomenda(encomenda);
        removeStockFromEncomenda(encomenda);
        facade.setStatusEncomenda(id, "Aceite");
    }

    private void removeStockFromEncomenda(Encomenda encomenda)throws Exception {
        for(LinhaDeEncomenda le : encomenda.getLinhasDeEncomenda()){
            if(le instanceof LinhaDeEncomendaPeca){
                LinhaDeEncomendaPeca lep = (LinhaDeEncomendaPeca) le;
                int quantidadeDisponivel = facade.getQuantidadeAtualStock(lep.getIdPeca());
                facade.setQuantidadeAtualStock(lep.getIdPeca(), quantidadeDisponivel - lep.getQuantidade());
            }
        }
    }

    private void checkStockForEncomenda(Encomenda encomenda) throws Exception {
        for(LinhaDeEncomenda le : encomenda.getLinhasDeEncomenda()){
            if(le instanceof LinhaDeEncomendaPeca){
                LinhaDeEncomendaPeca lep = (LinhaDeEncomendaPeca) le;
                int quantidadeDisponivel = facade.getQuantidadeAtualStock(lep.getIdPeca());
                if(quantidadeDisponivel - lep.getQuantidade() < 0)
                    throw new Exception("Não existe peças no stock para satisfazer a encomenda: Peça - " + lep.getDescricao());
            }
        }
    }

    public void removePeca(int id, int quantidade){
        this.enc.removePeca(id, quantidade);
    }

    public void removePacote(int id, int quantidade){
        this.enc.removePacote(id, quantidade);
    }

    public void configuracaoOtima(int quantiaMaxima) throws Exception{
        Encomenda encomenda = new Encomenda();
        List<Integer> nPecasEmConsideracao = new ArrayList<>();
        Map<String, Integer> nPecasEmCategoria = new HashMap<>();
        List<Float> percentagem = new ArrayList<>();
        int nCategorias = categoriasObrigatorias.size();
        for(int i = 0; i< nCategorias; i++){
            percentagem.add(1.f/nCategorias);
            nPecasEmConsideracao.add(-1);
        }

        for(int i = 0; i < nCategorias; i++){
            List<Peca> pecasOfCategoria = facade.getPecasOfCategorias(categoriasObrigatorias.get(i));
            pecasOfCategoria.sort((p1, p2) -> Float.compare(p2.getPreco(), p1.getPreco()));
            System.out.println(categoriasObrigatorias.get(i) + "......." + pecasOfCategoria.size());
            for(int j = 0; j < pecasOfCategoria.size(); j++){
                Peca p = pecasOfCategoria.get(j);
                System.out.println(categoriasObrigatorias.get(i) + " ... " + p.getDescricao());

                if(p.getPreco() < percentagem.get(i) * quantiaMaxima){
                    if(getLsEIncompativeisComPeca(p.getId()).isEmpty()){
                        List<Integer> idsObrigatorias = getPecasObrigatorias(p.getId());
                        List<Peca> pecasObrigatorias = new ArrayList<>();
                        for(Integer id: idsObrigatorias) pecasObrigatorias.add(getPeca(id));

                        if(!pecasObrigatorias.isEmpty()){
                            int preco = 0;
                            for(Peca peca : pecasObrigatorias)
                                preco += peca.getPreco();
                            if(preco + p.getPreco() < percentagem.get(i) * quantiaMaxima){
                                for(Peca peca : pecasObrigatorias)
                                    addPecaConfiguracaoOtima(encomenda, peca, nPecasEmCategoria);
                                addPecaConfiguracaoOtima(encomenda, p, nPecasEmCategoria);
                                break;
                            }
                        } else {
                            addPecaConfiguracaoOtima(encomenda, p, nPecasEmCategoria);
                            break;
                        }
                    }
                }
            }
        }
        this.enc = encomenda;
    }


    private void addPecaConfiguracaoOtima(Encomenda enc,Peca p, Map<String, Integer> nPecaEmCategoria){
        String categoria = p.getCategoria();
        enc.addPeca(p,1);
        if(nPecaEmCategoria.containsKey(categoria))
            nPecaEmCategoria.replace(categoria, nPecaEmCategoria.get(categoria) +1);
    }

    public boolean canCreatePacote(int id) throws Exception{
        PacoteDeConfiguracao pacote = getPacote(id);
        return this.enc.canCreatePacote(pacote);
    }

    public void createPacote(int id) throws  Exception{
        PacoteDeConfiguracao pacote = getPacote(id);
        this.enc.createPacote(pacote);
    }

    public static void main(String[] args) {
        try{
            Sistema sis = new Sistema();

            sis.addPeca(1,2);
            sis.addPeca(1,2);
            sis.addPeca(2, 1);
            sis.addPeca(3, 1);
            sis.addPeca(6, 1);
            sis.addPeca(8, 1);
            sis.addPeca(7, 1);

            List<Integer> leToRemove = new ArrayList<>();
            sis.removeLsE(leToRemove);
            //sis.addPeca(1000,1);
            sis.removePeca(1,1);
            System.out.println(sis.imprimirFatura(1, "123456789"));
            System.out.println(sis.canCreatePacote(2));
            if(sis.canCreatePacote(2));
                sis.createPacote(2);
            //sis.configuracaoOtima(100000);
            System.out.println(sis.imprimirFatura(1, "123456789"));
            //sis.addEncomenda();


        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
