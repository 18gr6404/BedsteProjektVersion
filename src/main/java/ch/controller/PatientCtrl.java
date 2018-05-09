package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class PatientCtrl {
    @FXML
    private Label nameLabel;
    @FXML
    private Label cprLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label genderLabel;


    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
    private MainApp mainAppRef;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PatientCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void showPatient(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreateAsthmaAppUserCtrl.class.getResource("/ch/view/patientView.fxml"));
            AnchorPane patientView = (AnchorPane) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            BorderPane tempRootLayout = mainAppRef.getRootLayout();
            tempRootLayout.setCenter(patientView);


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
