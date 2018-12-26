package Data;

import Business.Encomenda.Encomenda;
import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DAOFacede {

    private final ClienteDAO ClDAO = new ClienteDAO();
    private final UtilizadorDAO UtlDAO = new UtilizadorDAO();
    private final PeçaDAO PDAO = new PeçaDAO();
    private final StockIntegerDAO StockDAO = new StockIntegerDAO();
    private final PacoteDeConfiguracaoDAO PteDAO = new PacoteDeConfiguracaoDAO();
    private final EncomendaDAO EDAO = new EncomendaDAO();

    //devolve um Utilizador, Gestor ou Admin
    public Funcionario getUtilizador(int id)  throws Exception {//done
        return UtlDAO.getUtilizador(id);
    }

    //devolve um Cliente
    public Cliente getCliente(int id) throws Exception{//done
        return ClDAO.getCliente(id);
    }

    //verifica se existe um Utilizador
    public boolean constainsUtilizador(int id) throws Exception{//done
        return UtlDAO.constainsUtilizador(id);
    }

    //adiciona um novo utilizador
    public boolean putUtilizador(Funcionario user) throws Exception{
        UtlDAO.putUtilizador(user);
    }

    //Devolve todas as peças do sistema
    public Map<String, Pair<Integer, String>> getAllPecas() throws Exception{
        return PDAO.getAllPecas();
    }

    //Devolve a quantidade disponivel e máxima como um par
    public Pair<Integer, Integer> getInfoOfPeca(int id)throws Exception {
        return StockDAO.getInfoOfPeca(id);
    }

    //Devolve todas as encomendas do Sistema
    public Map<String, Pair<Integer, String>> getAllEncomendas() throws Exception{
        return EDAO.getAllEncomendas();
    }

    //verifica se existe uma peça em stock
    public boolean containsPeca(int id) throws Exception{
        return PDAO.containsPeca(id);
    }

    //devolve uma determinada peça
    public Peca getPeca(int id) throws Exception{
        return PDAO.getPeca(id);
    }

    //remove um utilizador
    public boolean removerUtilizador(int id)throws Exception {
        UtlDAO.removerUtilizador(id);
    }

    //devolve a lista de peças dependentes de uma determinada peça
    public List<Integer> getDependenciasPeca(int id) throws Exception{
        return PDAO.getPeca(id).getDependencias();
    }

    //devolve a lista de peças incompativeis de uma determinada peça
    public List<Integer> getIncompatibilidadesPeca(int id) throws Exception{
        return PDAO.getPeca(id).getIncompatibilidades();
    }

    //devolve um pacote
    public PacoteDeConfiguracao getPacote(int id) throws Exception {
        return PteDAO.getPacote(id);
    }

    //devolve as peças incomativeis de um pacote
    public Set<Integer> getIncompatibilidadesPacote(int id) throws Exception{
        return PteDAO.getIncompatibilidadesPacote(id);
    }

    //valida um utilizador
    public boolean validaUtilizador(int userId, String pass) throws Exception {
        return UtlDAO.validaUtilizador(userId, pass);
    }

    //adiciona uma peça
    public void addEncomenda(Encomenda enc) throws Exception {
        EDAO.addEncomenda(enc);
    }

    //devolve uma peça
    public Encomenda getEncomenda(int id) throws Exception{
        return EDAO.getEncomenda(id);
    }

    //verifica se contem um determinado cliente
    public boolean containsCliente(int id) throws Exception{
        return ClDAO.containsCliente(id);
    }

    //quantidade atual de uma peça em stock
    public int getQuantidadeAtualStock(int id) throws Exception{
        return StockDAO.getQuantidadeStock(id, "qtdisponivel");
    }

    //quantidade maxima de uma peça em stock
    public int getQuantidadeMaximaStock(int id) throws Exception{
        return StockDAO.getQuantidadeStock(id, "qtmaxima");
    }

    //define a quantidade atual de uma peça em stock
    public void setQuantidadeAtualStock(int id, int quantidade) throws Exception{
        StockDAO.setQuantidadeStock(id, "qtdisponivel", 10);
    }

    //define a qt maxima de uma peça em stock
    public void setQuantidadeMaximaStock(int id, String info, int quantidade) throws Exception{
        StockDAO.setQuantidadeStock(id, "qtmaxima", 10);
    }

    //remove uma encomenda do sistema
    public boolean removeEncomenda(int id) throws Exception{
        return EDAO.removeEncomenda(id);
    }

    //devolve a lista de stock
    public Map<Integer, String> getStock()throws Exception {
        return StockDAO.getStock();
    }

    //verifica se existem uma determinada peça em stock
    public boolean containsStock(int idPeca) throws Exception{
        return StockDAO.containsStock(idPeca);
    }

    //devolve as encomendas de um determinado cliente
    public List<Integer> getEncomendasDeCliente(int id) throws Exception{
        return ClDAO.getEncomendasDeCliente(id);
    }

    // Devolve um map em que tendo a categoria de uma peça, têm-se o Map em que endo o id de uma peça consegue-se o seu preço
    public Map<String, Map<Integer, Float>> getInfoForOtimização()throws Exception{
        return PDAO.getInfoForOtimização();
    }

    //Devvolve todos os pacotes com uma determinada peça
    public List<PacoteDeConfiguracao> pacotesComPeca(int id) throws Exception{
        return PteDAO.pacotesComPeca(id);
    }
}