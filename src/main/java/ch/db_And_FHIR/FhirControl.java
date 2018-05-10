package ch.db_And_FHIR;

import java.io.IOException;
import java.text.DateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.base.composite.BaseResourceReferenceDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.valueset.BundleTypeEnum;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ch.utility.dateUtil;
import com.google.common.collect.Lists;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;

import static java.lang.Math.floor;


public class FhirControl {

    public static void main(String[] args) throws IOException, FHIRException {

        // We're connecting to a DSTU3 compliant server
        FhirContext ctx = FhirContext.forDstu3();

        // TestServer adresse http://vonk.fire.ly/
        String serverBase = "http://hapi.fhir.org/baseDstu3";

        // Oprettelse af klient til tilgang af serveren (Klient tilgår server)
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        // CPR Marianne: 120773-1450
        // CPR Jens: 130380-3813
        String patientIdentifier = "1207731444";
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


// Skriv til serveren:
// Invoke the server create method (and send pretty-printed JSON
// encoding to the server
// instead of the default which is non-pretty printed XML)
/*        MethodOutcome outcome = client.create()
                .resource(patient)
                .prettyPrint()
                .encodedJson()
                .execute();*/




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
        System.out.println(searchedPatient.getName().get(0).getGiven());
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

/*pObs.getCode().addCoding()
.setSystem("Dag/Nat Symptom")
.setCode("Nat Symptom")
.setDisplay("Nat Symptom");
        Type hvaesen = new StringType("Hvaesen");
        pObs.setValue(hvaesen);
*/
        pObs.getCode().addCoding()
                .setSystem("Morgen/Aften måling")
                .setCode("Morgen måling")
                .setDisplay("Morgen måling");
        System.out.println(pObs.getCode());
        String dateString = "25.02.2018";
//Datoen vendes til yyyy.mm.dd, kan fikses hvis det er nødvendigt, men det vil tage noget tid
        LocalDate observationLocalDate = dateUtil.parse(dateString);

//Ikke helt god praksis at bruge SQL pakken her, men det virker.
        Date observationDate2 = java.sql.Date.valueOf(observationLocalDate);
        pObs.setIssued(observationDate2);

// Sætter reference til patient i observationen
        pObs.setSubject(new Reference(searchedPatient.getId()));

//Print value
        System.out.println(pObs.getValue());

        // Perform a search
        Bundle results1 = client
                .search()
                .forResource(Observation.class)
                .where(Observation.CODE.hasSystemWithAnyCode("Morgen/Aften måling"))
                .returnBundle(Bundle.class)
                .execute();
        System.out.println("Found " + results1.getEntry().size() + " observations with patient ID " + searchedPatient.getId());


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
        System.out.println(observationArrayList.get(0).getValueQuantity().getValue());
        System.out.println(observationArrayList.size());

        //Printer alle værdier for listen observationArrayList
        //observationArrayList.stream().map(Observation::getValue).forEach(System.out::println);

//putMarianne(client);
        String startString = "15.03.2018";
        String slutString = "10.05.2018";

        LocalDate startDate = dateUtil.parse(startString);
        LocalDate slutDate = dateUtil.parse(slutString);
//Bundle observationBundle = generateDagSymptom(searchedPatient, startDate, slutDate);
        //List<Observation> observationBundle = generateDagSymptom(searchedPatient, startDate, slutDate, 40);

        //observationBundle = client.create()
//client.transaction().withResources(observationBundle).execute();

    }




}
