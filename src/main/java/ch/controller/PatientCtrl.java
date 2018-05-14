package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.dbControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.hl7.fhir.dstu3.model.Patient;

import java.io.IOException;


public class PatientCtrl {
    // Reference to the main application.
    private MainApp mainAppRef;
    private Patient patientObject;

    @FXML
    private Label nameLabel;
    @FXML
    private Label cprLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label genderLabel;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PatientCtrl() {}


    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void setPatient(){

        dbControl dbControlOb = dbControl.getInstance();

        patientObject = dbControlOb.buildPatientData(mainAppRef.getPatientCPR());

        nameLabel.setText(patientObject.getName().get(0).getGivenAsSingleString() +" "+ patientObject.getName().get(0).getFamily());
        cprLabel.setText(patientObject.getIdentifier().get(0).getValue());
       // ageLabel.setText(patientObject; //Vender tilbage hertil
        genderLabel.setText(String.valueOf(patientObject.getGender()));

    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        this.mainAppRef = inputMain;
    }

}
