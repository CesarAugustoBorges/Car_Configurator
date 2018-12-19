package Data;

import com.sun.java_cup.internal.runtime.lr_parser;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeçaDAO {

    Connection con;

    public List<Pair<Integer, String>> getStock() {
        con = Connect.connect();
        List<Pair<Integer, String>> result = new ArrayList<>();

        if (con != null) {
            try {

                PreparedStatement ps = con.prepareStatement("Select id,categoria from Peça");
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    result.add(new Pair<>(rs.getInt("id"),rs.getString("categoria")));
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        } else Connect.close(con);

        return result;
    }
}
