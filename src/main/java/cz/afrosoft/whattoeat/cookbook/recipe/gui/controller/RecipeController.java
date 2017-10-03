package cz.afrosoft.whattoeat.cookbook.recipe.gui.controller;

import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeAddDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.PreparationTime;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.DeleteButton;
import cz.afrosoft.whattoeat.core.gui.component.EditButton;
import cz.afrosoft.whattoeat.core.gui.component.ViewButton;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.CollectionCell;
import cz.afrosoft.whattoeat.core.gui.table.KeywordCell;
import cz.afrosoft.whattoeat.core.gui.table.LabeledCell;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
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
    private TableColumn<Recipe, PreparationTime> preparationTimeColumn;
    @FXML
    private TableColumn<Recipe, Taste> tasteColumn;
    @FXML
    private TableColumn<Recipe, Integer> ratingColumn;
    @FXML
    private TableColumn<Recipe, Collection<Keyword>> keywordColumn;

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
        setupColumnCellFactories();
        disableRecipeActionButtons(true);
        setupSelectionHandler();
        recipeTable.getItems().addAll(recipeService.getAllRecipes());
    }

    /**
     * Setup cell value factories for all columns.
     */
    private void setupColumnCellFactories() {
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Recipe::getName));
        recipeTypeColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Recipe::getRecipeTypes, Collections.emptySet()));
        recipeTypeColumn.setCellFactory(column -> CollectionCell.newInstance(recipeType -> I18n.getText(recipeType.getLabelKey())));
        preparationTimeColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Recipe::getTotalPreparationTime, null));
        preparationTimeColumn.setCellFactory(param -> new LabeledCell<>());
        tasteColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Recipe::getTaste, null));
        tasteColumn.setCellFactory(param -> new LabeledCell<>());
        ratingColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Recipe::getRating, 0));
        keywordColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Recipe::getKeywords, Collections.emptySet()));
        keywordColumn.setCellFactory(column -> new KeywordCell<>());
    }

    /**
     * Sets state of cookbook action buttons. These buttons require cookbook to be selected.
     *
     * @param disabled True to disable buttons, false to enable.
     */
    private void disableRecipeActionButtons(final boolean disabled) {
        viewButton.setDisable(disabled);
        editButton.setDisable(disabled);
        deleteButton.setDisable(disabled);
    }

    /**
     * Setup listener for table selection. Enables or disables buttons which require author to be selected.
     */
    private void setupSelectionHandler() {
        recipeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> disableRecipeActionButtons(newValue == null)
        );
    }

    /**
     * Gets last selected recipe.
     *
     * @return (NotNull) Empty optional if no recipe is selected. Otherwise optional with selected cookbook.
     */
    private Optional<Recipe> getSelectedRecipe() {
        return Optional.ofNullable(recipeTable.getSelectionModel().getSelectedItem());
    }

    /* Button actions */

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
        getSelectedRecipe().ifPresent(//recipe is selected
                recipe -> recipeAddDialog.editRecipe(recipe).ifPresent(//edit is confirmed
                        recipeUpdateObject -> Collections.replaceAll(recipeTable.getItems(), recipe, recipeService.createOrUpdate(recipeUpdateObject))//table is updated
                )
        );
    }

    @FXML
    private void deleteRecipe(ActionEvent actionEvent){
        LOGGER.debug("Delete recipe action called.");
        getSelectedRecipe().ifPresent(recipe -> {
            if (DialogUtils.showConfirmDialog(
                    I18n.getText(DELETE_CONFIRM_TITLE_KEY), I18n.getText(DELETE_CONFIRM_MESSAGE_KEY, recipe.getName())
            )) {
                recipeService.delete(recipe);
                recipeTable.getItems().remove(recipe);
            }
        });
    }
    
}
