package Data;

import Business.Encomenda.Encomenda;
import Business.Encomenda.LinhaDeEncomenda;
import Business.Encomenda.LinhaDeEncomendaPacote;
import Business.Encomenda.LinhaDeEncomendaPeca;

import java.awt.geom.RectangularShape;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EncomendaDAO {
    Connection con;

    public void addEncomenda(Encomenda enc){
        con = Connect.connect();
        if(con!=null){
            try{
                PreparedStatement ps = con.prepareStatement("Insert into Encomenda (id,status) VALUES (?,?)");
                ps.setInt(1,enc.getId());
                //ps.setString(2,enc.getStatus());
                int a = ps.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }


        }else Connect.close(con);
    }


    public Encomenda getEncomenda(int id){
        Connection con = Connect.connect();
        Encomenda enc = new Encomenda();
        ArrayList<LinhaDeEncomenda> l = new ArrayList<>();

        if(con!=null){
            try{

                PreparedStatement ps = con.prepareStatement("Select * from Encomenda where id = ?");
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    enc.setId(rs.getInt("id"));
                    enc.setStatus(rs.getString("status"));
                }

                //todas as linhas de encomenda peças
                ps = con.prepareStatement("select LDE.preco , LDE.quantidade , LDEPeça.id from Encomenda as E  inner join LDEncomenda as LDE on E.id = LDE.idEncomenda inner join LDEPeça on LDE.id = LDEPeça.idLDEncomenda where E.id = ?;");
                ps.setInt(1,id);
                rs = ps.executeQuery();

                while(rs.next()){
                    LinhaDeEncomendaPeca ldp = new LinhaDeEncomendaPeca();
                    ldp.setQuantidade(rs.getInt("quantidade"));
                    ldp.setId(rs.getInt("id"));
                    ldp.setPreco(rs.getFloat("preco"));
                    l.add(ldp);
                }

                //todas as linahs de encomenda pacotes
                ps = con.prepareStatement("select LDE.preco , LDE.quantidade , LDEPacote.id from Encomenda as E  inner join LDEncomenda as LDE on E.id = LDE.idEncomenda inner join LDEPacote on LDE.id = LDEPacote.idLDEncomenda where E.id = ?;");
                ps.setInt(1,id);
                rs = ps.executeQuery();

                while(rs.next()){
                    LinhaDeEncomendaPacote ldp = new LinhaDeEncomendaPacote();
                    ldp.setQuantidade(rs.getInt("quantidade"));
                    ldp.setId(rs.getInt("id"));
                    ldp.setPreco(rs.getFloat("preco"));
                    l.add(ldp);
                }


                //todas as linhas de encomenda
                enc.setLinhasDeEncomenda(l);

            }catch (SQLException e){
                e.printStackTrace();
            }
        }else Connect.close(con);

        return enc;
    }

}
