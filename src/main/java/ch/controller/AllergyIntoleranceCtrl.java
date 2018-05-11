package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AllergyIntoleranceCtrl{

    @FXML
    private ListView AllergyList;
    @FXML
    private ListView IntoleranceList;

    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
    private MainApp mainAppRef;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public AllergyIntoleranceCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

    private void initialize() {

    }

    public void showAllergyIntolerance(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AllergyIntoleranceCtrl.class.getResource("/ch/view/AllergyIntolerance.fxml"));
            VBox allergyIntoleranceView = (VBox) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            //BorderPane tempRootLayout = mainAppRef.getRootLayout();
            //tempRootLayout.setCenter(allergyIntoleranceView);

            VBox tempSidepaneRight = mainAppRef.getSidepaneRight();
            tempSidepaneRight.getChildren().add(allergyIntoleranceView);


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
