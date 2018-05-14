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
    public MedicationCtrl() { }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }


    public VBox showMedication(VBox inputSidepane) {
        VBox thistempSidepaneLeft = new VBox();
        try {
            //Load showMedication
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MedicationCtrl.class.getResource("/ch/view/MedicationView.fxml"));
            VBox MedicationView = (VBox) loader.load();


            VBox tempSidepaneLeft = inputSidepane;
            tempSidepaneLeft.getChildren().add(MedicationView);
            thistempSidepaneLeft = tempSidepaneLeft;

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
