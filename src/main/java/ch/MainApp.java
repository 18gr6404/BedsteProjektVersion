package ch;

import ch.controller.*;
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
    //private Integer patientCPR = 1207731450; //Marianne.
    private Integer patientCPR = 1303803813;  //Jens

    /**
     * Constructor
     */
    public MainApp() {

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sundhedsappmodul");

        myDBClass.getPractitionerData(56789);
        myDBClass.getAllergyIntolerance(patientCPR);
        myDBClass.getCondition(patientCPR);
        myDBClass.getPatientData(patientCPR);


        initRootLayout(); //initiate root layout

        boolean isRegistered = myDBClass.requestIsRegistered(patientCPR);

        if(isRegistered) {




        }
        else {
            CreateAsthmaAppUserCtrl createAsthmaAppUserCtrl = new CreateAsthmaAppUserCtrl();
            createAsthmaAppUserCtrl.setMainApp(this);
            createAsthmaAppUserCtrl.showCreateAsthmaAppUser();
        }


        // buildPatient();  //Her skal vi kalde vores funktioner til at bygge vores modeller





        // PatientCtrl patientCtrl = new PatientCtrl();
        // patientCtrl.setMainApp(this);
        // patientCtrl.showPatient();

        //ConditionCtrl conditionCtrl = new ConditionCtrl();
        //conditionCtrl.setMainApp(this);
        //conditionCtrl.showCondition();

        //AllergyIntoleranceCtrl allergyIntoleranceCtrl = new AllergyIntoleranceCtrl();
        //allergyIntoleranceCtrl.setMainApp(this);
        //allergyIntoleranceCtrl.showAllergyIntolerance();


        /*
        ConsultationMeasurementCtrl consultationMeasurementCtrl = new ConsultationMeasurementCtrl();
        consultationMeasurementCtrl.setMainApp(this);
        consultationMeasurementCtrl.showConsultationMeasurement();
         */


        //showPerson(); //Her skal vi kalde vores funktioner til at vise

        //}
        //else{
        //showCreateAsthmaAppUser();
        //}

       // PractitionerCtrl practitionerCtrl = new PractitionerCtrl();
        //practitionerCtrl.setMainApp(this);
        //practitionerCtrl.showPractitioner();

        //MedicationCtrl medicationCtrl = new MedicationCtrl();
        //medicationCtrl.setMainApp(this);
        //medicationCtrl.showMedication();

//        OverviewCtrl overviewCtrl = new OverviewCtrl();
//        overviewCtrl.setMainApp(this);
//        overviewCtrl.showOverview();
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setFullScreen(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returnerer vores rootLayout så vi kan hente det fra de andre controlleres og de kan sætte deres respektive view heri. Se showCreateAsthmaAppUser() i CreateAsthmaAppUserCtrl for et eksempel.
     * @return
     */
    public BorderPane getRootLayout(){
    return rootLayout;
}
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Integer getPatientCPR() {
        return patientCPR;
    }



}




