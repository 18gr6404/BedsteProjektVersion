package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class SummaryCtrl {

    // Reference to the main application.
    private MainApp mainAppRef;
    private RootLayoutCtrl rootLayoutCtrlRef;

    @FXML
    private TextArea summaryField;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SummaryCtrl() {
    }

    /**
     * Initializes the ch.controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

    private void initialize() {

    }

    @FXML
    private void handleOk(){

        //Her et SQL-kald der gemmer i dB


        rootLayoutCtrlRef.getRootLayout().setBottom(null);
    }

    @FXML
    private void handleCancel(){
        rootLayoutCtrlRef.getRootLayout().setBottom(null);
    }

    @FXML
    private void handlePrintSelectedData(){

    }

    /**
     * Is called to give a reference back to itself.
     *
     * @param inputRootLayoutCtrl
     */
    public void setRootLayoutCtrlRef(RootLayoutCtrl inputRootLayoutCtrl) {
        this.rootLayoutCtrlRef = inputRootLayoutCtrl;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param inputMain
     */
    public void setMainApp(MainApp inputMain) {
        this.mainAppRef = inputMain;
    }

}
