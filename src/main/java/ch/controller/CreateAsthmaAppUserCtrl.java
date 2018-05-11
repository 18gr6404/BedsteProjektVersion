package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.dbControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class CreateAsthmaAppUserCtrl {

    @FXML
    private ChoiceBox chosenAppDropdown;
    @FXML
    private CheckBox pastDataWantedCheckbox;


    private Stage tempPrimaryStage;

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
            loader.setController(this);
            AnchorPane createAstmaAppUserView = (AnchorPane) loader.load();

            // Laver et midlertidigt instans af vores rootLayout for at vi kan sætte viewet heri.
            //BorderPane tempRootLayout = mainAppRef.getRootLayout();
            //tempRootLayout.setCenter(createAstmaAppUserView);

            Scene createAsthmaAppScene = new Scene(createAstmaAppUserView);
            tempPrimaryStage = mainAppRef.getPrimaryStage();
            tempPrimaryStage.setScene(createAsthmaAppScene);
            tempPrimaryStage.show();





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPastDataWantedCheckBox (){
        if (pastDataWantedCheckbox.isSelected()){
            return 1;
        }
        else return 0;
    }


    /**
     * Kaldes når brugeren klikker på "Bekræft" i CreateAsthmaAppUserView.
     */
    @FXML
    private void handleOk (){
        dbControl DBControlRef = new dbControl();

        String choosenAppInput = "AstmaApp";
        int isRegisteredInput = 1;
        int pastDataWantedInput = getPastDataWantedCheckBox();

        MainApp mainAppRef = new MainApp();
        Integer patientCPR = mainAppRef.getPatientCPR();

        //Integer patientCPR = 1207731450;
        System.out.print("CPR i handle metode" + patientCPR);

        DBControlRef.setAsthmaAppUser(patientCPR, choosenAppInput, isRegisteredInput, pastDataWantedInput);

        //tempPrimaryStage.close(); Giver en null pointer exception.
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
