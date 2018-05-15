package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.dbControl;
import ch.model.EncapsulatedParameters;
import ch.model.WeeklyParameters;
import ch.utility.FhirUpload;
import ch.utility.dateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.exceptions.FHIRException;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class WeeklyOverviewCtrl implements Initializable {


    @FXML
    private Button overViewBtn;
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
    private Button dateOkBtn;

    @FXML
    private DatePicker startPicker;
    @FXML
    private DatePicker endPicker;

    @FXML
    private Label HvaesenLabel;
    @FXML
    private Label HosteLabel;
    @FXML
    private Label HosteSlimLabel;
    @FXML
    private Label TrykkenBrystLabel;
    @FXML
    private Label AandenoedLabel;
    @FXML
    private Label NathosteLabel;
    @FXML
    private Label OpvaagningLabel;
    @FXML
    private Label TraethedLabel;
    @FXML
    private Label AktivitetLabel;
    @FXML
    private Label AllergiLabel;
    @FXML
    private Label StoevLabel;
    @FXML
    private Label UkendtLabel;
    @FXML
    private BarChart AstmaAppBarChart;
    @FXML
    private CategoryAxis UgeNr;
    @FXML
    private NumberAxis Antal;
    @FXML
    private LineChart PEFFEVChart;

    private Integer patientCPR;
    private LocalDate startDate; //Start dato for FHIR-søgningen ´. Dette er den ældste dato
    private LocalDate endDate; //Slutdato for FHIR-søgningen ´. Dette er den nyeste dato

    private MainApp mainAppRef;
    dbControl dbControlOB = dbControl.getInstance();
    // REference til Rootlayout
    private RootLayoutCtrl rootLayoutCtrlRef;

    public WeeklyOverviewCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

    public void initialize(URL url, ResourceBundle resourceBundle){

        LocalDate startDate = dateUtil.parse("10.03.2018");
        LocalDate endDate = dateUtil.parse("10.05.2018");
        CalculatedParametersCtrl calcParam = new CalculatedParametersCtrl();
        EncapsulatedParameters beggeParam = calcParam.buildCalculatedParameters(1207731470, startDate, endDate);
        WeeklyParameters WeeklyOverviewParam = beggeParam.getWeeklyParameters();
        List<Observation> fevListe =WeeklyOverviewParam.getFev1();
        try{
            System.out.println(fevListe.get(0).getValueQuantity().getValue() + "," +  "," + fevListe.get(0).getIssued());
        }catch(FHIRException e){
            System.out.println(e.getMessage());
        }

        XYChart.Series dagSymptomer = new XYChart.Series<>();
        dagSymptomer.setName("Dagsymptomer");

        XYChart.Series natSymptomer = new XYChart.Series<>();
        natSymptomer.setName("Natsymptomer");

        XYChart.Series aktivitetsBegraensning = new XYChart.Series<>();
        aktivitetsBegraensning.setName("Aktivitetsbegrænsning");


        //Sætter instansvariablerne for start og slut dato til defaultværdier for at vise de seneste 4 uger.
        startDate = LocalDate.now().minusDays(14);
        endDate = LocalDate.now();

    }


    @FXML
    private void handleConsultationMeasurement(){

        ConsultationMeasurementCtrl.showConsultationMeasurementView();
    }

    @FXML
    private void handleOverview(){
        rootLayoutCtrlRef.showOverview();
    }

    @FXML
    private void handleSummary(){ rootLayoutCtrlRef.showSummaryView(); }

    @FXML
    private void handleTwoWeeks(){
        endDate = LocalDate.now();
        startDate = LocalDate.now().minusDays(14);

        //showData();
    }

    @FXML
    private void handleFourWeeks(){
        endDate = LocalDate.now();
        startDate = LocalDate.now().minusDays(28);

       // showData();
    }

    @FXML
    private void handleCustomDate(){
        /* I start pickereren vælger lægen hvilken dato han vil se fra, dette vil typisk være den nyeste dato derfor sættes denne som slutningen
        på FHIR-søgningen. Ligeledes omvendt.
        */
        LocalDate tempEndDate = startPicker.getValue();
        LocalDate tempStartDate = endPicker.getValue();
        //Sørger for at den nyeste dato sættes som endDate:
        if (endDate.isAfter(startDate)){
            endDate = tempEndDate;
            startDate = tempStartDate;
        }
        else{
            endDate = tempStartDate;
            startDate = tempEndDate;
        }

        System.out.print(endDate);
        //showData();
    }

    @FXML
    private void handleSinceLastConsultation(){
        endDate = LocalDate.now();

        //LocalDate endDate = mainAppRef.getLastConsultationDate();
    }

/*
    public void showData(){
        if(mainAppRef == null) {
            mainAppRef = rootLayoutCtrlRef.getMainAppRef();
        }
        patientCPR = (Integer) mainAppRef.getPatientCPR();
        if (patientCPR == 1207731450){
            patientCPR = 1207731470;
        }
        else if (patientCPR == 1303803813){
            patientCPR = 1303803823;
        }

        CalculatedParametersCtrl calcParam = new CalculatedParametersCtrl();
        EncapsulatedParameters beggeParam = calcParam.buildCalculatedParameters(patientCPR, startDate, endDate);
        WeeklyParameters WeeklyOverviewParam = beggeParam.getWeeklyParameters();
        System.out.println(WeeklyOverviewParam.getUgeListeDagSymptomer().size());


        for (int i = 0; i<WeeklyOverviewParam.getUgeListeDagSymptomer().size(); i++) {
            int weeknumber = (WeeklyOverviewParam.getFoersteUge())+i;

            //Dagsymptomer
            XYChart.Series dagSymptomer = new XYChart.Series<>();
            dagSymptomer.setName("Dagsymptomer");
            dagSymptomer.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeDagSymptomer().get(i)));

            //Natsymptomer
            XYChart.Series natSymptomer = new XYChart.Series<>();
            natSymptomer.setName("Natsymptomer");
            natSymptomer.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeNatSymptomer().get(i)));

            //Aktivitetsbegrænsning
            XYChart.Series aktivitetsBegraensning = new XYChart.Series<>();
            aktivitetsBegraensning.setName("Aktivitetsbegrænsning");
            aktivitetsBegraensning.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeAktivitet().get(i)));

            //Anfaldsmedicin
            XYChart.Series anfald = new XYChart.Series<>();
            anfald.setName("Anfaldsmedicin");
            anfald.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeAnfaldsMed().get(i)));


            AstmaAppBarChart.getData().addAll(dagSymptomer, natSymptomer, aktivitetsBegraensning, anfald);

        }

    }*/



    /**
     * Is called to give a reference back to itself.
     *
     * @param inputRootLayoutCtrl
     */
    public void setRootLayoutCtrlRef(RootLayoutCtrl inputRootLayoutCtrl) {
        this.rootLayoutCtrlRef = inputRootLayoutCtrl;
    }


}




