package ch.db_And_FHIR;


import ch.MainApp;
import ch.controller.CreateAsthmaAppUserCtrl;
import ch.utility.dateUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;


public class dbControl {


    //Opretter forbindelse til vores database

    //?autoReconnect=true
    static String dbAdress = "jdbc:mysql://db.course.hst.aau.dk:3306/hst_2018_18gr6404?&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String dbUsername = "hst_2018_18gr6404";         // UNI-NET DETALJER
    static String dbPassword = "eenuathaiheugohxahmo";
    private Connection con;
    /**
     * db Control er lavet som en "SingleTon" Hvilket betyder at der kun laves én instans af klassen
     * dvs. at hvis vi kører startCon i main på det nyligt oprettede objekt, så behøver vi ikke køre den igen
     * i calculated Parameters. (Det gør ikke noget at kalde den, men den gør ikke noget)
     */
    private static dbControl instance;
    public static dbControl getInstance(){
        if (instance ==null)
            instance = new dbControl();
        return instance;
    }


    public void startConnection() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("The class was not found");
            //return null;
        }

        try {
            con = DriverManager.getConnection(dbAdress, dbUsername, dbPassword);
            System.out.println("Connection established");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getMessage());
        }
    }

    public void getPatientData(int patientCPR) {


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
                /*System.out.println(firstName + ", " + lastName +
                        ", " + age + ", " + gender+
                        ", " + cpr + ", " + practitionerID);*/

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

    public void setAsthmaAppUser(Integer patientCPR, String choosenAppInput, Integer isRegisteredInput, Integer pastDataWantedInput) {

          try{


            String SQL = "UPDATE App SET choosenApp = '" + choosenAppInput + "'," +
                    " isRegistered = '" + isRegisteredInput + "'," +
                    " pastDataWanted = '" + pastDataWantedInput+ "'" +
                    "WHERE cpr = '" + patientCPR + "'";
            int rows = con.createStatement().executeUpdate(SQL, 1);
            if (rows > 0)
                System.out.println("Updated!");
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error on updating Data");
        }


    }

    public boolean requestIsRegistered(Integer patienCPR){


        int requestedIsRegistered= 0;

        Statement stmnt = null;
        try{
            String SQL = "SELECT isRegistered FROM App WHERE cpr = '" + patienCPR + "'";
            //ResultSet rs = con.createStatement().executeQuery(SQL);
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(SQL);

            while (rs.next()) {
                int isRegistered = rs.getInt("isRegistered");

                System.out.print("Fra DB får vi isRegistered =" + isRegistered);
                requestedIsRegistered = isRegistered;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error fetching isRegistered");
        }


       if(requestedIsRegistered == 0){
            return false;
        }
        else return true;

    }





    public void buildPractitionerData(Integer practitionerIDET) {

        Statement stmnt = null;
        String query = "SELECT firstName, lastName, practitionerID FROM Practitioner WHERE practitionerID=" + practitionerIDET;
     //   String practitionerID = practitionerIDET.toString();
        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                Practitioner practitioner = new Practitioner();
                practitioner.addIdentifier().setValue(String.valueOf(rs.getInt("practitionerID")));
                practitioner.addName().setFamily(rs.getString("lastName")).addGiven(rs.getString("firstName"));

            //    System.out.println(practitioner.getName().get(0).getFamily() + practitioner.getName().get(0).getGivenAsSingleString() + practitioner.getIdentifier().get(0).getValue() );

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildAllergyIntoleranceData(int patientCPR) {

        Statement stmntA = null;
        Statement stmntI = null;
        String queryA = "SELECT name FROM Allergy WHERE cpr=" + patientCPR;
        String queryI = "SELECT name FROM Intolerance WHERE cpr=" + patientCPR;
        try {
            stmntA = con.createStatement();
            stmntI = con.createStatement();
            ResultSet rsA = stmntA.executeQuery(queryA);
            ResultSet rsI = stmntI.executeQuery(queryI);
            while (rsA.next()) {

                AllergyIntolerance allergy = new AllergyIntolerance();
                allergy.setType(AllergyIntolerance.AllergyIntoleranceType.ALLERGY);
                allergy.getCode().addCoding()
                        .setCode(rsA.getString("name")) // Normalt er det en talkode
                        .setDisplay(rsA.getString("name")); // Her vises talkoden som den tekst man ønsker at vise i stedet

//                String name = rs.getString("name");
//                int cpr = rs.getInt("cpr");
                //System.out.println(name + ", "+ cpr);

              //  System.out.println(allergy.getCode().getCoding().get(0).getCode());
            }

            while (rsI.next()) {
                AllergyIntolerance intolerance = new AllergyIntolerance();
                intolerance.setType(AllergyIntolerance.AllergyIntoleranceType.INTOLERANCE);
                intolerance.getCode().addCoding()
                        .setCode(rsI.getString("name"))
                        .setDisplay(rsI.getString("name"));

               // System.out.println(intolerance.getCode().getCoding().get(0).getCode());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void buildConditionData(int patientCPR) {

        Statement stmnt = null;
        String query = "SELECT name FROM Diagnosis WHERE cpr=" + patientCPR;

        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                Condition condition = new Condition();
                condition.getCode().addCoding()
                        .setCode(rs.getString("name"))
                        .setDisplay(rs.getString("name"));

              //  System.out.println(condition.getCode().getCoding().get(0).getCode());

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildPatientData(int patientCPR) {


        Statement stmnt = null;
        String query = "SELECT cpr, firstName, lastName, age, gender FROM Patient WHERE cpr=" + patientCPR;

        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                Patient patient = new Patient();

//                LocalDate birthDate = LocalDate.parse("04.04.2018");
//                LocalDate today = LocalDate.now();
//                long age1 = ChronoUnit.YEARS.between(today, birthDate);

                //Ny extension
                Extension ext3 = new Extension();
                ext3.setUrl("age");
                ext3.setValue(new Quantity(rs.getInt("age")));
                patient.addExtension(ext3);

                patient.addIdentifier().setValue(String.valueOf(rs.getInt("cpr")));
                patient.addName().setFamily(rs.getString("lastName")).addGiven(rs.getString("firstName"));
               if (rs.getString("gender") == "K") {
                   patient.setGender(Enumerations.AdministrativeGender.FEMALE);
               }
               else {
                   patient.setGender(Enumerations.AdministrativeGender.MALE);
               }
               patient.addExtension(ext3);

               System.out.println((patient.getExtension().get(0).getValue()));





//                Condition condition = new Condition();
//                condition.getCode().addCoding()
//                        .setCode(rs.getString("name"))
//                        .setDisplay(rs.getString("name"));

//                practitioner.addIdentifier().setValue(String.valueOf(rs.getInt("practitionerID")));
//                practitioner.addName().setFamily(rs.getString("lastName")).addGiven(rs.getString("firstName"));
                //System.out.println(practitioner.getName().get(0).getFamily() + practitioner.getName().get(0).getGivenAsSingleString() + practitioner.getIdentifier().get(0).getValue() );

                System.out.println(patient.getGender());
                //System.out.println(patient.getIdentifier().get(0).getValue() + "," + patient.getName().get(0).getGivenAsSingleString() + " " + patient.getName().get(0).getFamily());

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildMedicineData(int patientCPR) {

        Statement stmnt = null;
        String query = "SELECT type, dosage, dateTime FROM Medication WHERE cpr=" + patientCPR;

        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                MedicationRequest medicationRequest = new MedicationRequest();
                medicationRequest.setMedication(new StringType(rs.getString("type")));
                medicationRequest.addDosageInstruction().setDose(new StringType(rs.getString("dosage")));
                medicationRequest.setAuthoredOn(rs.getDate("dateTime"));

               //System.out.println(medicationRequest.getMedication() +","+ medicationRequest.getDosageInstruction().get(0).getDose()+","+ medicationRequest.getAuthoredOn());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Observation> buildFEV(int patientCPR) {

        Statement stmnt = null;
        String query = "SELECT value, dateTime FROM Fev1 WHERE cpr=" + patientCPR;
List<Observation> fev1List = new ArrayList<>();
        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                Observation Fev1 = new Observation();
                Fev1.setValue((new Quantity(rs.getFloat("value"))));
                Fev1.setIssued(rs.getDate("dateTime"));

                fev1List.add(Fev1);
                try{
                    System.out.println(Fev1.getValueQuantity().getValue() + "," +  "," + Fev1.getIssued());
                }catch(FHIRException e){
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return fev1List;
    }

}







