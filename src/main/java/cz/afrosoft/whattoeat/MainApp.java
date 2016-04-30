package cz.afrosoft.whattoeat;

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

        BorderPane root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
        rootPane = root;
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Menu.css");
        
        stage.setTitle("WhatToEat");
        stage.setScene(scene);
        stage.show();

        LOGGER.info("Application started.");
    }
    
    private BorderPane getBorderPane(Parent sceneRoot){
        for(Node node : sceneRoot.getChildrenUnmodifiable()){
            if(node instanceof BorderPane){
                return (BorderPane) node;
            }
        }
        
        throw new IllegalStateException("Parent does not contain border pane.");
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
