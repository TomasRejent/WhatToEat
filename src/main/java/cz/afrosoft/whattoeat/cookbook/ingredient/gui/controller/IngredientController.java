package cz.afrosoft.whattoeat.cookbook.ingredient.gui.controller;

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

import cz.afrosoft.whattoeat.cookbook.ingredient.gui.dialog.IngredientDialog;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.KeywordCell;
import cz.afrosoft.whattoeat.core.gui.table.LabeledCell;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for page {@link cz.afrosoft.whattoeat.core.gui.Page#INGREDIENTS}.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
    @Autowired
    private IngredientDialog dialog;

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
        unitColumn.setCellFactory(column -> new LabeledCell<>());
        priceColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Ingredient::getPrice, null));
        keywordColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Ingredient::getKeywords, Collections.emptySet()));
        keywordColumn.setCellFactory(column -> new KeywordCell<>());
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

    /**
     * Handler for add button. Brings up dialog for adding ingredient. If ingredient is added then table is updated.
     */
    @FXML
    private void addIngredient(){
        LOGGER.debug("Add ingredient action called.");
        dialog.addIngredient().ifPresent(
                ingredientUpdateObject -> ingredientTable.getItems().add(ingredientService.createOrUpdate(ingredientUpdateObject))
        );
    }

    /**
     * Handler for edit button. Brings up dialog for editing ingredient. If ingredient is edited then table is updated.
     */
    @FXML
    private void editIngredient(){
        LOGGER.debug("Edit ingredient action called.");
        getSelectedIngredient().ifPresent(//cookbook is selected
                ingredient -> dialog.editIngredient(ingredient).ifPresent(//edit is confirmed
                        ingredientUpdateObject -> Collections.replaceAll(ingredientTable.getItems(), ingredient, ingredientService.createOrUpdate(ingredientUpdateObject)) //table is updated
                )
        );
    }

    /**
     * Handler for delete button. Brings up confirmation dialog. If ingredient is deleted then table is updated.
     */
    @FXML
    private void deleteIngredient(){
        LOGGER.debug("Delete ingredient action called.");
        getSelectedIngredient().ifPresent(ingredient -> {
            if (DialogUtils.showConfirmDialog(
                    I18n.getText(DELETE_CONFIRM_TITLE_KEY), I18n.getText(DELETE_CONFIRM_MESSAGE_KEY, ingredient.getName()))
                    ) {
                ingredientService.delete(ingredient);
                ingredientTable.getItems().remove(ingredient);
            }
        });
    }
}
