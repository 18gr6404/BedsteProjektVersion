package ch.controller;

import ch.MainApp;
import ch.controller.RootLayoutCtrl;
import ch.db_And_FHIR.dbControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;


public class CreateAsthmaAppUserCtrl {

    @FXML
    private ChoiceBox chosenAppDropdown;
    @FXML
    private CheckBox pastDataWantedCheckbox;

    private VBox centerView;

    // Reference to the main application.
    private MainApp mainAppRef;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CreateAsthmaAppUserCtrl() {
    }

    /*
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }


    public void showCreateAsthmaAppUser(VBox centerView){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreateAsthmaAppUserCtrl.class.getResource("/ch/view/CreateAsthmaAppUserView.fxml"));
            AnchorPane createAstmaAppUserView = (AnchorPane) loader.load();

            centerView.getChildren().add(createAstmaAppUserView);
            this.centerView = centerView;

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
    private void handleOk (ActionEvent event) throws IOException {
        dbControl DBControlRef = new dbControl();

        String choosenAppInput = "AstmaApp";
        int isRegisteredInput = 1;
        int pastDataWantedInput = getPastDataWantedCheckBox();


        MainApp mainAppRef = new MainApp();
        Integer patientCPR = mainAppRef.getPatientCPR();

        //skal indkommenteres hvis vi vil sætte isRegistered i DB. Det er dog træls når man tester
        //DBControlRef.setAsthmaAppUser(patientCPR, choosenAppInput, isRegisteredInput, pastDataWantedInput);

        //Henter den stage som actionevent'et (altså knap-trykket) kommer fra.
        /*
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        RootLayoutCtrl rootLayoutCtrlRef = new RootLayoutCtrl();
        rootLayoutCtrlRef.initRootLayout(window);
        rootLayoutCtrlRef.initBasicLayout(); */

    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        //this.mainAppRef = inputMain;
    }

}
