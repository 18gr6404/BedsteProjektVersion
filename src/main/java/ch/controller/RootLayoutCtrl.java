package ch.controller;

import ch.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RootLayoutCtrl {

    private BorderPane rootLayout;
    private VBox sidePaneLeft;
    private VBox sidePaneRight;
    private VBox centerView;

    /**
     * Constructor
     */
    public RootLayoutCtrl()
    {}


    public VBox initRootLayout(Stage inputPrimaryStage) {
        FXMLLoader loader = new FXMLLoader();

        try {
            // Load root layout from fxml file.
            loader.setLocation(RootLayoutCtrl.class.getResource("/ch/view/RootLayout.fxml"));
            loader.setController(this);
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

            this.rootLayout = rootLayout;

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(rootLayout);
        inputPrimaryStage.setScene(scene);
        inputPrimaryStage.show();
        inputPrimaryStage.setFullScreen(false);

        return centerView;
    }

    public void initBasicLayout(){
        PractitionerCtrl practitionerCtrl = new PractitionerCtrl();
        this.sidePaneLeft = practitionerCtrl.showPractitioner(sidePaneLeft);

        PatientCtrl patientCtrl = new PatientCtrl();
        this.sidePaneLeft = patientCtrl.showPatient(sidePaneLeft);

        MedicationCtrl medicationCtrl = new MedicationCtrl();
        this.sidePaneLeft = medicationCtrl.showMedication(sidePaneLeft);

        ConditionCtrl conditionCtrl = new ConditionCtrl();
        this.sidePaneRight = conditionCtrl.showConditionView(sidePaneRight);

        AllergyIntoleranceCtrl allergyIntoleranceCtrl = new AllergyIntoleranceCtrl();
        this.sidePaneRight = allergyIntoleranceCtrl.showAllergyIntolerance(sidePaneRight);
    }

    public VBox getSidepaneLeft(){
        return this.sidePaneLeft;
    }
    public VBox getSidepaneRight(){
        return this.sidePaneRight;
    }
    public VBox getCenterView(){
        return this.centerView;
    }

}
