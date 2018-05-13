package ch;

import ch.controller.*;
import ch.db_And_FHIR.*;

import ch.utility.dateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private VBox sidePaneLeft;
    private VBox sidePaneRight;
    private VBox centerView;


    private Integer patientCPR = 1207731470; //Marianne.
    //private Integer patientCPR = 1303803823;  //Jens

    /**
     * Constructor
     */
    public MainApp() {

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sundhedsappmodul");
        LocalDate startDate = dateUtil.parse("10.03.2018");
        LocalDate endDate = dateUtil.parse("10.05.2018");
       FhirControl FhirClass = FhirControl.getInstance();
       FhirClass.startCtx();
        dbControl myDBClass = dbControl.getInstance();
        myDBClass.startConnection();
       /* myDBClass.startConnection();
        myDBClass.buildPractitionerData(56789);
        myDBClass.buildAllergyIntoleranceData(patientCPR);
        myDBClass.buildConditionData(patientCPR);
        myDBClass.buildMedicineData(patientCPR);
        myDBClass.getPatientData(patientCPR);*/
        myDBClass.buildFEV(patientCPR-20);
        //initRootLayout(); //initiate root layout
        //FhirClass.startCtx();



       // List<Observation> observationList = FhirClass.getFHIRObservations(patientCPR.toString(), startDate, endDate);
       // System.out.println(observationList.get(0).getCode().getCoding().get(0).getCode());

        CalculatedParametersCtrl calcParam = new CalculatedParametersCtrl();
        calcParam.buildCalculatedParameters(patientCPR, startDate, endDate);
       /* boolean isRegistered = myDBClass.requestIsRegistered(patientCPR);

        if(isRegistered) {
            //Sætter Practitioner i vores basis-view v. at lave en ny instans af controlleren, lave en referece til MainApp og kalde show-metoden
            PractitionerCtrl practitionerCtrl = new PractitionerCtrl();
            practitionerCtrl.setMainApp(this);
            practitionerCtrl.showPractitioner();

            //Sætter Patient i vores basis-view v. at lave en ny instans af controlleren, lave en referece til MainApp og kalde show-metoden
            PatientCtrl patientCtrl = new PatientCtrl();
            patientCtrl.setMainApp(this);
            patientCtrl.showPatient();

            //Sætter Medicine i vores basis-view v. at lave en ny instans af controlleren, lave en referece til MainApp og kalde show-metoden
            MedicationCtrl medicationCtrl = new MedicationCtrl();
            medicationCtrl.setMainApp(this);
            medicationCtrl.showMedication();

            ConditionCtrl conditionCtrl = new ConditionCtrl();
            conditionCtrl.setMainApp(this);
            conditionCtrl.showConditionView();

            AllergyIntoleranceCtrl allergyIntoleranceCtrl = new AllergyIntoleranceCtrl();
            allergyIntoleranceCtrl.setMainApp(this);
            allergyIntoleranceCtrl.showAllergyIntolerance();

            OverviewCtrl overviewCtrl = new OverviewCtrl();
            overviewCtrl.setMainApp(this);
            overviewCtrl.showOverview(overviewCtrl);

        }
        else {
            CreateAsthmaAppUserCtrl createAsthmaAppUserCtrl = new CreateAsthmaAppUserCtrl();
            createAsthmaAppUserCtrl.setMainApp(this);
            createAsthmaAppUserCtrl.showCreateAsthmaAppUser();
        }*/


        // buildPatient();  //Her skal vi kalde vores funktioner til at bygge vores modeller

        /*
        ConsultationMeasurementCtrl consultationMeasurementCtrl = new ConsultationMeasurementCtrl();
        consultationMeasurementCtrl.setMainApp(this);
        consultationMeasurementCtrl.showConsultationMeasurement();
         */
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();

        try {
            // Load root layout from fxml file.
            loader.setLocation(MainApp.class.getResource("/ch/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            VBox mySidepaneLeft = new VBox();
            mySidepaneLeft.setSpacing(20); //laver mellemrum mellem objekterne i VBox'en.
            mySidepaneLeft.setPadding(new Insets(5, 5, 10, 10)); //Sætter objekternes afstand fra kanterne
            sidePaneLeft = mySidepaneLeft;
            rootLayout.setLeft(sidePaneLeft);

            VBox mySidepaneRight = new VBox();
            mySidepaneRight.setSpacing(20); //laver mellemrum mellem objekterne i VBox'en.
            mySidepaneRight.setPadding(new Insets(5, 10, 10, 5)); //Sætter objekternes afstand fra kanterne
            sidePaneRight = mySidepaneRight;
            rootLayout.setRight(sidePaneRight);

            VBox myCenterView = new VBox();
            myCenterView.setSpacing(20); //laver mellemrum mellem objekterne i VBox'en.
            myCenterView.setPadding(new Insets(5, 5, 5, 5)); //Sætter objekternes afstand fra kanterne
            centerView = myCenterView;
            rootLayout.setCenter(myCenterView);

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
    public VBox getSidepaneLeft() { return sidePaneLeft; }
    public VBox getSidepaneRight() { return sidePaneRight;}
    public VBox getCenterView() { return centerView; }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Integer getPatientCPR() {
        return patientCPR;
    }


}




