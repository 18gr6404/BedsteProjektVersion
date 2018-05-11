package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MedicationCtrl {

    // Reference to the main application.
    private MainApp mainAppRef;

    @FXML
    private Label typeLabel;

    @FXML
    private Label dosisLabel;

    @FXML
    private Label dateLabel;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MedicationCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void showMedication() {
        try {
            //Load showMedication
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MedicationCtrl.class.getResource("/ch/view/MedicationView.fxml"));
            VBox MedicationView = (VBox) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            //BorderPane tempRootLayout = mainAppRef.getRootLayout();
            //tempRootLayout.setCenter(MedicationView);

            VBox tempSidepaneLeft = mainAppRef.getSidepaneLeft();
            tempSidepaneLeft.getChildren().add(MedicationView);


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
