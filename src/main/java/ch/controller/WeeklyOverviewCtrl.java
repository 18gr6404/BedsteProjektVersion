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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private LineChart LineChart;

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

        //Sætter instansvariablerne for start og slut dato til defaultværdier for at vise de seneste 4 uger.
        //startDate = LocalDate.now().minusDays(28);
        //endDate = LocalDate.now();
        startDate = LocalDate.parse("2018-03-10");
        endDate = LocalDate.parse("2018-05-10");

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
        //Dette hører til BarChart
        CalculatedParametersCtrl calcParam = new CalculatedParametersCtrl();
        EncapsulatedParameters beggeParam = calcParam.buildCalculatedParameters(patientCPR, startDate, endDate);
        WeeklyParameters WeeklyOverviewParam = beggeParam.getWeeklyParameters();

        //Dette hører til LineChart:FEV1
        List<Observation> FEVListe = WeeklyOverviewParam.getFev1();

        //Dette hører til LineChart:PEF
        List<Observation> PEFMorgen = WeeklyOverviewParam.getMorgenPEF();
        List<Observation> PEFAften = WeeklyOverviewParam.getAftenPEF();

        //BarChart
        XYChart.Series dagSymptomer = new XYChart.Series<>();
        dagSymptomer.setName("Dagsymptomer");

        XYChart.Series natSymptomer = new XYChart.Series<>();
        natSymptomer.setName("Natsymptomer");

        XYChart.Series aktivitetsBegraensning = new XYChart.Series<>();
        aktivitetsBegraensning.setName("Aktivitetsbegrænsning");

        XYChart.Series anfald = new XYChart.Series<>();
        anfald.setName("Anfaldsmedicin");

        //LineChart
        XYChart.Series FEV1 = new XYChart.Series<>();
        FEV1.setName("FEV1");

        XYChart.Series pefmorgen = new XYChart.Series<>();
        pefmorgen.setName("Morgen PEF");

        XYChart.Series pefaften = new XYChart.Series<>();
        pefaften.setName("Aften PEF");

        //Add data to LineChart:FEV1
        for (int i = 0; i<FEVListe.size(); i++){
            try{
                FEV1.getData().add(new XYChart.Data("" + FEVListe.get(i).getIssued(), FEVListe.get(i).getValueQuantity().getValue()));
            }catch(FHIRException e){
                System.out.println(e.getMessage());
            }
        }
        //Add data to LineChart:PEF
       for (int i = 0; i<PEFMorgen.size(); i++){
           try{
               pefmorgen.getData().add(new XYChart.Data(""+ Instant.ofEpochMilli(PEFMorgen.get(i).getIssued().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(), PEFMorgen.get(i).getValueQuantity().getValue()));
               pefaften.getData().add(new XYChart.Data(""+ Instant.ofEpochMilli(PEFAften.get(i).getIssued().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(), PEFAften.get(i).getValueQuantity().getValue()));
           }catch(FHIRException e){
               System.out.println(e.getMessage());
           }

       }
        //Add data to BarChart
        for (int i = 0; i<WeeklyOverviewParam.getUgeListeDagSymptomer().size(); i++) {
            int weeknumber = (WeeklyOverviewParam.getFoersteUge())+i;

            //Dagsymptomer
            dagSymptomer.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeDagSymptomer().get(i)));

            //Natsymptomer
            natSymptomer.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeNatSymptomer().get(i)));

            //Aktivitetsbegrænsning
            aktivitetsBegraensning.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeAktivitet().get(i)));

            //Anfaldsmedicin
            anfald.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeAnfaldsMed().get(i)));

        }
        //Add data and legend to BarChart
        AstmaAppBarChart.setLegendSide(Side.RIGHT);
        AstmaAppBarChart.getData().addAll(dagSymptomer, natSymptomer, aktivitetsBegraensning, anfald);

        //Add data and legend to LineChart
        LineChart.setLegendSide(Side.RIGHT);
        LineChart.getData().addAll(FEV1, pefaften, pefmorgen);
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




