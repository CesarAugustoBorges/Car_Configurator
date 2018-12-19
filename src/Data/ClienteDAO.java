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


    public void putUtilizador(Funcionario user){
        con = Connect.connect();
        if(con!=null){
            try {
                PreparedStatement insertUser = con.prepareStatement("Insert Into Funcionario (id,nome,nif,tipo,passe) VALUES (?,?,?,?,?);");
                insertUser.setInt(1,user.getId());
                insertUser.setString(2,user.getNome());
                insertUser.setString(3,user.getNif());
                insertUser.setString(4,user.getTipo());
                insertUser.setString(5,user.getPasse());


                //p representa o número de rows afetados
                int p = insertUser.executeUpdate();


            }catch (SQLException e){
                e.printStackTrace();
            }
        }else Connect.close(con);
    }


    public boolean constainsUtilizador(int id) {
        if(getUtilizador(id) != null){
            return true;
        }
        return false;
    }

    public Funcionario getUtilizador(int id){
        con = Connect.connect();
        Funcionario c = null;

        if(con!=null){
            try{
                PreparedStatement ps = con.prepareStatement("Select * from Funcionario Where id = ?");
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    c = new Funcionario();
                    c.setId(rs.getInt("id"));
                    c.setNif(rs.getString("nif"));
                    c.setNome(rs.getString("nome"));
                    c.setPasse(rs.getString("passe"));
                    c.setTipo(rs.getString("tipo"));
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            Connect.close(con);
        }
        return  c;
    }



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
