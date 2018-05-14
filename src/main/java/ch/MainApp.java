package ch;

import ch.controller.*;
import ch.db_And_FHIR.*;
import ch.controller.CreateAsthmaAppUserCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private VBox sidePaneLeft;
    private VBox sidePaneRight;
    private VBox centerView;

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

        myDBClass.buildPractitionerData(56789);
        myDBClass.buildAllergyIntoleranceData(patientCPR);
        myDBClass.buildConditionData(patientCPR);
        myDBClass.buildMedicineData(patientCPR);
        myDBClass.getPatientData(patientCPR);

        RootLayoutCtrl rootLayoutCtrlRef = new RootLayoutCtrl();
        centerView = rootLayoutCtrlRef.initRootLayout(this.primaryStage);

        boolean isRegistered = myDBClass.requestIsRegistered(patientCPR);

        if(isRegistered) {
            rootLayoutCtrlRef.initBasicLayout();

        }
        else {

            CreateAsthmaAppUserCtrl createAsthmaAppUserCtrl = new CreateAsthmaAppUserCtrl();
            createAsthmaAppUserCtrl.showCreateAsthmaAppUser(this.centerView);

        }


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
    private BorderPane initRootLayout() {
        FXMLLoader loader = new FXMLLoader();

        try {
            // Load root layout from fxml file.
            loader.setLocation(MainApp.class.getResource("/ch/view/RootLayout.fxml"));
            rootLayout = loader.<BorderPane>load();

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

            return rootLayout;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootLayout;
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




