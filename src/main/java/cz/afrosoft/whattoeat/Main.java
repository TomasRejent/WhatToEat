package cz.afrosoft.whattoeat;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.PAGE;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Entry point of application. Extends {@link Application} to be able to use JavaFX. Also set up Spring for dependency injection
 * and persistence layer. Connects JavaFX and Spring together so Spring can create controllers for JavaFX.
 */
@SpringBootApplication
public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * Spring application context. Provides components for dependency injection.
     */
    private static ConfigurableApplicationContext applicationContext;

    private static BorderPane rootPane;

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args The command line arguments. Currently not used for anything.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * Loads component for page. Construct FXML Loader which creates controller from Spring context.
     *
     * @param page (NotNull) Page to load.
     * @return (NotNull) GUI Component loaded by FXML Loader representing specified page.
     * @throws IOException When fxml file specified in page cannot be loaded by FXML Loader.
     */
    public static <T> T loadPage(final PAGE page) throws IOException {
        Validate.notNull(page);
        Validate.notNull(applicationContext);

        FXMLLoader fxmlLoader = new FXMLLoader(page.toUrlResource(), I18n.getResourceBundle());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        T pageComponent = fxmlLoader.load();
        if (pageComponent == null) {
            throw new IllegalStateException(String.format("GUI Component loaded for page %s is null.", page));
        }
        return pageComponent;
    }


    public static BorderPane getRootPane(){
        if(rootPane == null){
            throw new IllegalStateException("Root pane is null.");
        }

        return rootPane;
    }

    /**
     * Initializes application before it is started. Creates Spring application context and sets it as provider
     * for JavaFX controllers.
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(Main.class);

        I18n.init("cz");
        rootPane = loadPage(PAGE.ROOT);

    }

    @Override
    public void start(final Stage stage) throws Exception {
        LOGGER.info("Starting application.");


        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("/styles/Menu.css");
        
        stage.setTitle(I18n.getText("cz.afrosoft.whattoeat.title"));
        stage.setScene(scene);
        stage.show();

        LOGGER.info("Application started.");
    }

    /**
     * Called when application should stop to release resources. Closes Spring application context.
     * @throws Exception If any error occurs.
     */
    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }
}
