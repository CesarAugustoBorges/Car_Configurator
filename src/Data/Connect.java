package Data;

import java.sql.*;
import java.util.Properties;

public class Connect {

    //representa qual o sistema relacional de dados a usar
    private final static String bd = "mysql";
    //localhost do sistema operativo
    private final static String localhost = "3306";
    //representa qual a base de dados a usar
    private final static String db = "CarData";
    //representa a password do root
    private final static String password = "123cacbCACBCESAR";


    public static Connection connect(){

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");
        properties.setProperty("password", password);
        properties.setProperty("allowPublicKeyRetrieval","true");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn  = DriverManager.getConnection("jdbc:"+bd+"://localhost:"+localhost+"/"+db,properties);
            PreparedStatement ps = cn.prepareStatement("SET SQL_SAFE_UPDATES = 0;");
            ps.execute();

            return cn;
        }catch (ClassNotFoundException e){
            //unable to connect
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection cn){
        try{
            cn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
