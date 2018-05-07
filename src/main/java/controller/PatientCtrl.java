package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PatientCtrl {
    @FXML
    private Label nameLabel;
    @FXML
    private Label cprLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label genderLabel;


    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
     // private main main;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PatientCtrl() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param main
     */
   // public void setmain(main main) {
     //   this.main = main;

   // }

}
