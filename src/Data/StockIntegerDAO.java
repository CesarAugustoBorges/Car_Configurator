package Data;

import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockIntegerDAO {

    Connection con ;

    public Pair<Integer,Integer> getInfoOfPeca(int id){

        con = Connect.connect();
        Pair<Integer,Integer> result = null;

        if(con!=null){

            try{
                PreparedStatement ps = con.prepareStatement("Select qtmaxima,qtdisponivel From Stock where idPeça = ?");
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    result = new Pair<>(rs.getInt("qtmaxima"),rs.getInt("qtdisponivel"));
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }else Connect.close(con);

        return result;
    }


    public boolean containsPeca(int id){

        con = Connect.connect();

        if(con!=null){

            if(getInfoOfPeca(id)!=null) return true;

        }else Connect.close(con);

        return false;
    }
}
