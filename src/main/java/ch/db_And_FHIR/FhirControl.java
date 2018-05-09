package ch.db_And_FHIR;

import java.io.IOException;
import java.text.DateFormat;
import java.time.ZoneId;
import java.util.*;

import java.time.LocalDate;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.base.composite.BaseResourceReferenceDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.valueset.BundleTypeEnum;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ch.utility.dateUtil;
import org.hl7.fhir.dstu3.model.*;

public class FhirControl {

    public static void main(String[] args) throws IOException {

        // We're connecting to a DSTU3 compliant server
        FhirContext ctx = FhirContext.forDstu3();

        // TestServer adresse
        String serverBase = "http://hapi.fhir.org/baseDstu3";

        // Oprettelse af klient til tilgang af serveren (Klient tilgår server)
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        String patientIdentifier = "2206931445";
        String patientID = "3341463";

        // Opretter patient
        Patient patient = new Patient();
/*// ..populate the patient object..
        patient.addIdentifier().setSystem("urn:https://www.cpr.dk/cpr-systemet/opbygning-af-cpr-nummeret/").setValue(patientIdentifier);
        patient.addName().setFamily("Ramsing Lund").addGiven("Louise");*/
        //patient.setId(IdDt.newRandomUuid());
        
// Tilføj Extensions. NOTE! Hvis de ikke populeres, så vises de ikke på serveren, vi skal populere extensions i programmet, så det burde ikke betyde noget.        
Extension ext = new Extension();
//ext.setProperty("AppRegistered",);
ext.setUrl("Is Registered?");
ext.setValue(new BooleanType());
patient.addExtension(ext);

Extension ext1 = new Extension();
ext1.setUrl("Creation Date");
ext1.setValue(new DateTimeType());
patient.addExtension(ext1);

Extension ext2 = new Extension();
ext2.setUrl("Chosen App");
ext2.setValue(new StringType(""));
patient.addExtension(ext2);

/*
// Skriv observation til serveren:
// Invoke the server create method (and send pretty-printed JSON
// encoding to the server
// instead of the default which is non-pretty printed XML)
        MethodOutcome outcome = client.create()
                .resource(patient)
                .prettyPrint()
                .encodedJson()
                .execute();
*/



        // Søger på patienter med Identifier. Herfra kan vi få ID'et.
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(patientIdentifier))////value(testString))
                .returnBundle(Bundle.class)
                .execute();



        // Læs enkelt patient ind i et patient objekt (Kan kun gøres med ID, altså IKKE Identifier):
        Patient searchedPatient = client
                .read()
                .resource(Patient.class)
                .withId(patientID)
                .execute();

        System.out.println("Found " + searchedPatient + " patients with CPR " + patientIdentifier);
        System.out.println(searchedPatient.getId());


/**
* Opretter observation og tilføjer identifier + status + kode
**/
Observation pObs = new Observation();
pObs.addIdentifier().setValue(patientIdentifier);
pObs.setStatus(Observation.ObservationStatus.FINAL);
/*pObs.getCode().addCoding()
  .setSystem("Dag/Nat Symptom")
  .setCode("DagSymptom")
  .setDisplay("Dagsymptom");
*/
pObs.getCode().addCoding()
.setSystem("Dag/Nat Symptom")
.setCode("Nat Symptom")
.setDisplay("Nat Symptom");
        Type hvaesen = new StringType("Hvaesen");
        pObs.setValue(hvaesen);


String dateString = "25.02.2018";
//Datoen vendes til yyyy.mm.dd, kan fikses hvis det er nødvendigt, men det vil tage noget tid
LocalDate observationLocalDate = dateUtil.parse(dateString);

//Ikke helt god praksis at bruge SQL pakken her, men det virker.
Date observationDate2 = java.sql.Date.valueOf(observationLocalDate);
pObs.setIssued(observationDate2);

// Sætter reference til patient i observationen
pObs.setSubject(new Reference(searchedPatient.getId()));

        MethodOutcome outcome1 = client.create()
                .resource(pObs)
                .prettyPrint()
                .encodedJson()
                .execute();

        // Perform a search
        Bundle results1 = client
                .search()
                .forResource(Observation.class)
                .where(Observation.SUBJECT.hasId(searchedPatient.getId()))
                .returnBundle(Bundle.class)
                .execute();
        System.out.println("Found " + results1.getEntry().size() + " observations with patient ID " + patientID);


        System.out.println(results1.getEntry().get(1).getResource().getId());

//results1.getEntry().get(0).getResource();
        List<Observation> observationArrayList = new ArrayList<Observation>();

        Observation Observations = new Observation();
       // for(int i = 0; i>=results1.getEntry().size(); i++) {
            Observations = client
                    .read()
                    .resource(Observation.class)
                    .withId(results1.getEntry().get(0).getResource().getId())
                    .execute();
            observationArrayList.add(Observations);
       // }

        System.out.println(results1.getEntry().get(0).getResource());
   //     for (int i = 0; i<observationArrayList.size(); i++){
     //       observationArrayList.add(results1.getEntry().get(i).getResource())
       // }




        System.out.println(Observations.getCode().getText());
 //      System.out.println(observationArrayList.get(0).getCode().getText());

        //System.out.println(Observations);
        //List<Observation> observationList = new ArrayList<>();

        for (Observation item: observationArrayList
             ) {
            System.out.println(item.getCode().getText());
        }
    }
}
