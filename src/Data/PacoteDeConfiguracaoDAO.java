package Data;

import Business.Encomenda.PacoteDeConfiguracao;
import Business.Stock.Peca;

import java.sql.*;
import java.util.ArrayList;

public class PacoteDeConfiguracaoDAO {
    Connection con;

    public PacoteDeConfiguracao getPacote(int id){

        con = Connect.connect();
        PacoteDeConfiguracao pc = new PacoteDeConfiguracao();
        ArrayList<Peca> result = new ArrayList<>();

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


                ps = con.prepareStatement("Select idpeça from Peça_PacoteIncompativeis where id = ?");
                ps.setInt(1,id);
                rs = ps.executeQuery();

                while(rs.next()){
                    result.add(new PeçaDAO().getPeca((rs.getInt("idpeça"))));
                }

                pc.setPecas(result);



            }catch (SQLException e){
                e.printStackTrace();
            }

        }else Connect.close(con);

        return  pc;
    }

}
