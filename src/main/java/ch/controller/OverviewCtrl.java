package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    public void showOverview(OverviewCtrl overviewCtrl){
        FXMLLoader loader = new FXMLLoader();
        try {
            // Load person overview.
            loader.setLocation(OverviewCtrl.class.getResource("/ch/view/Overview.fxml"));
            loader.setController(overviewCtrl);
            AnchorPane OverviewView = (AnchorPane) loader.load();

            VBox tempCenterView = mainAppRef.getCenterView();
            tempCenterView.getChildren().add(OverviewView);
            tempCenterView.setFillWidth(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loaderr = new FXMLLoader();
            //Tabellen med hyppigst, der skal sættes i den højre sidebar.
            loaderr.setLocation(OverviewCtrl.class.getResource("/ch/view/MostFrequentTable.fxml"));
            loaderr.setController(overviewCtrl);
            VBox mostFrequentTable = (VBox) loaderr.load();

            VBox tempSidePaneRight = mainAppRef.getSidepaneRight();
            tempSidePaneRight.getChildren().add(mostFrequentTable);
            tempSidePaneRight.setFillWidth(true);
        }
        catch (IOException e){
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
