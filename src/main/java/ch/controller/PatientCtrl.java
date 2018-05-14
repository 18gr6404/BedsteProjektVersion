package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class PatientCtrl {
    // Reference to the main application.
    private MainApp mainAppRef;

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

    public VBox showPatient(VBox inputSidepane){
        VBox thistempSidepaneLeft = new VBox();
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PatientCtrl.class.getResource("/ch/view/PatientView.fxml"));
            AnchorPane patientView = (AnchorPane) loader.load();


            VBox tempSidepaneLeft = inputSidepane;
            tempSidepaneLeft.getChildren().add(patientView);
            thistempSidepaneLeft = tempSidepaneLeft;
            return tempSidepaneLeft;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return thistempSidepaneLeft;
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
