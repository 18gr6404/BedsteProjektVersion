package ch.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MedicationCtrl {
    @FXML
    private Label typeLabel;

    @FXML
    private Label dosisLabel;

    @FXML
    private Label dateLabel;

    // Reference to the main application. - Denne var i AddressApp men er ikke helt sikke på om vi skal bruge den.
    // private main main;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MedicationCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
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
