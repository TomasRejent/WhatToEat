/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.FloatFiled;
import cz.afrosoft.whattoeat.core.gui.component.IngredientQuantityTable;
import cz.afrosoft.whattoeat.core.gui.component.KeywordField;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *  Dialog for viewing of recipes. This dialog is not modal, so multiple recipes can be viewed at once. This is useful
 *  for cooking meals from multiple recipes at once or for comparing recipes.
 *
 *  This dialog should be created by spring {@link org.springframework.context.ApplicationContext#getBean(Class)} so all
 *  dependencies are autowired.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecipeViewDialog extends CustomDialog<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeViewDialog.class);
    private static final String DIALOG_FXML = "/fxml/RecipeViewDialog.fxml";

    private static final double TITLED_PANE_HEADER_HEIGHT = 30;
    private static final double INGREDIENT_TAB_EXPANDED_BASE_HEIGHT = 80;
    private static final double TABLE_ROW_HEIGHT = 29;
    private static final float DEFAULT_SERVINGS = 1;

    @FXML
    private VBox dialogContainer;
    @FXML
    private TitledPane generalPane;
    @FXML
    private GridPane generalLayout;
    @FXML
    private TitledPane ingredientPane;
    @FXML
    private TitledPane preparationPane;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Label typeLabel;
    @FXML
    private Label tasteLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private TextArea preparation;
    @FXML
    private KeywordField keywordField;
    @FXML
    private FloatFiled servingsField;
    @FXML
    private Label priceLabel;
    @FXML
    private IngredientQuantityTable ingredientQuantityTable;

    @Autowired
    private RecipeService recipeService;

    /**
     * Stores recipe ingredients loaded from recipe so changing of servings does not need to reload them.
     */
    private Collection<RecipeIngredient> recipeIngredients;

    public RecipeViewDialog() {
        super(DIALOG_FXML);
        setResizable(true);
        initModality(Modality.NONE);

        setupButtons();
        setupResultConverter();
        setupServingsField();
        setupSplitPane();
    }

    private void setupResultConverter() {
        this.setResultConverter(buttonType -> null);
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        translateButtons();
    }

    private void setupServingsField() {
        servingsField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                float servings = servingsField.getFloatOrZero();
                if (servings > 0) {
                    ingredientQuantityTable.setRecipeIngredientsQuantities(servings, recipeIngredients);
                    updatePriceLabel();
                }
                event.consume();
            }
        });
    }

    private void setupSplitPane() {
        ingredientPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPosition(0, computeDividerPosition());
        });
        preparationPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPosition(0, computeDividerPosition());
        });
    }

    private double computeDividerPosition() {
        if (ingredientPane.isExpanded() && !preparationPane.isExpanded()) {
            return 1;
        } else if (!ingredientPane.isExpanded()) {
            return 0;
        } else {
            return 0.5;
        }
    }

    private void updatePriceLabel() {
        priceLabel.setText(String.valueOf(ingredientQuantityTable.getTotalPrice()));
    }

    /**
     * Shows dialog with specified recipe.
     *
     * @param recipe (NotNull) Recipe to show.
     */
    public void showRecipe(final Recipe recipe) {
        Validate.notNull(recipe);

        setTitle(recipe.getName());
        prefillDialog(recipe);
        showAndWait();
    }

    private void prefillDialog(final Recipe recipe) {
        Validate.notNull(recipe);

        typeLabel.setText(StringUtils.join(recipe.getRecipeTypes().stream().map(recipeType -> I18n.getText(recipeType.getLabelKey())).collect(Collectors.toSet()), ", "));
        tasteLabel.setText(I18n.getText(recipe.getTaste().getLabelKey()));
        timeLabel.setText(I18n.getText(recipe.getTotalPreparationTime().getLabelKey()));
        ratingLabel.setText(String.valueOf(recipe.getRating()));
        keywordField.setSelectedKeywords(recipe.getKeywords());
        preparation.setText(recipe.getPreparation());
        servingsField.setFloat(DEFAULT_SERVINGS);
        recipeIngredients = recipeService.loadRecipeIngredients(recipe.getIngredients());
        ingredientQuantityTable.addRecipeIngredients(DEFAULT_SERVINGS, recipeIngredients);
        updatePriceLabel();
    }
}
