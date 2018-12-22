package Data;

import Business.Utilizador.Cliente;
import Business.Utilizador.Funcionario;

import java.sql.*;

//Save - put(k : Object, o : Object) : void
//Get - get(key : Object) : Object
//List - list(?) : List<Object>
//Delete - remove(key : Object) : void
//Count - size() : int


public class ClienteDAO{

    private Connection con;

    public Cliente getCliente(int id){
        con = Connect.connect();
        Cliente c = null;

        if(con!=null){

            try{
                PreparedStatement ps = con.prepareStatement("Select * from Cliente Where id = ?");
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    c = new Cliente();
                    c.setName(rs.getString("nome"));
                    c.setId(rs.getInt("id"));
                    c.setNif(rs.getString("nif"));
                }

            }catch (SQLException e){
                e.printStackTrace();
            }


        }else Connect.close(con);

        return  c;

    }

    public boolean containsCliente(int id){
        if(getCliente(id)!=null){
            return true;
        }
        return false;
    }


    public void showClients(){

        con = Connect.connect();
        if(con!=null){
            try {
                PreparedStatement ps = con.prepareCall("SELECT * FROM Cliente");
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    System.out.println(rs.getString("nome"));
                }
                Connect.close(con);

            }catch (SQLException e){
                e.printStackTrace();
            }

        }else{
            Connect.close(con);
        }

    }


}
