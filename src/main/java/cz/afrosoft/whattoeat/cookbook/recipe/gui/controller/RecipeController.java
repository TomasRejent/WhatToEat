/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.gui.controller;

import cz.afrosoft.whattoeat.core.gui.component.DeleteButton;
import cz.afrosoft.whattoeat.core.gui.component.EditButton;
import cz.afrosoft.whattoeat.core.gui.component.ViewButton;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.RecipeOld;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller fot page {@link cz.afrosoft.whattoeat.core.gui.Page#RECIPES}.
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecipeController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    private static final String DELETE_CONFIRM_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.delete.recipe.title";
    private static final String DELETE_CONFIRM_MESSAGE_KEY = "cz.afrosoft.whattoeat.dialog.delete.recipe.messages";

    @FXML
    private TableView<RecipeOld> recipeTable;
    @FXML
    private TableColumn<RecipeOld, String> nameColumn;
    @FXML
    private TableColumn<RecipeOld, String> recipeTypeColumn;
    @FXML
    private TableColumn<RecipeOld, String> preparationTimeColumn;
    @FXML
    private TableColumn<RecipeOld, String> tasteColumn;
    @FXML
    private TableColumn<RecipeOld, String> ratingColumn;
    @FXML
    private TableColumn<RecipeOld, String> keywordsColumn;

    @FXML
    private ViewButton viewButton;
    @FXML
    private EditButton editButton;
    @FXML
    private DeleteButton deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Initializing recipe controller");
    }


    @FXML
    private void showRecipe(ActionEvent actionEvent){
    }

    @FXML
    private void addRecipe(ActionEvent actionEvent){
    }

    @FXML
    private void editRecipe(ActionEvent actionEvent){
    }

    @FXML
    private void deleteRecipe(ActionEvent actionEvent){
        LOGGER.debug("Delete recipe action called.");
    }
    
}
