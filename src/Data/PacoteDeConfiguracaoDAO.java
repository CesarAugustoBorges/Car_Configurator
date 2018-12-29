package Data;

import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;
import javafx.util.Pair;

import java.sql.*;
import java.util.*;

public class PacoteDeConfiguracaoDAO {
    Connection con;

    public Map<String, Pair<Integer, List<String>>> getAllPacotes() throws Exception{ // Map<Descricao, id>>

        con = Connect.connect();
        Map<String, Pair<Integer, List<String>>> result = new HashMap<>();

        if (con != null) {
            PreparedStatement ps = con.prepareStatement("Select PA.descricao as pacote, PA.id as IdPacote,PE.descricao as peca from Pacote as PA\n"+
                                                            "INNER Join PeçaDoPacote as PD on PA.id = PD.idPacote\n"+
                                                            "INNER JOIN Peça as PE on PD.idPeca = PE.id;");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String pacote = rs.getString("pacote");
                if(result.containsKey(pacote))
                   result.get(pacote).getValue().add(rs.getString("peca"));
                else{
                    int idPacote = Integer.parseInt(rs.getString("IdPacote"));
                    result.put(pacote, new Pair<>(idPacote, new ArrayList<>()));

                }
            }
        }else Connect.close(con);

        return result;

    }

    public PacoteDeConfiguracao getPacote(int id) throws Exception{

        con = Connect.connect();
        PacoteDeConfiguracao pc = new PacoteDeConfiguracao();
        Map<Peca, Integer> peças = new HashMap<>();

        if(con!=null) {


            PreparedStatement ps = con.prepareStatement("Select * from Pacote where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pc.setId(rs.getInt("id"));
                pc.setDescricao(rs.getString("descricao"));
                pc.setPreco(rs.getFloat("preco"));
            }

            ps = con.prepareStatement("Select * from PeçaDoPacote where idPacote = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            PeçaDAO pd = new PeçaDAO();

            while (rs.next()) {
                Peca aux = pd.getPeca(rs.getInt("idPeca"));
                peças.put(aux, 1);
            }

            pc.setPecas(peças);

        }else Connect.close(con);
        return  pc;
    }

    public Set<Integer> getIncompatibilidadesPacote(int id) throws Exception{

        con = Connect.connect();
        Set<Integer> result = new HashSet<>();

        if (con != null) {


            PreparedStatement ps = con.prepareStatement("select Pi.idincompativel from Peça as P inner join PeçasIncompativeis as Pi " +
                    "on P.id = Pi.id1 inner join PeçaDoPacote as PP on PP.idPeca = P.id where PP.idPacote = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(rs.getInt("idincompativel"));
            }

        } else Connect.close(con);

        return result;
    }

    public List<PacoteDeConfiguracao> pacotesComPeca(int id) throws Exception{
        con = Connect.connect();
        List<PacoteDeConfiguracao> result = new ArrayList<>();

        if (con != null) {

                PreparedStatement ps = con.prepareStatement("Select idPacote from PeçaDoPacote where idPeca = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    result.add(getPacote(rs.getInt("idPacote")));
                }

        } else Connect.close(con);

        return result;
    }


}
