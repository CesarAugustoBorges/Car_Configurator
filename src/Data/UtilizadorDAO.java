package Data;

import Business.Utilizador.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilizadorDAO {
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

    public boolean validaUtilizador(int userId, String pass){
        con = Connect.connect();
        Funcionario c = null;

        if(con!=null){
            try{
                PreparedStatement ps = con.prepareStatement("Select id,passe from Funcionario Where id = ? and passe = ?");
                ps.setInt(1,userId);
                ps.setString(2,pass);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    if (rs!=null){
                        return true;
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            Connect.close(con);
        }
        return  false;
    }


    public void removerFuncionario(int id){
        if(getUtilizador(id)!=null){
            con = Connect.connect();
            Funcionario c = null;

            if(con!=null){
                try{
                    PreparedStatement ps = con.prepareStatement("Delete from Funcionario where id = ?");
                    ps.setInt(1,id);

                    //numeros de rows modificadas
                    int a = ps.executeUpdate();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                Connect.close(con);
            }
        }
    }

}
