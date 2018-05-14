package ch.controller;

import ch.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hl7.fhir.dstu3.model.AllergyIntolerance;

import java.io.IOException;

public class RootLayoutCtrl {

    private BorderPane rootLayout;
    private VBox sidePaneLeft;
    private VBox sidePaneRight;
    private VBox centerView;
    private MainApp mainAppRef;

    /**
     * Constructor
     */
    public RootLayoutCtrl(MainApp inputMainAppRef)
    {this.mainAppRef = inputMainAppRef; }


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

        try {   //Practitioner
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PractitionerCtrl.class.getResource("/ch/view/PractitionerView.fxml"));
                HBox practitionerView = (HBox) loader.load();

                sidePaneLeft.getChildren().add(practitionerView);

                PractitionerCtrl controller = loader.getController();
                controller.setMainApp(mainAppRef);
                controller.setPractitioner();


            } catch (IOException e) {
                e.printStackTrace();
            }


        try {  //Patient
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PatientCtrl.class.getResource("/ch/view/PatientView.fxml"));
            AnchorPane patientView = (AnchorPane) loader.load();

            sidePaneLeft.getChildren().add(patientView);

            PatientCtrl controller = loader.getController();
            controller.setMainApp(mainAppRef);
            controller.setPatient();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {  //Medication
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MedicationCtrl.class.getResource("/ch/view/MedicationView.fxml"));
            VBox MedicationView = (VBox) loader.load();

            sidePaneLeft.getChildren().add(MedicationView);

            MedicationCtrl controller = loader.getController();
            controller.setMainApp(mainAppRef);
            controller.setMedication();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {  //Condition
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ConditionCtrl.class.getResource("/ch/view/ConditionView.fxml"));
            VBox conditionView = (VBox) loader.load();

            sidePaneRight.getChildren().add(conditionView);

            ConditionCtrl controller = loader.getController();
            controller.setMainApp(mainAppRef);
            controller.setCondition();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {  //AllergyIntolerance
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AllergyIntoleranceCtrl.class.getResource("/ch/view/AllergyIntoleranceView.fxml"));
            VBox allergyIntoleranceView = (VBox) loader.load();

            sidePaneRight.getChildren().add(allergyIntoleranceView);

            AllergyIntoleranceCtrl controller = loader.getController();
            controller.setMainApp(mainAppRef);
            controller.setAllergyIntolerance();

        } catch (IOException e) {
            e.printStackTrace();
        }


      //  AllergyIntoleranceCtrl allergyIntoleranceCtrl = new AllergyIntoleranceCtrl();
      //  this.sidePaneRight = allergyIntoleranceCtrl.showAllergyIntolerance(sidePaneRight);
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
