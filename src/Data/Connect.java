package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {

    //representa qual o sistema relacional de dados a usar
    private final static String bd = "mysql";
    //localhost do sistema operativo
    private final static String localhost = "3306";
    //representa qual a base de dados a usar
    private final static String db = "CarData";
    //representa a password do root
<<<<<<< HEAD
    private final static String password = "andreguigui1";
=======
    private final static String password = "123cacbCACBCESAR";
>>>>>>> ba32da2fa1550d8e0b1cb610c8746049d2055dd9


    public static Connection connect(){

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");
        properties.setProperty("password", password);

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
