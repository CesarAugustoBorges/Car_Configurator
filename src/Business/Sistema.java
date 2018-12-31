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
    private Map<Integer,String> pecas;
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
            for (Integer x : pecas.keySet()) {

                if(pecas.get(x).equals(nome)){
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
            pecas = facade.getStock();
            return pecas;
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


    public void addEncomenda(String nif) throws Exception{
        if(this.valid())
            facade.addEncomenda(this.enc,nif);
    }

    public void rejeitarEncomenda(int id)throws Exception  {
        facade.removeEncomenda(id);
    }

    public void encomendarPeca(int id, int quantia) throws Exception{
        if(!facade.containsStock(id))
            throw new Exception("Stock não existe: " + id);
        int quantidade = facade.getQuantidadeAtualStock(id);
        int quantidadeMaxima = facade.getQuantidadeMaximaStock(id);
        if (quantidade + quantia > quantidadeMaxima || quantia <= 0)
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


    public boolean removerFuncionario(int id) throws Exception {
        if(facade.constainsUtilizador(id)){
            facade.removerUtilizador(id);
            return true;
        }
        return false;
    }

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


    public Map<String, Pair<Integer, String>> getEncomendasDeCliente(int id) throws Exception{
        try{
            Map<String, Pair<Integer, String>>encomendas = facade.getEncomendasDeCliente(id);
            if(encomendas.size() == 0)
                throw new Exception("O cliente \"" + id + "\" não tem encomendas");
            return encomendas;

        } catch (Exception e){
            throw  new Exception("O cliente + \"" + id + "\" não tem encomendas");
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
        facade.setStatusEncomenda(id, "Valida");
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
        Encomenda lockedEncomenda = new Encomenda();
        Integer[] nPecasEmConsideracao = new Integer[categoriasObrigatorias.size()];
        Map<String, List<Peca>> pecasEmCategoria = new HashMap<>();
        Map<String, Float> percentagem = new HashMap<>();

        int nCategorias = categoriasObrigatorias.size();
        for(int i = 0; i< nCategorias; i++){
            percentagem.put( categoriasObrigatorias.get(i), 1.f/nCategorias);
            nPecasEmConsideracao[i] = -1;
        }
        int i = 0;
        while(i >= 0){
            System.out.println(i);
            String categoria = categoriasObrigatorias.get(i);
            List<Peca> pecasOfCategoria = facade.getPecasOfCategorias(categoria);
            pecasOfCategoria.sort((p1, p2) -> Float.compare(p2.getPreco(), p1.getPreco()));
            for(int j = 0; j < pecasOfCategoria.size(); j++){
                Peca p = pecasOfCategoria.get(j);

                if(getPrecoCategoria(pecasEmCategoria.get(categoria)) <= percentagem.get(categoria) * quantiaMaxima){
                    if(getLsEIncompativeisComPeca(p.getId()).isEmpty()){
                        List<Integer> idsObrigatorias = getPecasObrigatorias(p.getId());
                        List<Peca> pecasObrigatorias = new ArrayList<>();
                        for(Integer id: idsObrigatorias) pecasObrigatorias.add(getPeca(id));

                        if(!pecasObrigatorias.isEmpty()){
                            int preco = 0;
                            boolean canAdd = true;
                            for(Peca peca : pecasObrigatorias)
                                if(peca.getPreco() + getPrecoCategoria(pecasEmCategoria.get(peca.getCategoria())) > percentagem.get(categoria) * quantiaMaxima)
                                    canAdd = false;
                            if(!canAdd) break;

                            for(Peca peca : pecasObrigatorias)
                                addPecaConfiguracaoOtima(encomenda, lockedEncomenda, peca, pecasEmCategoria, nPecasEmConsideracao);
                            addPecaConfiguracaoOtima(encomenda, lockedEncomenda, p, pecasEmCategoria, nPecasEmConsideracao);
                            break;

                        } else {
                            if(p.getPreco() + getPrecoCategoria(pecasEmCategoria.get(p.getCategoria())) > percentagem.get(categoria) * quantiaMaxima){
                                addPecaConfiguracaoOtima(encomenda, lockedEncomenda, p, pecasEmCategoria, nPecasEmConsideracao);
                                break;
                            }
                        }
                    }
                }
            }
            int next = getNextCategoria(nPecasEmConsideracao);
            if(i == next){
                encomenda = lockedEncomenda.clone();
                nPecasEmConsideracao[i] = 0;
                for(int k = 0; k < nPecasEmConsideracao.length; k++){
                    if(nPecasEmConsideracao[k] != 0)
                        nPecasEmConsideracao[k] = -1;
                }
                float percNeeded = pecasOfCategoria.get(pecasOfCategoria.size() - 1).getPreco() / quantiaMaxima;
                if(percNeeded >= 1)
                    throw new Exception("Não existe financiamento suficiente para: " + categoria);
                percentagem.replace(categoria, percNeeded);
                nPecasEmConsideracao[i] = 0;
                int categoriasLocked = getNCategoriasNotLocked(nPecasEmConsideracao);
                if(categoriasLocked >= categoriasObrigatorias.size() - 1)
                    throw new Exception("Nao existe configuração ótima com essa quantia");
                float percDifForEach = (percNeeded - percentagem.get(categoria))/(categoriasObrigatorias.size() - categoriasLocked);
                for(int k = 0; k < percentagem.size(); k++){
                    if(nPecasEmConsideracao[k] != 0){
                        percentagem.replace(categoria, percentagem.get(categoria) - percDifForEach);
                    }
                }
            }
            i = getNextCategoria(nPecasEmConsideracao);
        }
        this.enc = encomenda;
    }


    private int getNextCategoria(Integer[] cat){
        for(int i = 0; i < cat.length; i++)
            if(cat[i] < 0) return i;
        return -1;
    }

    private int getNCategoriasNotLocked(Integer[] cat){
        int n = 0;
        for(Integer i : cat)
            if(i == 0)
                n++;
        return n;
    }

    private float getPrecoCategoria(List<Peca> pecas){
        int preco = 0;
        if(pecas == null) return 0;
        for(Peca p: pecas)
            preco += p.getPreco();
        return preco;
    }

    private void addPecaConfiguracaoOtima(Encomenda enc, Encomenda saved, Peca p, Map<String, List<Peca>> nPecaEmCategoria, Integer[] flags){
        String categoria = p.getCategoria();
        int index = -1;
        for(int i = 0; i < categoriasObrigatorias.size(); i++)
            if(categoriasObrigatorias.get(i).equals(p.getCategoria()))
                index = i;
        flags[index] = 1;
        enc.addPeca(p,1);
        if(flags[index] == 0)
            saved.addPeca(p,1);
        if(nPecaEmCategoria.containsKey(categoria))
            nPecaEmCategoria.get(categoria).add(p);
    }

    public Map<String, Integer> possiblePacotesInEncomenda() throws Exception{
        Map<String, Integer> res = new HashMap<>();
        Set<Integer> pacotesTestados = new HashSet<>();
        List<LinhaDeEncomenda> les = this.enc.getLinhasDeEncomenda();
        for(LinhaDeEncomenda le: les)
            if(le instanceof LinhaDeEncomendaPeca){
                int idPeca = ((LinhaDeEncomendaPeca) le).getIdPeca();
                List<PacoteDeConfiguracao> pacotesComPeca = facade.pacotesComPeca(idPeca);
                for(PacoteDeConfiguracao pacote : pacotesComPeca)
                    if(!pacotesTestados.contains(pacote.getId())){
                        if(this.enc.canCreatePacote(pacote))
                            res.put(pacote.getDescricao(), pacote.getId());
                        pacotesTestados.add(pacote.getId());
                    }
            }
        return res;
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
            for(String s : sis.getEncomendasDeCliente(1).keySet()) System.out.println("Encomenda de "+ 1 +" " + s);
            for(int i = 1; i < 20; i++)
                sis.addPeca(i,2);

            //sis.addPeca(1000,1);
            //sis.configuracaoOtima(100);
            System.out.println(sis.imprimirFatura(1, "123456789"));
            for(String s : sis.possiblePacotesInEncomenda().keySet())
                System.out.println("pacotesPossiveis: " + s);

            //sis.addEncomenda();


        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
