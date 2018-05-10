package ch.db_And_FHIR;

import ch.utility.dateUtil;
import org.hl7.fhir.dstu3.model.Patient;
import java.util.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import org.hl7.fhir.dstu3.model.*;



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

    public void getPatientData(int patientCPR) {
        Connection con = connect();

        Statement stmnt = null;
        String query = "SELECT cpr, firstName, lastName, age, gender, practitionerID FROM Patient WHERE cpr=" + patientCPR;

        try {
            stmnt = con.createStatement();
            //I vores ResultSet(statement objekter generere ResultSet objektet, som er en tabel af data som repræsentere en DB resultset)
            //sætter vi den til at execute en forespørgelse, "executeQuery" er en metode der returnere ResultSet objektet, vi vil gerne
            //returneret vores query og derfor sættes den som argument.

            ResultSet rs = stmnt.executeQuery(query);

            //Næste metode (rs.next) i objektet rs flytter cursoren (en pointer der peger på 1 række i data i ResultSet objektet).

            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                int practitionerID = rs.getInt("practitionerID");
                int cpr = rs.getInt("cpr");
                System.out.println(firstName + ", " + lastName +
                        ", " + age + ", " + gender+
                        ", " + cpr + ", " + practitionerID);

            }
            /* Patient firstName = new Patient();
            String cpr = cpr;
            firstName.addIdentifier().setSystem("urn:https://www.cpr.dk/cpr-systemet/opbygning-af-cpr-nummeret/").setValue(cpr);
            firstName.addName().setFamily(lastName).addGiven(firstName);
            firstName.setGender(Enumerations.AdministrativeGender.gender);
            LocalDate dato = dateUtil.parse("");
            firstName.setBirthDate(java.sql.Date.valueOf(dato));
            Extension ext = new Extension();
            ext.setUrl("Is Registered?");
            ext.setValue(new BooleanType(true));
            firstName.addExtension(ext);
            LocalDate dato1 = dateUtil.parse("");
            Extension ext1 = new Extension();
            ext1.setUrl("Creation Date");
            ext1.setValue(new DateType(java.sql.Date.valueOf(dato1)));
            firstName.addExtension(ext1);
            Extension ext2 = new Extension();
            ext2.setUrl("Chosen App");
            ext2.setValue(new StringType("Astma App"));
            firstName.addExtension(ext2); */

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void getPractitionerData(int practitionerIDET) {
        Connection con = connect();

        Statement stmnt = null;
        String query = "SELECT firstName, lastName, practitionerID FROM Practitioner WHERE practitionerID=" + practitionerIDET;

        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int practitionerID = rs.getInt("practitionerID");
                System.out.println(firstName + ", " + lastName +
                        ", "+ practitionerID);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getAllergyIntolerance(int patientCPR) {
        Connection con = connect();

        Statement stmnt = null;
        String query = "SELECT cpr, name FROM Allergy WHERE cpr=" + patientCPR;

        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                int cpr = rs.getInt("cpr");
                System.out.println(name + ", "+ cpr);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void getCondition(int patientCPR) {
        Connection con = connect();

        Statement stmnt = null;
        String query = "SELECT cpr, name FROM Diagnosis WHERE cpr=" + patientCPR;

        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                int cpr = rs.getInt("cpr");
                System.out.println(name + ", "+ cpr);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}







