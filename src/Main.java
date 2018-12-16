import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String args[]) throws Exception{
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "123cacbCACBCESAR");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        String db;
        db = "Npng";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, properties);
        String sql = "Select * FROM Aula;";
        Statement st = cn.createStatement();
        ResultSet res = st.executeQuery(sql);
        while(res.next()){
            System.out.println("Descrição: "+ res.getString("Descrição"));
        }
    }
}
