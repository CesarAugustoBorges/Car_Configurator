package Data;

import Business.Encomenda.Encomenda;
import Business.Encomenda.LinhaDeEncomenda;
import Business.Encomenda.LinhaDeEncomendaPacote;
import Business.Encomenda.LinhaDeEncomendaPeca;
import javafx.util.Pair;

import java.awt.geom.RectangularShape;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Condition;

public class EncomendaDAO {

    Connection con;

    public void addEncomenda(Encomenda enc) throws Exception{
        con = Connect.connect();
        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Insert into Encomenda (id,estado,descricao) VALUES (?,?,?)");
            ps.setInt(1, enc.getId());
            ps.setString(2, enc.getStatus());
            ps.setString(3, enc.getDescricao());
            int a = ps.executeUpdate();


        }
        else Connect.close(con);
    }


    public Encomenda getEncomenda(int id) throws Exception{
        Connection con = Connect.connect();
        Encomenda enc = new Encomenda();
        ArrayList<LinhaDeEncomenda> l = new ArrayList<>();

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select * from Encomenda where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                enc.setId(rs.getInt("id"));
                enc.setStatus(rs.getString("estado"));
                enc.setDescricao(rs.getString("descricao"));
            }

            //todas as linhas de encomenda peças
            ps = con.prepareStatement("select LDE.preco , LDE.quantidade , LDEPeça.idPeca from Encomenda as E  inner join LDEncomenda as LDE on E.id = LDE.idEncomenda inner join LDEPeça on LDE.id = LDEPeça.idLDEncomenda where E.id = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                LinhaDeEncomendaPeca ldp = new LinhaDeEncomendaPeca();
                ldp.setQuantidade(rs.getInt("quantidade"));
                ldp.setId(rs.getInt("idPeca"));
                ldp.setPreco(rs.getFloat("preco"));
                l.add(ldp);
            }

            //todas as linahs de encomenda pacotes
            ps = con.prepareStatement("select LDE.preco , LDE.quantidade , LDEPacote.idPacote from Encomenda as E  inner join LDEncomenda as LDE on E.id = LDE.idEncomenda inner join LDEPacote on LDE.id = LDEPacote.idLDEncomenda where E.id = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                LinhaDeEncomendaPacote ldp = new LinhaDeEncomendaPacote();
                ldp.setQuantidade(rs.getInt("quantidade"));
                ldp.setId(rs.getInt("idPacote"));
                ldp.setPreco(rs.getFloat("preco"));
                l.add(ldp);
            }


            //todas as linhas de encomenda
            enc.setLinhasDeEncomenda(l);
        }else Connect.close(con);

        return enc;
    }

    //Map<Descricao, Pair<Id, Categoria>>
    public Map<String, Pair<Integer, String>> getAllEncomendas() throws Exception{

        con = Connect.connect();
        Map<String, Pair<Integer, String>> result = new HashMap<>();

        if(con!=null) {
            PreparedStatement ps = con.prepareStatement("Select * from Encomenda");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.put(rs.getString("descricao"), new Pair<>(rs.getInt("id"), rs.getString("estado")));
            }

        }else Connect.close(con);

        return result;
    }


    public boolean removeEncomenda(int id) throws Exception{

        con = Connect.connect();

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("select * from Encomenda where id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs==null){
                return true;
            }

            ps = con.prepareStatement("Delete from LDEPeça where idLDEncomenda in ( select LD.id from Encomenda as E inner join LDEncomenda  AS LD on E.id = LD.idEncomenda where E.id = ?);");
            ps.setInt(1, id);
            ps.executeUpdate();


            ps = con.prepareStatement("Delete from LDEPacote where idLDEncomenda in ( select LD.id from Encomenda as E inner join LDEncomenda  AS LD on E.id = LD.idEncomenda where E.id = ?);");
            ps.setInt(1, id);
            ps.executeUpdate();

            ps = con.prepareStatement("Delete from LDEncomenda where idEncomenda = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            ps = con.prepareStatement("Delete from EncomendaCliente where idEncomenda =?");
            ps.setInt(1,id);
            ps.executeUpdate();

            ps = con.prepareStatement("Delete from Encomenda where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;


        }else Connect.close(con);

        return false;
    }

    public void setStatusEncomenda(int id,String x) throws Exception{

        con = Connect.connect();

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Update Encomenda Set estado = ? where id = ?");
            ps.setString(1,x);
            ps.setInt(2, id);
            int a = ps.executeUpdate();

        }else Connect.close(con);
    }

}
