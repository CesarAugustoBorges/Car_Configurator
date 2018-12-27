package Data;

import Business.Utilizador.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilizadorDAO {
    private Connection con;


    public void putUtilizador(Funcionario user) throws Exception{
        con = Connect.connect();
        if(con!=null) {

            PreparedStatement insertUser = con.prepareStatement("Insert Into Funcionario (id,nome,tipo,passe,nif) VALUES (?,?,?,?,?);");
            insertUser.setInt(1, user.getId());
            insertUser.setString(2, user.getNome());
            insertUser.setString(3, user.getTipo());
            insertUser.setString(4, user.getPasse());
            insertUser.setString(5,user.getTipo());


            //p representa o número de rows afetados
            int p = insertUser.executeUpdate();


        }else Connect.close(con);
    }


    public boolean constainsUtilizador(int id) throws Exception{
        if(getUtilizador(id) != null){
            return true;
        }
        return false;
    }

    public Funcionario getUtilizador(int id) throws Exception{
        con = Connect.connect();
        Funcionario c = null;

        if(con!=null) {

            PreparedStatement ps = con.prepareStatement("Select * from Funcionario Where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                c = new Funcionario();
                c.setId(rs.getInt("id"));
                c.setNif(rs.getString("nif"));
                c.setNome(rs.getString("nome"));
                c.setPasse(rs.getString("passe"));
                c.setTipo(rs.getString("tipo"));
            }

        }else{
            Connect.close(con);
        }
        return  c;
    }

    public boolean validaUtilizador(int userId, String pass) throws Exception{
        con = Connect.connect();
        Funcionario c = null;

        if(con!=null){

                PreparedStatement ps = con.prepareStatement("Select id,passe from Funcionario Where id = ? and passe = ?");
                ps.setInt(1,userId);
                ps.setString(2,pass);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    if (rs!=null){
                        return true;
                    }
                }

        }else{
            Connect.close(con);
        }
        return  false;
    }

    public boolean removerUtilizador(int id) throws Exception{
        if(getUtilizador(id)!=null){
            con = Connect.connect();
            Funcionario c = null;

            if(con!=null) {
                if(getUtilizador(id)!=null) {


                    PreparedStatement ps = con.prepareStatement("Delete from Funcionario where id = ?");
                    ps.setInt(1, id);

                    //numeros de rows modificadas
                    int a = ps.executeUpdate();
                }else  return false;

            }else{
                Connect.close(con);
            }
        }
        return true;
    }

}
