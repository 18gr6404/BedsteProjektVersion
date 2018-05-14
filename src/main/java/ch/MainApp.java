package ch;

import ch.controller.*;
import ch.db_And_FHIR.*;
import ch.model.EncapsulatedParameters;
import ch.model.OverviewParameters;
import ch.model.WeeklyParameters;
import ch.utility.dateUtil;
import ch.controller.CreateAsthmaAppUserCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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


    


    //private Integer patientCPR = 1207731450; //Marianne. Daniel vil gerne = 1207731470
    private Integer patientCPR = 1303803813;  //Jens. Daniel vil gerne = 1303803823
    private Integer practitionerID = 56789; // Ole Bosen


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
        myDBClass.buildFEV(patientCPR);
       /* 
        myDBClass.buildPractitionerData(56789);
        myDBClass.buildAllergyIntoleranceData(patientCPR);
        myDBClass.buildConditionData(patientCPR);
        myDBClass.buildMedicineData(patientCPR);*/

        myDBClass.buildPatientData(patientCPR);

        RootLayoutCtrl rootLayoutCtrlRef = new RootLayoutCtrl();
        centerView = rootLayoutCtrlRef.initRootLayout(this.primaryStage);


        CalculatedParametersCtrl calcParam = new CalculatedParametersCtrl();
        // HER HENTER JEG DE UDREGNEDE PARAMETRE
        EncapsulatedParameters beggeParam = calcParam.buildCalculatedParameters(1207731470, startDate, endDate); // Marianne CPR på FHIR Server = 1207731470
        OverviewParameters OverviewParam = beggeParam.getOverviewParameters();
        WeeklyParameters WeeklyOverviewParam = beggeParam.getWeeklyParameters();
        System.out.println(OverviewParam.getAvgFEV1());
        boolean isRegistered = myDBClass.requestIsRegistered(patientCPR);

        if(isRegistered) {

            //Sætter Practitioner i vores basis-view v. at lave en ny instans af controlleren, lave en referece til MainApp og kalde show-metoden
            PractitionerCtrl practitionerCtrl = new PractitionerCtrl();
            practitionerCtrl.setMainApp(this); // 
            //practitionerCtrl.showPractitioner(); //

            
            rootLayoutCtrlRef.initBasicLayout();

        }
        else {

            CreateAsthmaAppUserCtrl createAsthmaAppUserCtrl = new CreateAsthmaAppUserCtrl();
            createAsthmaAppUserCtrl.showCreateAsthmaAppUser(this.centerView);


        }

        ConsultationMeasurementCtrl consultationMeasurementCtrl = new ConsultationMeasurementCtrl();
        consultationMeasurementCtrl.setMainApp(this);
        consultationMeasurementCtrl.showConsultationMeasurement();

    }

    public VBox getSidepaneRight(){return sidePaneRight;}
    public VBox getCenterView(){return centerView;}
    public Integer getPatientCPR() {
        return patientCPR;
    }


   

    public Integer getPractitionerID() { return practitionerID; }
}





