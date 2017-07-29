/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.core.gui.controller;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.I18n;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for menu bar. Switches displayed pages according to pressed button.
 * @author Tomas Rejent
 */
@Controller
public class MenuController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    private static final String RESOURCE_PATH_RECIPES_PAGE = "/fxml/RecipeList.fxml";
    private static final String RESOURCE_PATH_INGREDIENTS_PAGE = "/fxml/IngredientList.fxml";
    private static final String RESOURCE_PATH_FOOD_LIST_PAGE = "/fxml/FoodList.fxml";
    private static final String RESOURCE_PATH_GENERATOR_PAGE = "/fxml/DietGenerator.fxml";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void showRecipeList(ActionEvent actionEvent){
        LOGGER.debug("Switching to Recipes page.");
        showPage(RESOURCE_PATH_RECIPES_PAGE);
    }
    
    @FXML
    private void showIngredientList(ActionEvent actionEvent){
        LOGGER.debug("Switching to Ingredients page.");
        showPage(RESOURCE_PATH_INGREDIENTS_PAGE);
    }

    @FXML
    private void showFoodList(ActionEvent actionEvent){
        LOGGER.debug("Switching to Food list page.");
        showPage(RESOURCE_PATH_FOOD_LIST_PAGE);
    }
    
    @FXML
    private void showGenerator(ActionEvent actionEvent){
        LOGGER.debug("Switching to Generator page.");
        showPage(RESOURCE_PATH_GENERATOR_PAGE);
    }

    private void showPage(final String resourcePath){
        try {
            final Parent page = FXMLLoader.load(getClass().getResource(resourcePath), I18n.getResourceBundle());
            final BorderPane rootPane = Main.getRootPane();
            rootPane.setCenter(page);
        } catch (IOException ex) {
            LOGGER.error("Cannot show page from resource: " + resourcePath, ex);
        }
    }
}
