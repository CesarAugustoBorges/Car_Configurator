package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Testing {
    public static void main(String args[]) throws Exception {

        System.out.println(new ClienteDAO().getCliente(1).getName());

    }
}
