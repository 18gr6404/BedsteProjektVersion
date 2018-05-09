package ch.db_And_FHIR;

import java.io.IOException;
import java.util.*;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.base.composite.BaseResourceReferenceDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.valueset.BundleTypeEnum;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import javafx.collections.FXCollections;
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
        Patient patient = new Patient();/*
// ..populate the patient object..
        patient.addIdentifier().setSystem("urn:https://www.cpr.dk/cpr-systemet/opbygning-af-cpr-nummeret/").setValue(patientIdentifier);
        patient.addName().setFamily("Ramsing Lund").addGiven("Daniel");
        patient.setId(IdDt.newRandomUuid());*/


// Skriv observation til serveren:
// Invoke the server create method (and send pretty-printed JSON
// encoding to the server
// instead of the default which is non-pretty printed XML)
/*        MethodOutcome outcome = client.create()
                .resource(pObs)
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
        Type hvaesen = new StringType("hvaesen");
        pObs.setValue(hvaesen);

// Sætter reference til patient i observationen
pObs.setSubject(new Reference(searchedPatient.getId()));

        MethodOutcome outcome = client.create()
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

        System.out.println(results1.getEntry().get(1).getResource());


       Observation Observations = client
                .read()
                .resource(Observation.class)
                .withId(results1.getEntry().get(0).getResource().getId())
                .execute();
        System.out.println(Observations);
        System.out.println(Observations.getCode().getText());
        //System.out.println(Observations);
        //List<Observation> observationList = new ArrayList<>();


// The observation refers to the patient using the ID, which is already
// set to a temporary UUID
      //  observation.setSubject(new BaseResourceReferenceDt(patient.getId()));

// Create a bundle that will be used as a transaction
       // Bundle bundle = new Bundle();
      //  bundle.setType(Bundle.BundleType.TRANSACTION);

// Add the patient as an entry. This entry is a POST with an
// If-None-Exist header (conditional create) meaning that it
// will only be created if there isn't already a Patient with
// the identifier 12345
     /*   bundle.addEntry()
                .setFullUrl(patient.getId())
                .setResource(patient)
                .getRequest()
                .setUrl("Patient")
                .setIfNoneExist("identifier=http://acme.org/mrns|12345")
                .setMethod(HTTPVerbEnum.POST);

// Add the observation. This entry is a POST with no header
// (normal create) meaning that it will be created even if
// a similar resource already exists.
        bundle.addEntry()
                .setResource(observation)
                .getRequest()
                .setUrl("Observation")
                .setMethod(HTTPVerbEnum.POST);

// Log the request
        System.out.println(ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle));

// Create a client and post the transaction to the server
        IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");
        Bundle resp = client.transaction().withBundle(bundle).execute();

// Log the response
        System.out.println(ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(resp));*/
    }
}
