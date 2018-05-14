package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.dbControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PractitionerCtrl extends HBox {

    // Reference to the main application. 
    private MainApp mainAppRef;

    @FXML
    private Label practitionerNameLabel;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PractitionerCtrl() {

}

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }


    public VBox showPractitioner(VBox inputSidepane){
        VBox thistempSidepaneLeft = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PractitionerCtrl.class.getResource("/ch/view/PractitionerView.fxml"));
            HBox practitionerView = (HBox) loader.load();


            VBox tempSidepaneLeft = inputSidepane;
            tempSidepaneLeft.getChildren().add(practitionerView);
            thistempSidepaneLeft = tempSidepaneLeft;
            //return tempSidepaneLeft;


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
