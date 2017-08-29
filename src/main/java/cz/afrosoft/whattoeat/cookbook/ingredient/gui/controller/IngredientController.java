package cz.afrosoft.whattoeat.cookbook.ingredient.gui.controller;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.CollectionCell;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for page {@link cz.afrosoft.whattoeat.core.gui.Page#INGREDIENTS}.
 *
 * @author Tomas Rejent
 */
@Controller
public class IngredientController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientController.class);

    private static final String DELETE_CONFIRM_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.delete.ingredient.title";
    private static final String DELETE_CONFIRM_MESSAGE_KEY = "cz.afrosoft.whattoeat.dialog.delete.ingredient.messages";

    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, String> nameColumn;
    @FXML
    private TableColumn<Ingredient, IngredientUnit> unitColumn;
    @FXML
    private TableColumn<Ingredient, Float> priceColumn;
    @FXML
    private TableColumn<Ingredient, Collection<Keyword>> keywordColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @Autowired
    private IngredientService ingredientService;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        LOGGER.debug("Initializing ingredient controller");
        setupColumnCellFactories();
        disableIngredientActionButtons(true);
        setupSelectionHandler();
        ingredientTable.getItems().addAll(ingredientService.getAllIngredients());
    }

    /**
     * Setup cell value factories for all columns.
     */
    private void setupColumnCellFactories() {
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Ingredient::getName));
        unitColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Ingredient::getIngredientUnit, null));
        priceColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Ingredient::getPrice, null));
        keywordColumn.setCellFactory(column -> CollectionCell.newInstance(Keyword::getName));
    }

    /**
     * Sets state of ingredient action buttons. These buttons require cookbook to be selected.
     *
     * @param disabled True to disable buttons, false to enable.
     */
    private void disableIngredientActionButtons(final boolean disabled) {
        editButton.setDisable(disabled);
        deleteButton.setDisable(disabled);
    }

    /**
     * Setup listener for table selection. Enables or disables buttons which require author to be selected.
     */
    private void setupSelectionHandler() {
        ingredientTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> disableIngredientActionButtons(newValue == null)
        );
    }

    /**
     * Gets last selected ingredient.
     *
     * @return (NotNull) Empty optional if no ingredient is selected. Otherwise optional with selected ingredient.
     */
    private Optional<Ingredient> getSelectedIngredient() {
        return Optional.ofNullable(ingredientTable.getSelectionModel().getSelectedItem());
    }

    /* Button actions */

    @FXML
    private void addIngredient(){
        LOGGER.debug("Add ingredient action called.");
    }

    @FXML
    private void editIngredient(){
        LOGGER.debug("Edit ingredient action called.");
    }

    @FXML
    private void deleteIngredient(){
        LOGGER.debug("Delete ingredient action called.");
    }
}
