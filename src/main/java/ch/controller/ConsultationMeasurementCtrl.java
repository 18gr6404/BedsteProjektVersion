package ch.controller;

import ch.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConsultationMeasurementCtrl {


    @FXML
    private TextField ConsultationMeasurementTextField;

    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
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

//          Hvad skal vi gøre med FEV1 målingen??

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

        if (ConsultationMeasurementTextField.getText() == null || ConsultationMeasurementTextField.getText().length() == 0) {
            errorMessage += "FEV1-måling må ikke være nul\n"; }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(consultationMeasurementStage);
            alert.setTitle("Invalid indtastning");
            alert.setHeaderText("Indtast venligst en valid FEV1-måling");
            alert.setContentText(errorMessage);
            alert.showAndWait();
                return false;
            }
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

