package ch.db_And_FHIR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;



public class dbControl {


    //Opretter forbindelse til vores database

    //?autoReconnect=true
    static String dbAdress = "jdbc:mysql://db.course.hst.aau.dk:3306/hst_2018_18gr6404?&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String dbUsername = "hst_2018_18gr6404";         // UNI-NET DETALJER
    static String dbPassword = "eenuathaiheugohxahmo";


    public static Connection connect() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("The class was not found");
            //return null;
        }

        try {
            connection = DriverManager.getConnection(dbAdress, dbUsername, dbPassword);
            System.out.println("Connection established");
            return connection;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getMessage());
            return null;
        }
    }

//dbName= vores databases navn


    //Skal nok ikke være Void
    public void getPatientData(int patientCPR) {
        Connection con = connect();

        Statement stmnt = null;
        String query = "SELECT CPR, FIRSTNAME, LASTNAME, AGE, GENDER, PRACTITIONERID FROM PATIENT WHERE PATIENTCPR=";
        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                boolean gender = rs.getBoolean("gender");
                int practitionerID = rs.getInt("practitionerID");
                int cpr = rs.getInt("cpr");
                System.out.println(firstName + ", " + lastName +
                        ", " + age + ", " + gender+
                        ", " + cpr);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}


//getConnection();


/**  Statement con = Connection.createStatement();


 String SQL = "SELECT cpr, firstName, lastName, age, gender, practitionerID FROM Patient";

 ResultSet rs = con.createStatement().executeQuery(SQL);


 while (rs.next()) //next item is selected
 **/

