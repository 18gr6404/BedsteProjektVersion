package db_And_FHIR;

import java.io.IOException;

import ca.uhn.fhir.context.FhirContext;
//import ca.uhn.fhir.model.base.composite.BaseResourceReferenceDt;
//import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.*;
//import org.hl7.fhir.instance.model.api.IIdType;

public class FhirControl {

    public static void main(String[] args) throws IOException {

        // We're connecting to a DSTU3 compliant server in this example
        FhirContext ctx = FhirContext.forDstu3();

        String serverBase = "http://hapi.fhir.org/baseDstu3";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        String patientIdentifier = "2206931445";
        String patientID = "3341463";
        // Opretter patient
        Patient patient = new Patient();/*
// ..populate the patient object..
        patient.addIdentifier().setSystem("urn:https://www.cpr.dk/cpr-systemet/opbygning-af-cpr-nummeret/").setValue(patientID);
        patient.addName().setFamily("Ramsing Lund").addGiven("Daniel");
        patient.setId(IdDt.newRandomUuid());
*/
        CodeableConcept Dag = new CodeableConcept();
        Dag.setText("Dag");
        Observation pObs = new Observation();
        pObs.addIdentifier().setValue(patientIdentifier);
        pObs.setStatus(Observation.ObservationStatus.FINAL).setCode(Dag);

// Skriv patient til serveren:
// Invoke the server create method (and send pretty-printed JSON
// encoding to the server
// instead of the default which is non-pretty printed XML)
/*        MethodOutcome outcome = client.create()
                .resource(pObs)
                .prettyPrint()
                .encodedJson()
                .execute();
*/
// The MethodOutcome object will contain information about the
// response from the server, including the ID (NOT! Identifier) of the created
// resource, the OperationOutcome response, etc. (assuming that
// any of these things were provided by the server! They may not
// always be)
        // IIdType = ID
        // idDt = Identifier
       //IIdType id = outcome.getId();
       //System.out.println("Got ID: " + id.getValue());
        

        String testString = "Ramsing Lund";
        // Perform a search for bundle:
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(patientIdentifier))////value(testString))
                .returnBundle(Bundle.class)
                .execute();


        //Perform search for single patient:

        Patient searchedPatient = client.read()
                .resource(Patient.class)
                .withId(patientID)
                .execute();
        System.out.println("Found " + searchedPatient.getName() + " patients with CPR " + patientIdentifier);
        System.out.println(searchedPatient);

       // ResourceRefere
        Reference random = new Reference();
        random.setReference(searchedPatient.getId());
        pObs.setSubject(random);
        System.out.println(random);
        MethodOutcome outcome = client.create()
                .resource(pObs)
                .prettyPrint()
                .encodedJson()
                .execute();

        // Perform a search
        Bundle results1 = client
                .search()
                .forResource(Observation.class)
                .where(Observation.SUBJECT.hasId(patientID))
                .returnBundle(Bundle.class)
                .execute();
        System.out.println("Found " + results1.getEntry().size() + " observations with patient ID " + patientID);

    }
}
