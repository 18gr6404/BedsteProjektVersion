package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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

    public void showConsultationMeasurement(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ConsultationMeasurementCtrl.class.getResource("/ch/view/ConsultationMeasurementView.fxml"));
            AnchorPane consultationMeasurementView = loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            BorderPane tempRootLayout = mainAppRef.getRootLayout();
            tempRootLayout.setCenter(consultationMeasurementView);


        } catch (IOException e) {
            e.printStackTrace();
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


}
