package Data;

import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

public class DAOFacede {

    private final ClienteDAO  ClDAO = new ClienteDAO();
    private final UtilizadorDAO UtlDAO = new UtilizadorDAO();
    private final PeçaDAO PDAO = new PeçaDAO();
    private final StockIntegerDAO StockDAO = new StockIntegerDAO();
    private final PacoteDeConfiguracaoDAO PteDAO = new PacoteDeConfiguracaoDAO();

    //devolve um Utilizador, Gestor ou Admin
    public Funcionario getUtilizador(int id) {//done
        return UtlDAO.getUtilizador(id);
    }

    //devolve um Cliente
    public Cliente getCliente(int id){//done
        return ClDAO.getCliente(id);
    }

    //verifica se existe um Utilizador
    public boolean constainsUtilizador(int id){//done
        return UtlDAO.constainsUtilizador(id);
    }

    //adiciona um novo utilizador
    public void putUtilizador(Funcionario user){
        UtlDAO.putUtilizador(user);
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

    //devolve uma determinada peça
    public Peca getPeca(int id){
        return PDAO.getPeca(id);
    }

    //remove um funcionário
    public void removerFuncionario(int id) {
        UtlDAO.removerFuncionario(id);
    }

    //devolve a lista de peças dependentes de uma determinada peça
    public List<Integer> getDependenciasPeca(int id){
        return PDAO.getPeca(id).getDependencias();
    }

    //devolve a lista de peças incompativeis de uma determinada peça
    public List<Integer> getIncompatibilidadesPeca(int id){
        return PDAO.getPeca(id).getIncompatibilidades();
    }

    //devolve um pacote
    public PacoteDeConfiguracao getPacote(int id){
        return PteDAO.getPacote(id);
    }

    //devolve as peças incomativeis de um pacote
    public Set<Integer> getIncompatibilidadesPacote(int id){
        return PteDAO.getIncompatibilidadesPacote(id);
    }

    //valida um utilizador
    public boolean validaUtilizador(int userId, String pass){
        return UtlDAO.validaUtilizador(userId,pass);
    }

}
