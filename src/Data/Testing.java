package Data;

import Business.Utilizador.Funcionario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Testing {
    public static void main(String args[]) throws Exception {

       // System.out.println(new ClienteDAO().getCliente(1).getName());
       // System.out.println(new ClienteDAO().getUtilizador(1).getNome());
        //System.out.println(new ClienteDAO().constainsUtilizador(1));

       new ClienteDAO().putUtilizador(new Funcionario("asd","sad","asd"));

    }
}
