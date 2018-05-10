package ch.db_And_FHIR;

import java.io.IOException;
import java.text.DateFormat;
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

import static java.lang.Math.floor;


public class FhirControl {

    public static void main(String[] args) throws IOException {

        // We're connecting to a DSTU3 compliant server
        FhirContext ctx = FhirContext.forDstu3();

        // TestServer adresse http://hapi.fhir.org/baseDstu3
        String serverBase = "http://vonk.fire.ly/";

        // Oprettelse af klient til tilgang af serveren (Klient tilgår server)
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        // CPR Marianne: 120773-1450
        // CPR Jens: 130380-3813
        String patientIdentifier = "2206931445";
        String patientID = "3341463";

        // Opretter patient
        Patient patient = new Patient();
// ..populate the patient object..
        patient.addIdentifier().setSystem("urn:https://www.cpr.dk/cpr-systemet/opbygning-af-cpr-nummeret/").setValue(patientIdentifier);
        patient.addName().setFamily("Ramsing Lund").addGiven("Louise");
        //patient.setId(IdDt.newRandomUuid());
        
// Tilføj Extensions. NOTE! Hvis de ikke populeres, så vises de ikke på serveren, vi skal populere extensions i programmet, så det burde ikke betyde noget.        
Extension ext = new Extension();
//ext.setProperty("AppRegistered",);
    ext.setUrl("Is Registered?");
    ext.setValue(new BooleanType(true));
    patient.addExtension(ext);

        LocalDate dato1 = dateUtil.parse("25.02.2018");

Extension ext1 = new Extension();
    ext1.setUrl("Creation Date");
    ext1.setValue(new DateType(java.sql.Date.valueOf(dato1)));
    patient.addExtension(ext1);

Extension ext2 = new Extension();
    ext2.setUrl("Chosen App");
    ext2.setValue(new StringType("Astma App"));
    patient.addExtension(ext2);


// Skriv observation til serveren:
// Invoke the server create method (and send pretty-printed JSON
// encoding to the server
// instead of the default which is non-pretty printed XML)
        MethodOutcome outcome = client.create()
                .resource(patient)
                .prettyPrint()
                .encodedJson()
                .execute();




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
                .withId(results.getEntry().get(0).getResource().getId())
                .execute();

       // System.out.println("Found " + searchedPatient + " patients with CPR " + patientIdentifier);
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

/**
 *      Jeg kan ikke helt få fat i koderne igen, som andet end CodeableConcepts
 *      Vi kan stadig sortere efter koderne, hvis vi gør følgende: .getCode.setText
 *      Det er dog lidt slattent
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
//pObs.setSubject(new Reference(searchedPatient.getId()));

//Print value
System.out.println(pObs.getValue());

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


        System.out.println(results1.getTotal());
        List<Observation> observationArrayList = new ArrayList<Observation>();

        // Tilføjer observationer til en liste
        for (int i = 0; i<results1.getEntry().size(); i++){
        observationArrayList.add((Observation) results1.getEntry().get(i).getResource());
        }

        /**
         * Loader næster side af bundle og lægger det ind i en array liste
         *
         */
        for (int j = 0; j < floor(results1.getTotal()/20); j++){
            client.loadPage().next(results1);
        for (int i = 0; i<results1.getEntry().size(); i++){
            if(observationArrayList.size() < results1.getTotal()) {
                observationArrayList.add((Observation) results1.getEntry().get(i).getResource());
            }
        }
 }
        System.out.println(observationArrayList.size());
     for (int i = 0; i< observationArrayList.size(); i++){
            System.out.println(observationArrayList.get(i).getValue());
     }
/* Test kode, som ikke virkede, benyt bundle.
        Observation Observations = new Observation();
       // for(int i = 0; i>=results1.getEntry().size(); i++) {
            Observations = client
                    .read()
                    .resource(Observation.class)
                    .withId(results1.getEntry().get(0).getResource().getId())
                    .execute();
            observationArrayList.add(Observations);
       // }
*/

     }

    /**
     * Metoden bruges kun til at skubbe data op til serveren, og dette skal kun gøres én gang for hver server reset
     */
    private void putMarianne(IGenericClient client){
        // Skal kun kaldes såfremt der ikke er en FhirContext aktiv i det pågældende stykke kode

        //Mariannes detaljer
        Patient Marianne = new Patient();
        String CPR = "1207731450";
         Marianne.addIdentifier().setSystem("urn:https://www.cpr.dk/cpr-systemet/opbygning-af-cpr-nummeret/").setValue(CPR);
         Marianne.addName().setFamily("Jensen").addGiven("Marianne");
         Marianne.setGender(Enumerations.AdministrativeGender.FEMALE);
         LocalDate dato = dateUtil.parse("12.07.1973");
         Marianne.setBirthDate(java.sql.Date.valueOf(dato));

         Extension ext = new Extension();
         ext.setUrl("Is Registered?");
         ext.setValue(new BooleanType(true));
         Marianne.addExtension(ext);

         LocalDate dato1 = dateUtil.parse("04.04.2018");

         Extension ext1 = new Extension();
         ext1.setUrl("Creation Date");
         ext1.setValue(new DateType(java.sql.Date.valueOf(dato1)));
         Marianne.addExtension(ext1);

         Extension ext2 = new Extension();
         ext2.setUrl("Chosen App");
         ext2.setValue(new StringType("Astma App"));
         Marianne.addExtension(ext2);

         MethodOutcome outcome1 = client.create()
                 .resource(Marianne)
                 .prettyPrint()
                 .encodedJson()
                 .execute();
     }

    /**
     * Bruges til at generere observationer til 9 uger tilbage
     */
    private void generateObservations(String patientIdentifier, IGenericClient client){
        //Dag symptomer
        Type hvaesen = new StringType("Hvaesen");
        Type hosten = new StringType("Hosten");
        Type slimHoste = new StringType("Hoste m. slim");
        Type brystStrammen = new StringType("Strammen for brystet");
        Type aandeNoed = new StringType("Åndenød");

        //Nat Symptomer
        Type natHoste = new StringType("Hoste");
        Type opvaagning = new StringType("Opvågning");
        Type traethed = new StringType("Træthed");

        //Begrænsning i aktivitet
        Type aktivitetsBegraensning = new StringType("Aktivitetsbegrænsning");

        //Behov for anfaldsmedicin
        Type anfaldsMedicin = new StringType("Behov for anfaldsmedicin");

        //Trigger, skal typisk hænge sammen med "Behov for anfaldsMedicin
        Type aktivitet = new StringType("Aktivitet");
        Type Allergi = new StringType("Allergi");
        Type stoev = new StringType("Støv");
        Type ukendt = new StringType("Ukendt");

        Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(patientIdentifier))
                .returnBundle(Bundle.class)
                .execute();

        // Læs enkelt patient ind i et patient objekt (Kan kun gøres med ID, altså IKKE Identifier):
        Patient searchedPatient = client
                .read()
                .resource(Patient.class)
                .withId(results.getEntry().get(0).getResource().getId())
                .execute();



        Observation pObs = new Observation();
        pObs.addIdentifier().setValue(patientIdentifier);
        pObs.setStatus(Observation.ObservationStatus.FINAL);
        pObs.getCode().addCoding()
                .setSystem("Dag/Nat Symptom")
                .setCode("Nat Symptom")
                .setDisplay("Nat Symptom");

        pObs.setValue(hvaesen);
        pObs.setSubject(new Reference(searchedPatient.getId()));
     }
}
