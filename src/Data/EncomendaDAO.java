package Data;

import Business.Encomenda.*;
import Business.Stock.Peca;
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


    public void addEncomenda(Encomenda enc,String nif,String nome) throws Exception {

        try {
            con = Connect.connect();


            int idCliente;
            int idEncomenda = -1;

            try {
                con.setAutoCommit(false);

                //start the transaction

                List<LinhaDeEncomenda> ld = enc.getLinhasDeEncomenda();
                PreparedStatement ps;


                //caso o cliente não exista adiciono
                if((idCliente = new ClienteDAO().getIdCliente(nif)) == -1){
                    ps = con.prepareStatement("Insert into Cliente (nome,nif) VALUES (?,?)");
                    ps.setString(1,nome);
                    ps.setString(2,nif);
                    ps.executeUpdate();
                }

                idCliente = new ClienteDAO().getIdCliente(nif);//id do cliente


                ps = con.prepareStatement("Insert into Encomenda (estado,descricao,idCliente) VALUES (?,?,?)");
                ps.setString(1, enc.getStatus());
                ps.setString(2, enc.getDescricao());
                ps.setInt(3, idCliente);
                ps.executeUpdate();


                ps = con.prepareStatement("Select Max(id) from Encomenda;");
                ResultSet rs = ps.executeQuery();

                //de cretza que tem pois acabei de inserir uma encomenda
                while(rs.next()) {
                    idEncomenda = rs.getInt("Max(id)");
                }

                for (LinhaDeEncomenda tmp : ld) {

                    ps = con.prepareStatement("Insert into LDEncomenda  (preco,quantidade,idEncomenda) VALUES (?,?,?)");
                    ps.setFloat(1, tmp.getPrecoTotal());
                    ps.setInt(2, tmp.getQuantidade());
                    ps.setInt(3, idEncomenda);
                    ps.executeUpdate();
                }


                for (LinhaDeEncomenda tmp : ld) {

                    if (tmp instanceof LinhaDeEncomendaPeca) {//caso seja linha de encomenda peça
                        ps = con.prepareStatement("Insert into LDEPeça (idPeca,idLDEncomenda) VALUES(?,?)");
                        ps.setInt(1, ((LinhaDeEncomendaPeca) tmp).getIdPeca());
                        ps.setInt(2, idEncomenda);
                        ps.executeUpdate();

                    } else { //caso seja linha de encomenda pacote
                        ps = con.prepareStatement("Insert into LDEPacote (idPacote,idLDEncomenda) VALUES(?,?)");
                        ps.setInt(1, ((LinhaDeEncomendaPacote) tmp).getPacoteDeConfiguracao().getId());
                        ps.setInt(2, idCliente);
                        ps.executeUpdate();
                    }

                }
                con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
          }
    }finally {Connect.close(con);}

    }


    public Encomenda getEncomenda(int id) throws Exception{
        Connection con = Connect.connect();
        Encomenda enc = new Encomenda();
        ArrayList<LinhaDeEncomenda> l = new ArrayList<>();

        if(con!=null) {
            try {

                con.setAutoCommit(false);
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
                con.commit();
            }catch (SQLException e){
                con.rollback();
            }
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


            try {
                con.setAutoCommit(false);
                PreparedStatement ps = con.prepareStatement("select * from Encomenda where id = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs == null) {
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

                ps = con.prepareStatement("Delete from Encomenda where id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                con.commit();
                return true;

            }catch (SQLException e){
                con.rollback();
            }
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

    public Map<String, Pair<Integer, String>> getPeçasEncomenda(int id) throws Exception{
        con = Connect.connect();
        Map<String, Pair<Integer, String>> result = new HashMap<>();
        if(con!=null) {
            PreparedStatement ps = con.prepareStatement("Select idPeca from LDEPeça where idLDEncomenda = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            PeçaDAO peça = new PeçaDAO();
            while(rs.next()){
                int idPeca = rs.getInt("idPeca");
                PreparedStatement p = con.prepareStatement("Select categoria,descricao from Peça where id = ?");
                p.setInt(1,idPeca);
                ResultSet r = p.executeQuery();
                while(r.next()){
                    Pair<Integer, String> par = new Pair(idPeca, r.getString("categoria"));
                    result.put(r.getString("descricao"), par);
                }
            }

        }else Connect.close(con);

        return result;
    }

    public List<String> getPacotesEncomenda(int id) throws Exception{
        con = Connect.connect();
        List<String> result = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement("Select P.descricao as descricao from LDEPacote as LP\n"
                                                            + "INNER JOIN LDEncomenda as LDE on LP.idLDEncomenda = LDE.id\n"
                                                            + "INNER JOIN Pacote as P on P.id = LP.idPacote \n"
                                                            +" where LDE.idEncomenda = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                result.add(rs.getString("descricao"));
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception("Não foi possível obter os pacotes da Encomenda " + id);
        } finally {
            Connect.close(con);
        }

        return result;
    }

}
