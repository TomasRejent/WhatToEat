package cz.afrosoft.whattoeat;

import com.google.common.collect.ImmutableList;
import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.diet.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    private static BorderPane rootPane;
    
    public static BorderPane getRootPane(){
        if(rootPane == null){
            throw new IllegalStateException("Root pane is null.");
        }
        
        return rootPane;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("Starting application.");

        I18n.init("cz");
        rootPane = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"), I18n.getResourceBundle());
        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("/styles/Menu.css");
        
        stage.setTitle(I18n.getText("cz.afrosoft.whattoeat.title"));
        stage.setScene(scene);
        stage.show();

        LOGGER.info("Application started.");
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
