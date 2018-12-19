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

       // System.out.println(new ClienteDAO().getCliente(1).getName()); -> método 1
       // System.out.println(new ClienteDAO().getUtilizador(1).getNome()); -> método 2
        //System.out.println(new ClienteDAO().constainsUtilizador(1)); -> método 3
       //new ClienteDAO().putUtilizador(new Funcionario("asd","sad","asd")); -> método 4
        //new DAOFacede().getStock().forEach(integerStringPair -> System.out.println(integerStringPair.getValue())); -> método 5
        //System.out.println(new DAOFacede().containsPeca(7)); -> método 6
    }
}
