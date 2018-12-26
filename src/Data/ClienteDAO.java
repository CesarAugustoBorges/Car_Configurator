package Data;

import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean containsCliente(int id) throws Exception{
        if(getCliente(id)!=null){
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

    public List<Integer> getEncomendasDeCliente(int id) throws Exception{

        con = Connect.connect();
        List<Integer> x = new ArrayList<>();

        if(con!=null) {

            PreparedStatement ps = con.prepareCall("SELECT idEncomenda FROM EncomendaCliente where idCliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                x.add(rs.getInt("idEncomenda"));
            }
            Connect.close(con);

        }else{
            Connect.close(con);
        }

        return x;
    }

}
