import db_And_FHIR.dbControl;
import javafx.application.Application;
import db_And_FHIR.dbControl;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    private Stage primaryStage;
    private java.sql.Connection con;
    dbControl myDBClass = new dbControl();

    @Override
    public void start(Stage primaryStage) throws IOException {
    con = myDBClass.connect();
    }
}
