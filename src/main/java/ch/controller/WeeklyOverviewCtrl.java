package ch.controller;

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

import java.net.URL;
import java.time.LocalDate;
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

        XYChart.Series dagSymptomer = new XYChart.Series<>();
        dagSymptomer.setName("Dagsymptomer");

        XYChart.Series natSymptomer = new XYChart.Series<>();
        natSymptomer.setName("Natsymptomer");

        XYChart.Series aktivitetsBegraensning = new XYChart.Series<>();
        aktivitetsBegraensning.setName("Aktivitetsbegrænsning");

        XYChart.Series anfald = new XYChart.Series<>();
        anfald.setName("Anfaldsmedicin");

        for (int i = 0; i<4; i++) {
            int weeknumber = (WeeklyOverviewParam.getFoersteUge())+i;

            //Dagsymptomer
            dagSymptomer.getData().add(new XYChart.Data("Uge " + weeknumber,  WeeklyOverviewParam.getUgeListeDagSymptomer().get(i)));

            //Natsymptomer
            natSymptomer.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeNatSymptomer().get(i)));

            //Aktivitetsbegrænsning
            aktivitetsBegraensning.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeAktivitet().get(i)));

            //Anfaldsmedicin
            anfald.getData().add(new XYChart.Data("Uge " + weeknumber, WeeklyOverviewParam.getUgeListeAnfaldsMed().get(i)));

        }

        AstmaAppBarChart.setLegendSide(Side.RIGHT);
        AstmaAppBarChart.getData().addAll(dagSymptomer, natSymptomer, aktivitetsBegraensning, anfald);


        System.out.println(WeeklyOverviewParam.getUgeListeAktivitet().get(1));
        System.out.println(WeeklyOverviewParam.getUgeListeDagSymptomer().get(1));
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
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(14);
    }

    @FXML
    private void handleFourWeeks(){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(28);
    }

    @FXML
    private void handleCustomDate(){
        LocalDate startDate = startPicker.getValue();
        LocalDate endDate = endPicker.getValue();
    }

    @FXML
    private void handleSinceLastConsultation(){
        LocalDate startDate = LocalDate.now();

        //LocalDate endDate = mainAppRef.getLastConsultationDate();
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




