package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OverviewCtrl {

    // Reference to the main application.
    private MainApp mainAppRef;
    // Reference til Rootlayout
    private RootLayoutCtrl rootLayoutCtrlRef;



    @FXML
    private Button weeklyOverviewBtn;
    @FXML
    private Button consultationMeasurementBtn;
    @FXML
    private Button summaryBtn;
    @FXML
    private Button sinceLastConBtn;
    @FXML
    private Button twoWeeksBtn;
    @FXML
    private Button fourWeeksBtn;

    @FXML
    private TextField fromTextfield;
    @FXML
    private TextField toTextfield;

    @FXML
    private Label mostFreqDaySymptomLabel;
    @FXML
    private Label mostFreqNigthSymptomLabel;
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
    private void initialize() { }

    @FXML
    private void handleConsultationMeasurement(){
        ConsultationMeasurementCtrl.showConsultationMeasurementView();
    }

    @FXML
    private void handleWeeklyOverview(){
        rootLayoutCtrlRef.showWeeklyOverview();
    }

    @FXML
    private void handleSummary(){}





    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        this.mainAppRef = inputMain;
    }

    /**
     * Is called to give a reference back to itself.
     *
     * @param inputRootLayoutCtrl
     */
    public void setRootLayoutCtrlRef(RootLayoutCtrl inputRootLayoutCtrl) {
        this.rootLayoutCtrlRef = inputRootLayoutCtrl;
    }

}
