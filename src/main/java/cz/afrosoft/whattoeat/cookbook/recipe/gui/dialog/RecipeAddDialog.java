package cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Rating;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientUpdateObject;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeUpdateObject;
import cz.afrosoft.whattoeat.core.gui.FillUtils;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.DurationField;
import cz.afrosoft.whattoeat.core.gui.component.FloatFiled;
import cz.afrosoft.whattoeat.core.gui.component.KeywordField;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import cz.afrosoft.whattoeat.core.gui.suggestion.NamedEntitySuggestionProvider;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.KeywordCell;
import cz.afrosoft.whattoeat.core.gui.table.RemoveCell;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.util.StringConverter;

/**
 * Dialog for adding and editing of recipes. This dialog also allows to change relation between recipe and cookbooks.
 * <p>
 * This dialog must be modal because controller using it has only one instance of this dialog.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecipeAddDialog extends CustomDialog<RecipeUpdateObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeAddDialog.class);

    private static final String DIALOG_FXML = "/fxml/RecipeAddDialog.fxml";

    /**
     * Title message displayed when dialog is in add mode.
     */
    private static final String ADD_TITLE_KEY = "cz.afrosoft.whattoeat.recipe.add.title";
    /**
     * Title message displayed when dialog is in edit mode.
     */
    private static final String EDIT_TITLE_KEY = "cz.afrosoft.whattoeat.recipe.edit.title";

    private static final int MAX_RATING = 10;

    @FXML
    private GridPane dialogContainer;

    @FXML
    private TextField nameField;
    @FXML
    private CheckComboBox<CookbookRef> cookbooksField;
    @FXML
    private CheckComboBox<RecipeType> recipeTypeField;
    @FXML
    private ComboBox<Taste> tasteField;
    @FXML
    private DurationField preparationTimeField;
    @FXML
    private DurationField cookingTimeField;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Rating ratingField;
    @FXML
    private TextField ingredientNameField;
    @FXML
    private FloatFiled ingredientQuantityField;
    @FXML
    private Label unitLabel;
    @FXML
    private TableView<RecipeIngredientUpdateObject> ingredientTable;
    @FXML
    private TableColumn<RecipeIngredientUpdateObject, String> ingredientNameColumn;
    @FXML
    private TableColumn<RecipeIngredientUpdateObject, Float> ingredientQuantityColumn;
    @FXML
    private TableColumn<RecipeIngredientUpdateObject, Collection<Keyword>> ingredientKeywordColumn;
    @FXML
    private TableColumn<RecipeIngredientUpdateObject, Void> ingredientRemoveColumn;
    @FXML
    private TextArea preparationField;
    @FXML
    private KeywordField keywordField;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CookbookService cookbookService;
    @Autowired
    private IngredientService ingredientService;

    private final NamedEntitySuggestionProvider<Ingredient> ingredientSuggestionProvider = new NamedEntitySuggestionProvider<>();

    /**
     * Holds createOrUpdate object when creating or editing recipe.
     */
    private RecipeUpdateObject recipeUpdateObject;

    private Ingredient selectedIngredient;

    /**
     * Creates new dialog. This constructor must be used only by Spring, because dependencies must be autowired to created instance.
     */
    public RecipeAddDialog() {
        super(DIALOG_FXML);
        getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setupResultConverter();
        setupFields();
        setupStaticFieldOptions();
        setupIngredientTable();
    }

    /**
     * Creates and sets result converter which handles dialog button events.
     */
    private void setupResultConverter() {
        this.setResultConverter((buttonType) -> {
            if (ButtonType.FINISH.equals(buttonType)) {
                return fillUpdateObject();
            } else {
                return null;
            }
        });
    }

    private void setupFields() {
        ComboBoxUtils.initLabeledComboBox(tasteField);
        ComboBoxUtils.initLabeledCheckComboBox(recipeTypeField);
        cookbooksField.setConverter(ComboBoxUtils.createAsymmetricStringConverter(CookbookRef::getName, string -> null));

        StringConverter<Ingredient> ingredientConverter = ComboBoxUtils.createAsymmetricStringConverter(Ingredient::getName, string -> null);
        AutoCompletionBinding<Ingredient> ingredientBinding = TextFields.bindAutoCompletion(ingredientNameField, ingredientSuggestionProvider, ingredientConverter);
        ingredientBinding.setOnAutoCompleted(event -> {
            selectedIngredient = event.getCompletion();
            unitLabel.setText(I18n.getText(selectedIngredient.getIngredientUnit().getLabelKey()));
            ingredientQuantityField.requestFocus();
        });

        ingredientQuantityField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ingredientTable.getItems().add(
                        recipeService.getRecipeIngredientCreateObject()
                                .setQuantity(ingredientQuantityField.getFloat())
                                .setIngredient(selectedIngredient)
                );
                selectedIngredient = null;
                ingredientQuantityField.setText(StringUtils.EMPTY);
                ingredientNameField.setText(StringUtils.EMPTY);
                ingredientNameField.requestFocus();
                event.consume();
            }
        });
    }

    private void setupIngredientTable() {
        ingredientNameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(riuo -> riuo.getIngredient().map(Ingredient::getName).orElse(StringUtils.EMPTY)));
        ingredientQuantityColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(riuo -> riuo.getQuantity().orElse(null), 0F));
        ingredientKeywordColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(riuo -> riuo.getIngredient().map(Ingredient::getKeywords).orElse(Collections.emptySet()), Collections.emptySet()));
        ingredientKeywordColumn.setCellFactory(param -> new KeywordCell<>());
        ingredientRemoveColumn.setCellFactory(param -> new RemoveCell<>());
    }

    private void setupStaticFieldOptions() {
        recipeTypeField.getItems().addAll(RecipeType.values());
        tasteField.getItems().addAll(Taste.values());
        ratingField.setMax(MAX_RATING);
    }

    private void setupDynamicFieldOptions() {
        cookbooksField.getItems().clear();
        cookbooksField.getItems().addAll(cookbookService.getAllCookbookRefs());
        ingredientSuggestionProvider.setPossibleSuggestions(ingredientService.getAllIngredients());
    }

    /**
     * Fills data from fields to createOrUpdate object. Precondition of this method is that {@link #recipeUpdateObject} is not null.
     *
     * @return (NotNull)
     * @throws IllegalStateException If createOrUpdate object does not exist.
     */
    private RecipeUpdateObject fillUpdateObject() {
        if (recipeUpdateObject == null) {
            throw new IllegalStateException("Cookbook createOrUpdate object cannot be null.");
        }

        recipeUpdateObject.setName(nameField.getText());
        recipeUpdateObject.setPreparation(preparationField.getText());
        recipeUpdateObject.setRating((int) ratingField.getRating());
        recipeUpdateObject.setRecipeTypes(new HashSet<>(recipeTypeField.getCheckModel().getCheckedItems()));
        recipeUpdateObject.setTaste(tasteField.getValue());
        recipeUpdateObject.setIngredientPreparationTime(preparationTimeField.getDuration());
        recipeUpdateObject.setCookingTime(cookingTimeField.getDuration());
        recipeUpdateObject.setIngredients(new HashSet<>(ingredientTable.getItems()));
        //TODO add side dishes
        recipeUpdateObject.setCookbooks(new HashSet<>(cookbooksField.getCheckModel().getCheckedItems()));
        recipeUpdateObject.setKeywords(keywordField.getSelectedKeywords());

        return recipeUpdateObject;
    }

    /**
     * Shows dialog for adding recipe. This is blocking call. It waits until user close dialog.
     *
     * @return (NotNull) Empty optional if user cancels dialog. Optional with recipe createOrUpdate object if user submit dialog.
     */
    public Optional<RecipeUpdateObject> addRecipe() {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        setupDynamicFieldOptions();
        recipeUpdateObject = recipeService.getCreateObject();
        return showAndWait();
    }

    /**
     * Shows dialog for editing recipe. This is blocking call. It waits until user close dialog.
     *
     * @param recipe (NotNull) Recipe to edit.
     * @return (NotNull) Empty optional if user cancels dialog. Optional with recipe createOrUpdate object if user submit dialog.
     */
    public Optional<RecipeUpdateObject> editRecipe(final Recipe recipe) {
        Validate.notNull(recipe);
        setTitle(I18n.getText(EDIT_TITLE_KEY));
        setupDynamicFieldOptions();
        prefillDialog(recipe);
        recipeUpdateObject = recipeService.getUpdateObject(recipe);
        return showAndWait();
    }

    /**
     * Clears all dialog fields.
     */
    private void clearDialog() {
        nameField.setText(StringUtils.EMPTY);
        preparationField.setText(StringUtils.EMPTY);
        ratingField.setRating(5);
        recipeTypeField.getCheckModel().clearChecks();
        tasteField.getSelectionModel().clearSelection();
        preparationTimeField.setText(StringUtils.EMPTY);
        cookingTimeField.setText(StringUtils.EMPTY);
        //TODO side dishes
        ingredientTable.getItems().clear();
        ingredientNameField.setText(StringUtils.EMPTY);
        ingredientQuantityField.setText(StringUtils.EMPTY);
        cookbooksField.getCheckModel().clearChecks();
        keywordField.clearSelectedKeywords();
    }

    private void prefillDialog(final Recipe recipe) {
        Validate.notNull(recipe);
        nameField.setText(recipe.getName());
        preparationField.setText(recipe.getPreparation());
        ratingField.setRating(recipe.getRating());
        FillUtils.checkItems(recipeTypeField, recipe.getRecipeTypes());
        tasteField.getSelectionModel().select(recipe.getTaste());
        preparationTimeField.setDuration(recipe.getIngredientPreparationTime());
        cookingTimeField.setDuration(recipe.getCookingTime());
        //TODO side dishes
        ingredientTable.getItems().clear();
        ingredientTable.getItems().addAll(recipeService.toUpdateObjects(recipe.getIngredients()));
        FillUtils.checkItems(cookbooksField, recipe.getCookbooks());
        keywordField.setSelectedKeywords(recipe.getKeywords());
    }
}
