package Data;

import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockIntegerDAO {

    Connection con ;

    public Pair<Integer,Integer> getInfoOfPeca(int id) throws Exception{

        con = Connect.connect();
        Pair<Integer,Integer> result = null;

        if(con!=null) {


            PreparedStatement ps = con.prepareStatement("Select qtmaxima,qtdisponivel From Stock where idPeça = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result = new Pair<>(rs.getInt("qtmaxima"), rs.getInt("qtdisponivel"));
            }

        }else Connect.close(con);

        return result;
    }




    public int getQuantidadeStock(int id,String info) throws Exception{

        con = Connect.connect();
        int res = 0;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select " + info + " from Stock where idPeça = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                res = rs.getInt(info);
            }

        }else Connect.close(con);

        return res;
    }

    public int setQuantidadeStock(int id,String info,int quantidade) throws Exception{
        con = Connect.connect();
        int res = 0;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("UPDATE Stock SET " + info + " = ? where idPeça = ?;");
            ps.setInt(1, quantidade);
            ps.setInt(2, id);
            int a = ps.executeUpdate();

        }else Connect.close(con);

        return res;
    }

    public boolean containsStock(int idPeca) throws Exception{
        con = Connect.connect();
        int res = 0;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select idPeça from Stock where idPeça = ?");
            ps.setInt(1, idPeca);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                return true;
            }

        }else Connect.close(con);

        return false;
    }

    public Map<Integer,String> getStock() throws Exception {
        con = Connect.connect();
        Map<Integer,String> result = new HashMap<>();

        if (con != null) {

            PreparedStatement ps = con.prepareStatement("Select P.id,P.descricao from Stock as S inner join Peça as P on P.id = S.idPeça");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.put(rs.getInt("id"), rs.getString("descricao"));
            }

        } else Connect.close(con);

        return result;
    }
}
