package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.dbControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Quantity;
import org.hl7.fhir.dstu3.model.StringType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConsultationMeasurementCtrl {


    @FXML
    private TextField ConsultationMeasurementTextField;

    // Reference to the main application.
    private MainApp mainAppRef;
    private Stage consultationMeasurementStage;
    private boolean okClicked = false;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ConsultationMeasurementCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

    private void initialize() {

    }

    /**
     * Håndterer OK knappen
     */
    @FXML
    private void handleOK() {
        if (isInputValid()) {

            Integer cpr = 1207731450; //mainAppRef.getPatientCPR();
            Integer practitionerID = 56789;//mainAppRef.getPractitionerID();
            Observation fev1 = new Observation();

            fev1.setValue(new StringType(ConsultationMeasurementTextField.getText()));
          //  System.out.println(fev1.getValue());

            Date dateIn = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(dateIn.toInstant(), ZoneId.systemDefault());
            Date dateOut = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            fev1.setIssued(dateOut);

            dbControl dbControlOb = dbControl.getInstance();
            dbControlOb.insertfev1(fev1, cpr, practitionerID);


            okClicked = true;
            consultationMeasurementStage.close();
        }
    }

    /**
     * Håndterer Cancel knappen.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * Validates the user input in the text fields.
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        // || !StringUtils.isStrictlyNumeric(ConsultationMeasurementTextField.getText())
        if (ConsultationMeasurementTextField.getText() == null ||ConsultationMeasurementTextField.getText().length() == 0) {
            errorMessage += "Ugyldig indtastning\n"; }
        else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(ConsultationMeasurementTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Ugyldig indtastning.\n Indtastningen skal bestå af et tal.";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(consultationMeasurementStage);
            alert.setTitle("Ugyldig indtastning");
            alert.setHeaderText("Indtast konsultationsmåling for at bekræfte");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;}
    }


    private boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        this.mainAppRef = inputMain;
    }

    public void setConsultationMeasurementStage(Stage consultationMeasurementStage) {
        this.consultationMeasurementStage = consultationMeasurementStage;
    }

    public static void showConsultationMeasurementView(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ConsultationMeasurementCtrl.class.getResource("/ch/view/ConsultationMeasurementView.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage consultationMeasurementStage = new Stage();
            consultationMeasurementStage.setTitle("Indtast konsultationsmåling");
            consultationMeasurementStage.initModality(Modality.WINDOW_MODAL);
            //this.consultationMeasurementStage = consultationMeasurementStage;
            // consultationMeasurementStage.initOwner();
            Scene consultationmeasurementscene = new Scene(page);
            consultationMeasurementStage.setScene(consultationmeasurementscene);

            ConsultationMeasurementCtrl controller = loader.getController();
            controller.setConsultationMeasurementStage(consultationMeasurementStage);

            // Show the dialog and wait until the user closes it
            consultationMeasurementStage.showAndWait();

            //Skal vi ikke bruge da de bruger det i adressapp til at finde ud af om man har trykket OK i dialogboxen,
            // der hvor de kalder show-metoden.
            //return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

