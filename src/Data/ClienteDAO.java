package Data;

import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Save - put(k : Object, o : Object) : void
//Get - get(key : Object) : Object
//List - list(?) : List<Object>
//Delete - remove(key : Object) : void
//Count - size() : int


public class ClienteDAO{

    private Connection con;

    public Cliente getCliente(int id) throws Exception{
        con = Connect.connect();
        Cliente c = null;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select * from Cliente Where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                c = new Cliente();
                c.setName(rs.getString("nome"));
                c.setId(rs.getInt("id"));
                c.setNif(rs.getString("nif"));
            }


        }else Connect.close(con);

        return  c;

    }

    public Cliente getCliente(String nif) throws Exception{
        con = Connect.connect();
        Cliente c = null;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select * from Cliente Where nif = ?");
            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                c = new Cliente();
                c.setName(rs.getString("nome"));
                c.setId(rs.getInt("id"));
                c.setNif(rs.getString("nif"));
            }


        }else Connect.close(con);

        return  c;

    }

    public void putCliente(Cliente x) throws Exception{
        try {
            con = Connect.connect();

            if (con != null) {

                PreparedStatement ps = con.prepareStatement("Insert into Cliente (nif,nome) VALUES (?,?)");
                ps.setString(1, x.getNif());
                ps.setString(2,x.getName());

                int a = ps.executeUpdate();

            }
        }finally {
            Connect.close(con);
        }
    }


    public int getIdCliente(String nif) throws Exception{
        con = Connect.connect();
        Cliente c = null;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select id from Cliente Where nif = ?");
            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                return rs.getInt("id");
            }


        }else Connect.close(con);

        return  -1;

    }


    public boolean containsCliente(int id) throws Exception{
        if(getCliente(id)!=null){
            return true;
        }
        return false;
    }

    public boolean containsCliente(String nif) throws Exception{
        if(getCliente(nif)!=null){
            return true;
        }
        return false;
    }


    public void showClients() throws Exception{

        con = Connect.connect();
        if(con!=null) {

            PreparedStatement ps = con.prepareCall("SELECT * FROM Cliente");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("nome"));
            }
            Connect.close(con);
        } else{
            Connect.close(con);
        }

    }

    public Map<String, Pair<Integer, String>> getEncomendasDeCliente(int id) throws Exception{

        con = Connect.connect();
        Map<String, Pair<Integer, String>> x = new HashMap<>();

        if(con!=null) {

            PreparedStatement ps = con.prepareCall("SELECT id, descricao, estado FROM Encomenda where idCliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                x.put(rs.getString("descricao"), new Pair<>(rs.getInt("id"), rs.getString("estado")));
            }
            Connect.close(con);

        }else{
            Connect.close(con);
        }

        return x;
    }

}
