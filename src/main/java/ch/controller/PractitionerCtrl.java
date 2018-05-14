package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.dbControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hl7.fhir.dstu3.model.Practitioner;

import java.io.IOException;

public class PractitionerCtrl extends HBox {

    // Reference to the main application. 
    private MainApp mainAppRef;

    private Practitioner practitionerObjekt;
    private String b;

    @FXML
    private Label practitionerNameLabel;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PractitionerCtrl() {
        /*FXMLLoader loader = new FXMLLoader();
        try{
        loader.setLocation(PractitionerCtrl.class.getResource("/ch/view/PractitionerView.fxml"));
        HBox practitionerView = (HBox) loader.load();

        // Laver et midlertidigt instans af vores sidepane for at vi kan sætte viewet heri.
        VBox tempSidepane = mainAppRef.getSidepane();
        tempSidepane.getChildren().add(practitionerView);


        } catch(IOException e) {
        e.printStackTrace();
        }*/
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        MainApp mainAppRef = new MainApp();
        Integer id = mainAppRef.getPractitionerID();
        dbControl dbControlOb = mainAppRef.getMyDBClass();
        prove(dbControlOb,id);
    }



    public void prove(dbControl myDBClass, Integer practitionerID) {
        practitionerObjekt = myDBClass.buildPractitionerData(practitionerID);
        practitionerNameLabel.setText(practitionerObjekt.getName().get(0).getGivenAsSingleString() +" "+ practitionerObjekt.getName().get(0).getFamily());
    }


    public void showPractitioner(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PractitionerCtrl.class.getResource("/ch/view/PractitionerView.fxml"));
            HBox practitionerView = (HBox) loader.load();

            // Laver et midlertidigt instans af vores sidepane for at vi kan sætte viewet heri.
            //BorderPane tempRootLayout = mainAppRef.getSidepane();
            //tempRootLayout.setCenter(PractitionerView);

            VBox tempSidepaneLeft = mainAppRef.getSidepaneLeft();
            tempSidepaneLeft.getChildren().add(practitionerView);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

// practitionerNameLabel.setText(practitionerObjekt.getName().get(0).getGivenAsSingleString()) + practitionerOb.getName().get(0).getFamily();

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        this.mainAppRef = inputMain;
    }

}
