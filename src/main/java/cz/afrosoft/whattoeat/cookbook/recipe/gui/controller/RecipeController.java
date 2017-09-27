package cz.afrosoft.whattoeat.cookbook.recipe.gui.controller;

import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeAddDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.component.DeleteButton;
import cz.afrosoft.whattoeat.core.gui.component.EditButton;
import cz.afrosoft.whattoeat.core.gui.component.ViewButton;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.Duration;
import java.util.Collection;
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
    private TableView<Recipe> recipeTable;
    @FXML
    private TableColumn<Recipe, String> nameColumn;
    @FXML
    private TableColumn<Recipe, Collection<RecipeType>> recipeTypeColumn;
    @FXML
    private TableColumn<Recipe, Duration> preparationTimeColumn;
    @FXML
    private TableColumn<Recipe, Taste> tasteColumn;
    @FXML
    private TableColumn<Recipe, Float> ratingColumn;
    @FXML
    private TableColumn<Recipe, Collection<Keyword>> keywordsColumn;

    @FXML
    private ViewButton viewButton;
    @FXML
    private EditButton editButton;
    @FXML
    private DeleteButton deleteButton;

    @Autowired
    private RecipeAddDialog recipeAddDialog;

    @Autowired
    private RecipeService recipeService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Initializing recipe controller");
    }


    @FXML
    private void showRecipe(ActionEvent actionEvent){
        LOGGER.debug("Show recipe action triggered.");
    }

    @FXML
    private void addRecipe(ActionEvent actionEvent){
        LOGGER.debug("Add recipe action triggered.");
        recipeAddDialog.addRecipe().ifPresent(
                recipeUpdateObject -> recipeTable.getItems().add(recipeService.createOrUpdate(recipeUpdateObject))
        );
    }

    @FXML
    private void editRecipe(ActionEvent actionEvent){
        LOGGER.debug("Edit recipe action triggered.");
    }

    @FXML
    private void deleteRecipe(ActionEvent actionEvent){
        LOGGER.debug("Delete recipe action called.");
    }
    
}
