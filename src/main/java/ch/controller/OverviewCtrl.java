package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.IOException;

public class OverviewCtrl {

    // Reference to the main application.
    private MainApp mainAppRef;

    @FXML
    private javafx.scene.control.Button weeklyOverviewBtn;

    @FXML
    private javafx.scene.control.Button consultationMeasurementBtn;

    @FXML
    private javafx.scene.control.Button summaryBtn;

    @FXML
    private javafx.scene.control.Button sinceLastConBtn;

    @FXML
    private javafx.scene.control.Button twoWeeksBtn;

    @FXML
    private javafx.scene.control.Button fourWeeksBtn;

    @FXML
    private javafx.scene.control.TextField fromTextfield;

    @FXML
    private javafx.scene.control.TextField toTextfield;

    @FXML
    private javafx.scene.control.Label mostFreqDaySymptomLabel;

    @FXML
    private javafx.scene.control.Label mostFreqNigthSymptomLabel;

    @FXML
    private Label triggerLabel;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public OverviewCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void showOverview(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(OverviewCtrl.class.getResource("/ch/view/OverviewView.fxml"));
            AnchorPane OverviewView = (AnchorPane) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            BorderPane tempRootLayout = mainAppRef.getRootLayout();
            tempRootLayout.setCenter(OverviewView);

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
