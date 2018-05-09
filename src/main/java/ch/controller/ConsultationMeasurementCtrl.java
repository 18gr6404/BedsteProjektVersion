package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConsultationMeasurementCtrl {

    @FXML
    private Label FEV1Label;
    @FXML
    private TextField ConsultationMeasurementsTextField;
    @FXML
    private Button OKButton;
    @FXML
    private Button CancelButton;

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

    public boolean showConsultationMeasurement() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ConsultationMeasurementCtrl.class.getResource("/ch/view/ConsultationMeasurementView.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage consultationMeasurementStage = new Stage();
            consultationMeasurementStage.setTitle("Indtast konsultationsmålinger");
            consultationMeasurementStage.initModality(Modality.WINDOW_MODAL);
            // consultationMeasurementStage.initOwner();
            Scene consultationmeasurementscene = new Scene(page);
            consultationMeasurementStage.setScene(consultationmeasurementscene);

            // Set the person into the controller.
            //PersonEditDialogController controller = loader.getController();
            //controller.setDialogStage(dialogStage);
            //controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            consultationMeasurementStage.showAndWait();
            return isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        this.mainAppRef = inputMain;
    }



    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            //person.setFirstName(firstNameField.getText());

            okClicked = true;
            consultationMeasurementStage.close();
        }
    }

@FXML
private void handleCancel() {
    consultationMeasurementStage.close();
}

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (FEV1Label.getText() == null || FEV1Label.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            return false;}
    }

}

