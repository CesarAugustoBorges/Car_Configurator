package Data;

import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PacoteDeConfiguracaoDAO {
    Connection con;

    public PacoteDeConfiguracao getPacote(int id){

        con = Connect.connect();
        PacoteDeConfiguracao pc = new PacoteDeConfiguracao();
        ArrayList<Peca> peças = new ArrayList<>();

        if(con!=null){

            try {
                PreparedStatement ps = con.prepareStatement("Select * from Pacote where id = ?");
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    pc.setId(rs.getInt("id"));
                    pc.setDescricao(rs.getString("descricao"));
                    pc.setPreco(rs.getFloat("preco"));
                }

                ps = con.prepareStatement("Select * from Peca where idPacote = ?");
                ps.setInt(1,id);
                rs = ps.executeQuery();
                PeçaDAO pd = new PeçaDAO();

                while(rs.next()){
                    Peca aux = pd.getPeca(rs.getInt("id"));
                    peças.add(aux);
                }

                pc.setPecas(new HashMap<>());

            }catch (SQLException e){
                e.printStackTrace();
            }

        }else Connect.close(con);

        return  pc;
    }

    public Set<Integer> getIncompatibilidadesPacote(int id) {

        con = Connect.connect();
        Set<Integer> result = new HashSet<>();

        if (con != null) {

            try {
                PreparedStatement ps = con.prepareStatement(" select Pi.idincompativel from Peça as P inner join PeçasIncompativeis as Pi " +
                        "on P.id = Pi.id1 where P.idPacote = ?;");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    result.add(rs.getInt("idincompativel"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else Connect.close(con);

        return result;
    }

}
