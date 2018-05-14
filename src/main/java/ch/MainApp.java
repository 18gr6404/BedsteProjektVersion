package ch;

import ch.controller.*;
import ch.db_And_FHIR.*;
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
        calcParam.buildCalculatedParameters(1207731470, startDate, endDate); // Marianne CPR på FHIR Server = 1207731470
       boolean isRegistered = myDBClass.requestIsRegistered(patientCPR);

        if(isRegistered) {
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

    }




