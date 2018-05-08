package ch.db_And_FHIR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbControl {

    //?autoReconnect=true
    static String dbAdress = "jdbc:mysql://db.course.hst.aau.dk:3306/hst_2018_18gr6404?&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String dbUsername = "hst_2018_18gr6404";         // UNI-NET DETALJER
    static String dbPassword = "eenuathaiheugohxahmo";

    public static Connection connect(){
        Connection connection = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex){
            System.out.println("The class was not found");
            //return null;
        }

        try{
            connection = DriverManager.getConnection(dbAdress, dbUsername, dbPassword);
            System.out.println("Connection established");
            return connection;
        }
        catch (SQLException sqlEx){
            System.out.println(sqlEx.getMessage());
            return null;
        }
    }
}
