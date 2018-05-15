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
import sun.applet.Main;

import java.io.IOException;

public class ConsultationMeasurementCtrl {


    @FXML
    private TextField ConsultationMeasurementTextField;

    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
    private MainApp mainAppRef;
    private Stage consultationMeasurementStage;
    private Integer patientCpr;
    private Integer practitionerId;


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

            String fev1String = ConsultationMeasurementTextField.getText();
            Integer fev1Int = Integer.parseInt(fev1String);
            dbControl dbControlOb = dbControl.getInstance();
            //OBS skal indkommenteres når vi vil sætte til DB.
            //dbControlOb.insertfev1(fev1Int, patientCpr, practitionerId);

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
            errorMessage += "Invalid input!!\n"; }
        else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(ConsultationMeasurementTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid input (must be an integer)!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(consultationMeasurementStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;}
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

    public static void showConsultationMeasurementView(Integer patientCpr, Integer practitionerId){
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
            controller.setPatientCpr(patientCpr);
            controller.setPractitionerId(practitionerId);

            // Show the dialog and wait until the user closes it
            consultationMeasurementStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPatientCpr(Integer patientCpr) {
        this.patientCpr = patientCpr;
    }

    public void setPractitionerId(Integer practitionerId) {
        this.practitionerId = practitionerId;
    }
}

