package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ConditionCtrl {

    @FXML
    private ListView komorbiditetList;

    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
    private MainApp mainAppRef;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ConditionCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

    private void initialize() {

    }

    public void showCondition(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ConditionCtrl.class.getResource("/ch/view/ConditionView.fxml"));
            AnchorPane conditionView = (AnchorPane) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            BorderPane tempRootLayout = mainAppRef.getRootLayout();
            tempRootLayout.setCenter(conditionView);


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
