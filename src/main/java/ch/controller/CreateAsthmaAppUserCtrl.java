package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class CreateAsthmaAppUserCtrl {

    // Reference to the main application.
    private MainApp mainAppRef;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CreateAsthmaAppUserCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void showCreateAsthmaAppUser(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreateAsthmaAppUserCtrl.class.getResource("/ch/view/CreateAsthmaAppUserView.fxml"));
            AnchorPane createAstmaAppUserView = (AnchorPane) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            BorderPane tempRootLayout = mainAppRef.getRootLayout();
            tempRootLayout.setCenter(createAstmaAppUserView);


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
