package Business;

import Business.Encomenda.*;
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
        this.categoriasObrigatorias.add("Vidro Frente");
        this.categoriasObrigatorias.add("Vidro Tras");
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

    public List<String> getPacoteOfEncomenda(int x){
        try{
            return facade.getPacotesEncomenda(x);
        }
            catch (Exception e){
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
              //  System.out.println();
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


    public void addEncomenda(String nif,String nome) throws Exception{
        if(this.validaEncomendaAtual()) {

            if(!facade.containsCliente(nif)){
                facade.putCliente(new Cliente(nome,nif));
            }

            facade.addEncomenda(this.enc, nif,nome);
        }
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

    public Map<String, Integer> getLsEIncompativeisComPeca(int id) throws Exception {
        Peca p = facade.getPeca(id);
        Map<String, Integer> incom = this.enc.getLEIncompativeisCom(p);
        return incom;
    }

    public Map<String, Integer> getLsEIncompativeisComPacote(int id) throws Exception  {
        PacoteDeConfiguracao p = facade.getPacote(id);
        Map<String, Integer> incom = this.enc.getLEIncompativeisCom(p);
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

    public List<String> addPeca(int id, int quantidade) throws Exception{
        List<String> possiveisPacotes = new ArrayList<>();
        Peca peca = getPeca(id);
        this.enc.addPeca(peca, quantidade);
        List<PacoteDeConfiguracao> pacotes = facade.pacotesComPeca(id);
        for(PacoteDeConfiguracao pacote : pacotes)
            if(this.enc.canCreatePacote(pacote))
                possiveisPacotes.add(pacote.getDescricao());
        return possiveisPacotes;
    }

    public void addPeca(Peca peca, int quantidade) throws Exception{
        this.enc.addPeca(peca, quantidade);
    }


    public void addPacote(int id) throws  Exception{
        PacoteDeConfiguracao pacote = getPacote(id);
        this.enc.addPacote(pacote);
    }


    public Map<String, Integer> getLsEDependentesPacote(int IdPacote) throws Exception {
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

    public String imprimirFatura(String Nif) throws Exception {

        Cliente c = facade.getCliente(Nif);
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

    public boolean validaEncomendaAtual()throws Exception{
        return validaEncomenda(this.enc);
    }

    public boolean validaEncomenda(Encomenda enc) throws Exception{
        List<String> categorias = new ArrayList<>();
        for(String s : categoriasObrigatorias)
            categorias.add(s);
        return enc.valid(categorias);
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


    private Encomenda initConfiguracaoOtima(Map<String, List<Peca>> pecasEmCategoria) throws Exception{
        Encomenda configOtima = new Encomenda();
        // Construção da solução mais barata
        for(String categoria: pecasEmCategoria.keySet())
            pecasEmCategoria.get(categoria).sort(new Comparator<Peca>() {
                @Override
                public int compare(Peca peca, Peca t1) {
                    Float preco1, preco2;
                    preco1 = preco2 = 0.0f;
                    try{
                        preco1 = costToAddPeca(peca, configOtima);
                        preco2 = costToAddPeca(t1, configOtima);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    return Float.compare(preco1, preco2);
                }
            });
        for(String categoria: pecasEmCategoria.keySet()){
            if(!pecasEmCategoria.get(categoria).isEmpty()){
                Peca maisBarata = pecasEmCategoria.get(categoria).get(0);
                configOtima.addPeca(maisBarata,1);
                addDependenciasDePeca(maisBarata, configOtima);
                pecasEmCategoria.get(categoria).remove(0);
            }
        }
        return  configOtima;
    }

    public List<String> getPecasEncomenda(){
        List<String> res = new ArrayList<>();
        for(LinhaDeEncomenda l : this.enc.getLinhasDeEncomenda()){
            if(l instanceof LinhaDeEncomendaPeca){
                res.add(l.getDescricao());
            }
        }

        return res;
    }

    public List<String> getPacotesEncomenda(){
        List<String> res = new ArrayList<>();
        for(LinhaDeEncomenda l : this.enc.getLinhasDeEncomenda()){
            if(l instanceof LinhaDeEncomendaPacote){
                res.add(l.getDescricao());
            }
        }
        return res;
    }

    public void configuracaoOtima(int quantiaMaxima) throws Exception{
        Encomenda configOtima;
        Encomenda validConfigOtima = new Encomenda();
        Map<String, List<Peca>> pecasEmCategoria = new HashMap<>();

        for(String categoria: categoriasObrigatorias)
            pecasEmCategoria.put(categoria, facade.getPecasOfCategorias(categoria));

        configOtima = initConfiguracaoOtima(pecasEmCategoria);


        if(configOtima.getPreco() > quantiaMaxima)
            throw new Exception("Nao existe configuração ótima para a quantia escolhida");
        pecasEmCategoria.put("Extra", facade.getPecasOfCategorias("Extra"));
        // A cada iteraçao a encomenda fica mais cara
        while(true){
            if(configOtima.valid(categoriasObrigatorias) && configOtima.NoDepsAndNoInc())
                validConfigOtima = configOtima.clone();

            for(String categoria: pecasEmCategoria.keySet())
                pecasEmCategoria.get(categoria).sort(new Comparator<Peca>() {
                    @Override
                    public int compare(Peca peca, Peca t1) {
                        Float preco1, preco2;
                        preco1 = preco2 = 0.0f;
                        try{
                            preco1 = costToAddPeca(peca, configOtima);
                            preco2 = costToAddPeca(t1, configOtima);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        return Float.compare(preco1, preco2);
                    }
                });
            String categoria = getLowestCostCategoria(pecasEmCategoria, configOtima);
            if(pecasEmCategoria.get(categoria).isEmpty()) break;
            Peca maisBarata = pecasEmCategoria.get(categoria).get(0);

            if(categoria != "Extra" && configOtima.hasCategoriaFilled(maisBarata.getCategoria()))
                configOtima.removeCategoria(maisBarata.getCategoria());

            if(costToAddPeca(maisBarata, configOtima) + configOtima.getPreco() >= quantiaMaxima)
                break;

            configOtima.addPeca(maisBarata,1);
            for(Integer le: getLsEIncompativeisComPeca(maisBarata.getId()).values())
                configOtima.removeLinhaEncomenda(le);
            addDependenciasDePeca(maisBarata, configOtima);
            pecasEmCategoria.get(categoria).remove(0);
            if(pecasEmCategoria.get(categoria).isEmpty()) pecasEmCategoria.remove(categoria);
        }
        System.out.println(validConfigOtima.valid(categoriasObrigatorias));
        System.out.println(validConfigOtima.NoDepsAndNoInc());
        this.enc = validConfigOtima;
    }

    private void addDependenciasDePeca(Peca peca, Encomenda enc) throws Exception{
        for(Integer i: enc.getPecasObrigatorias(peca)){
            Peca p = facade.getPeca(i);
            enc.addPeca(p,1);
            addDependenciasDePeca(p, enc);
        }
    }

    private float costToAddPeca(Peca peca, Encomenda enc) throws Exception{
        Encomenda encWithPeca = enc.clone();
        encWithPeca.addPeca(peca,1);
        int custoTotal = 0;
        if(!encWithPeca.getLEIncompativeisCom(peca).isEmpty()) return 99999999999.98f;
        for(Integer i: enc.getPecasObrigatorias(peca)){
            Peca p = facade.getPeca(i);
            custoTotal += costToAddPeca(p, encWithPeca);
        }
        return peca.getPreco() + custoTotal;
    }

    private String getLowestCostCategoria(Map<String, List<Peca>> pecas, Encomenda enc) throws Exception{
        String categoria = "";
        for(String s : pecas.keySet())
            if(categoria == ""){
                categoria = s;
                break;
            }
        float custo = 99999999999.99f;

        for(String cat : pecas.keySet()){
            if(!pecas.get(cat).isEmpty()){
                float cost2compare = costToAddPeca(pecas.get(cat).get(0), enc);
                if(custo > cost2compare ){
                    categoria = cat;
                    custo = cost2compare;
                }
            }
        }
        return categoria;
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
            sis.addPeca(22,1);
            sis.addPeca(23,1);
            sis.addPeca(24,1);
            sis.addPeca(25,1);
            sis.addPeca(5,1);
            sis.addPeca(8,1);
            for(String s : sis.addPeca(29,1))
                System.out.print("É possivel fazer este pacote : " +s + "\n");

            System.out.println(sis.enc.getFatura());
            sis.createPacote(6);
            System.out.println(sis.enc.getFatura());

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
