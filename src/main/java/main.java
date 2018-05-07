import db_And_FHIR.dbControl;
import javafx.application.Application;
import db_And_FHIR.dbControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private java.sql.Connection con;
    dbControl myDBClass = new dbControl();

    /**
     * Constructor
     */
    public main() {
        //
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AstmaAppSystem");


    con = myDBClass.connect();

        //isRegistered = DBCtrl.requestIsRegistered();   - Her skal vi indhente isRegistered fra DB og have en if-sætning.

        //if(isRegistered){
        initRootLayout(); //initiate root layout


        // buildPatient();  //Her skal vi kalde vores funktioner til at bygge vores modeller
        //showPerson(); //Her skal vi kalde vores funktioner til at vise

        //}
        //else{
        //showCreateAsthmaAppUser();
        //}

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
