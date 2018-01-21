package cz.afrosoft.whattoeat;

import cz.afrosoft.whattoeat.core.gui.FXMLLoaderFactory;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Page;
import cz.afrosoft.whattoeat.core.gui.component.SplashScreen;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.Validate;
import org.hsqldb.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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

    /**
     * Root of application gui.
     */
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
    public static <T> T loadPage(final Page page) throws IOException {
        Validate.notNull(page);
        Validate.notNull(applicationContext);

        FXMLLoaderFactory fxmlLoaderFactory = applicationContext.getBean(FXMLLoaderFactory.class);
        FXMLLoader fxmlLoader = fxmlLoaderFactory.createFXMLLoader(page.toUrlResource());
        T pageComponent = fxmlLoader.load();
        if (pageComponent == null) {
            throw new IllegalStateException(String.format("GUI Component loaded for page %s is null.", page));
        }
        return pageComponent;
    }

    /**
     * Gets Spring application context.
     *
     * @return (NotNull)
     */
    public static ApplicationContext getApplicationContext() {
        Validate.notNull(applicationContext);
        return applicationContext;
    }

    @Override
    public void init() throws Exception {
        long time = System.nanoTime();
        I18n.init("cz");
        LOGGER.info("I18n initialized in {}ms", (System.nanoTime() - time) * 0.000001);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        LOGGER.info("Starting application.");
        Stage splashStage = showSplashScreen();
        /**
         * Initialization of Spring is not done in CompletableFuture in parallel because it does not work for jar.
         * If run by IDE it works, but when it is packed to jar and run, then exception
         * <code>
         *     Caused by: java.lang.IllegalArgumentException: No auto configuration classes found in META-INF/spring.factories.
         *     If you are using a custom packaging, make sure that file is correct.
         * </code>
         * occurs. So Spring must be initialised in JavaFX thread. Because splash screen does not consume any user interaction
         * it does not matter that JavaFX thread is blocked for several seconds when initializing Spring.
         * */
        Platform.runLater(this::prepareApplication);
        Platform.runLater(() -> switchToMainStage(stage, splashStage));
    }

    /**
     * Shows {@link SplashScreen} component in undecorated stage.
     *
     * @return (NotNull) Splash screen stage. Can be used to hide splash screen with {@link Stage#hide()}.
     */
    private Stage showSplashScreen() {
        long time = System.nanoTime();
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        splashStage.setScene(new Scene(new SplashScreen()));
        splashStage.show();
        LOGGER.info("Splash screen initialized in {}ms", (System.nanoTime() - time) * 0.000001);
        return splashStage;
    }

    /**
     * Initializes Spring and persistence layer. Creates root component of UI from fxml. Must be called from JavaFX thread,
     * because it sets exception handler for thread and because Spring does not work in packaged jar when initialized from other thread.
     */
    private void prepareApplication() {
        LOGGER.info("Initializing spring.");
        long time = System.nanoTime();
        //start Spring
        applicationContext = SpringApplication.run(Main.class);
        try {
            //load main screen
            rootPane = loadPage(Page.ROOT);
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
        //setup exception handler
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            LOGGER.error("Unhandled exception occurred.", throwable);
            DialogUtils.showExceptionDialog(I18n.getText("cz.afrosoft.whattoeat.common.exception"), throwable);
        });
        LOGGER.info("Spring initialized in {}ms", (System.nanoTime() - time) * 0.000001);
    }

    private void switchToMainStage(final Stage stage, final Stage splashStage) {
        LOGGER.info("Switching to main window.");
        long time = System.nanoTime();
        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("/styles/Application.css");

        stage.setTitle(I18n.getText("cz.afrosoft.whattoeat.title"));
        stage.setScene(scene);
        stage.show();
        splashStage.hide();
        LOGGER.info("Screen switch in {}ms", (System.nanoTime() - time) * 0.000001);
        LOGGER.info("Application started.");
    }

    /**
     * Called when application should stop to release resources. Closes Spring application context.
     * @throws Exception If any error occurs.
     */
    @Override
    public void stop() throws Exception {
        applicationContext.close();
        /* Explicit DB close so all changes are flushed to database.script after program exit.*/
        DatabaseManager.closeDatabases(4);
    }
}
