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
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class WeeklyOverviewCtrl implements Initializable {

    @FXML
    private Button OversigtButton;
    @FXML
    private Button IndtastMaalingButton;
    @FXML
    private Button UdfyldNotatButton;
    @FXML
    private Button SidenSidstButton;
    @FXML
    private Button FireUgerButton;
    @FXML
    private Button ToUgerButton;
    @FXML
    private DatePicker StartDatePicker;
    @FXML
    private DatePicker SlutDatePicker;
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
        System.out.println(WeeklyOverviewParam.getUgeListeAktivitet().get(1));
        System.out.println(WeeklyOverviewParam.getUgeListeDagSymptomer().get(1));
    }




//    @FXML
//    private void setChart(){
//
//
//
//    }

}




