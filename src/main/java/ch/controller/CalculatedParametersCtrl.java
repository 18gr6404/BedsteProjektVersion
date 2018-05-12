package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.FhirControl;
import ch.db_And_FHIR.dbControl;
import org.hl7.fhir.dstu3.model.Observation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculatedParametersCtrl {

    MainApp mainApp;
    public void buildCalculatedParameters(Integer patientIdentifier, LocalDate startDate, LocalDate endDate){
        List<Observation> FhirObservations = new ArrayList<>();
        List<Observation> Fev1 = new ArrayList<>();
        // Henter instansen af FhirControl, dette er IKKE at lave et nyt objekt.
        FhirControl FhirClass = FhirControl.getInstance();

        FhirClass.startCtx();
        FhirObservations = FhirClass.getFHIRObservations(patientIdentifier.toString(), startDate, endDate);
        dbControl dbClass = dbControl.getInstance();
        Fev1 = dbClass.buildFEV(patientIdentifier);

    }
}
