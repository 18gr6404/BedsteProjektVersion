package ch;

import ch.controller.AllergyIntoleranceCtrl;
import ch.controller.ConditionCtrl;
import ch.controller.CreateAsthmaAppUserCtrl;
import ch.controller.PractitionerCtrl;
import ch.db_And_FHIR.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private java.sql.Connection con;
    dbControl myDBClass = new dbControl();

    /**
     * Constructor
     */
    public MainApp() {
        //
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AstmaAppSystem");


        con = myDBClass.connect();

        //isRegistered = DBCtrl.requestIsRegistered();   - Her skal vi indhente isRegistered fra DB og have en if-sætning.

        //if(isRegistered){
        initRootLayout(); //initiate root layout


        // buildPatient();  //Her skal vi kalde vores funktioner til at bygge vores modeller
     //   CreateAsthmaAppUserCtrl createAsthmaAppUserCtrl = new CreateAsthmaAppUserCtrl();
     //   createAsthmaAppUserCtrl.setMainApp(this);
     //   createAsthmaAppUserCtrl.showCreateAsthmaAppUser();
        //CreateAsthmaAppUserCtrl createAsthmaAppUserCtrl = new CreateAsthmaAppUserCtrl();
        //createAsthmaAppUserCtrl.setMainApp(this);
        //createAsthmaAppUserCtrl.showCreateAsthmaAppUser();

        // PatientCtrl patientCtrl = new PatientCtrl();
        // patientCtrl.setMainApp(this);
        // patientCtrl.showPatient();

        //ConditionCtrl conditionCtrl = new ConditionCtrl();
        //conditionCtrl.setMainApp(this);
        //conditionCtrl.showCondition();

        //AllergyIntoleranceCtrl allergyIntoleranceCtrl = new AllergyIntoleranceCtrl();
        //allergyIntoleranceCtrl.setMainApp(this);
        //allergyIntoleranceCtrl.showAllergyIntolerance();

        //showPerson(); //Her skal vi kalde vores funktioner til at vise

        //}
        //else{
        //showCreateAsthmaAppUser();
        //}

        PractitionerCtrl practitionerCtrl = new PractitionerCtrl();
        practitionerCtrl.setMainApp(this);
        practitionerCtrl.showPractitioner();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            //primaryStage.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/*
    public void showCreateAsthmaAppUser(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreateAsthmaAppUserCtrl.class.getResource("/ch/view/CreateAsthmaAppUserView.fxml"));
            AnchorPane createAstmaAppUserView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.

            rootLayout.setCenter(createAstmaAppUserView);

            // Give the controller access to the main app.
            CreateAsthmaAppUserCtrl controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/

    /**
     * Returnerer vores rootLayout så vi kan hente det fra de andre controlleres og de kan sætte deres respektive view heri. Se showCreateAsthmaAppUser() i CreateAsthmaAppUserCtrl for et eksempel.
     * @return
     */
    public BorderPane getRootLayout(){
    return rootLayout;
}

}
