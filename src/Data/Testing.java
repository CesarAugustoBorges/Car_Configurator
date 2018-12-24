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

        System.out.println(new ClienteDAO().getCliente(1).getName()); //-> método 1
        System.out.println(new UtilizadorDAO().getUtilizador(1).getNome());// -> método 2
        System.out.println(new UtilizadorDAO().constainsUtilizador(1)); //-> método 3
        new UtilizadorDAO().putUtilizador(new Funcionario("asd","sad","asd")); //-> método 4
        new DAOFacede().getStock().forEach(integerStringPair -> System.out.println(integerStringPair.getValue())); //-> método 5
        System.out.println(new DAOFacede().containsPeca(7)); //-> método 6
        new DAOFacede().getIncompatibilidadesPeca(1).forEach(integer -> System.out.println(integer));
        System.out.println(new DAOFacede().validaUtilizador(1,"vedeta"));
        new DAOFacede().getIncompatibilidadesPacote(1).forEach(integer -> System.out.println(integer));
        new DAOFacede().getEncomenda(1).getLinhasDeEncomenda().forEach(linhaDeEncomenda -> System.out.println(linhaDeEncomenda.getQuantidade()));
        System.out.println(new DAOFacede().getQuantidadeAtualStock(1));
        new DAOFacede().removeEncomenda(1);


    }
}
