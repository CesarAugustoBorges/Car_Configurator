package Data;

import Business.Stock.Peca;
import com.sun.java_cup.internal.runtime.lr_parser;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeçaDAO {

    private Connection con;

    public Map<String, Pair<Integer, String>> getAllPecas() throws Exception{ // Map<Descricao, Pair<Id, Categoria>>

        con = Connect.connect();
        Map<String, Pair<Integer, String>> result = new HashMap<>();

        if (con != null) {


            PreparedStatement ps = con.prepareStatement("Select * from Peça");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.put(rs.getString("descricao"), new Pair<>(rs.getInt("id"), rs.getString("categoria")));
            }
        }else Connect.close(con);

        return result;

    }

    public boolean containsPeca(int id) throws Exception{

        con = Connect.connect();

        if(con!=null){

            if(getPeca(id)!=null) return true;

        }else Connect.close(con);

        return false;
    }


    public Peca getPeca(int id) throws Exception{


        Peca p = new Peca();

        try {
            con = Connect.connect();

             try{
                con.setAutoCommit(false);


                p = new Peca();
                ArrayList<Integer> dependencia = new ArrayList<>();
                ArrayList<Integer> incompatibilidade = new ArrayList<>();

                PreparedStatement ps = con.prepareStatement("Select * from Peça where id = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                //trata da informação da peça
                while (rs.next()) {
                    p.setId(rs.getInt("id"));
                    p.setPreco(rs.getFloat("preco"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setDescricao(rs.getString("descricao"));
                }

                //trata das peças dependentes
                ps = con.prepareStatement("Select iddependente from PeçasDependentes where id1 = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                while (rs.next()) {
                    dependencia.add(rs.getInt("iddependente"));
                }

                p.setDependencias(dependencia);

                //trata das peças incompatíveis
                ps = con.prepareStatement("Select idincompativel from PeçasIncompativeis where id1 = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                while (rs.next()) {
                    incompatibilidade.add(rs.getInt("idincompativel"));
                }

                p.setIncompatibilidades(incompatibilidade);
                con.commit();

            }catch (SQLException e) {
            con.rollback();
            }
        }finally{
            Connect.close(con);
        }

        return p;
    }

    public Map<String, Map<Integer, Float>> getInfoForOtimização() throws Exception{

        con = Connect.connect();
        Map<String, Map<Integer, Float>>  result = new HashMap<>();

        if (con != null) {


            PreparedStatement ps = con.prepareStatement("Select * from Peça");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String desc = rs.getString("descricao");
                if (result.containsKey(desc)) {//se já contiver a desrição
                    result.get(desc).put(rs.getInt("id"), rs.getFloat("preco"));
                } else {
                    Map<Integer, Float> x = new HashMap<>();
                    x.put(rs.getInt("id"), rs.getFloat("preco"));
                    result.put(desc, x);
                }
            }

        } else Connect.close(con);

        return result;

    }

    public List<Peca> getPecasOfCategorias(String categoria) throws Exception{
        List<Peca> result = new ArrayList<>();

        con = Connect.connect();

        if (con != null) {


            PreparedStatement ps = con.prepareStatement("Select id from Peça\n where categoria = ?");
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(getPeca(rs.getInt("id")));
            }

        } else Connect.close(con);

        return result;
    }
}
