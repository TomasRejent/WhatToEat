package cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.ingredient.gui.dialog.IngredientDialog;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.recipe.gui.table.IngredientQuantityCell;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientUpdateObject;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeUpdateObject;
import cz.afrosoft.whattoeat.core.gui.FillUtils;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.*;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import cz.afrosoft.whattoeat.core.gui.list.ListBinding;
import cz.afrosoft.whattoeat.core.gui.suggestion.ComboBoxSuggestion;
import cz.afrosoft.whattoeat.core.gui.suggestion.NamedEntitySuggestionProvider;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.KeywordCell;
import cz.afrosoft.whattoeat.core.gui.table.RemoveCell;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
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
    private MultiSelect<CookbookRef> cookbooksField;
    @FXML
    private MultiSelect<RecipeType> recipeTypeField;
    @FXML
    private ComboBox<Taste> tasteField;
    @FXML
    private DurationField preparationTimeField;
    @FXML
    private DurationField cookingTimeField;
    @FXML
    private Rating ratingField;
    @FXML
    private FloatFiled ingredientServingsField;
    @FXML
    private TextField ingredientNameField;
    @FXML
    private FloatFiled ingredientQuantityField;
    @FXML
    private Label unitLabel;
    @FXML
    private AddButton ingredientAddButton;
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
    @FXML
    private TabPane recipeTabs;
    @FXML
    private Tab sideDishTab;
    @FXML
    private ComboBox<RecipeRef> sideDishField;
    @FXML
    private ListView<RecipeRef> sideDishList;

    private Button finishButton;
    private Button nextButton;
    private Button previousButton;

    @Autowired
    private IngredientDialog ingredientDialog;
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

    private Property<Ingredient> selectedIngredient = new SimpleObjectProperty<>();

    /**
     * Creates new dialog. This constructor must be used only by Spring, because dependencies must be autowired to created instance.
     */
    public RecipeAddDialog() {
        super(DIALOG_FXML);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);

        setupButtons();
        setupResultConverter();
        setupFields();
        setupStaticFieldOptions();
        setupIngredientTable();
        setupFocusTraversal();
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

    private void setupFocusTraversal() {
        EventHandler<KeyEvent> tabHandler = event -> {
            if (event.getCode() == KeyCode.TAB) {
                nextButton.fire();
                event.consume();
            }
        };

        keywordField.addEventFilter(KeyEvent.KEY_PRESSED, tabHandler);
        ingredientNameField.addEventFilter(KeyEvent.KEY_PRESSED, tabHandler);
        preparationField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (!nextButton.isDisable() && event.getCode() == KeyCode.TAB && event.isControlDown()) {
                nextButton.fire();
                event.consume();
            } else if (!finishButton.isDisable() && event.getCode() == KeyCode.ENTER && event.isControlDown()) {
                finishButton.fire();
            }
        });
    }

    private void setupFields() {
        /* Focus name field after dialog is shown. Must be in runLater, because onShown event is actually fired before dialog is shown
            because it is blocking call. */
        setOnShown(event -> Platform.runLater(() -> {
            nameField.requestFocus();
            getDialogPane().getScene().focusOwnerProperty().addListener((observable, oldValue, newValue) -> {
                LOGGER.debug("Focused node {} -> {}", oldValue, newValue);
            });
        }));

        ComboBoxUtils.initLabeledComboBox(tasteField);
        ComboBoxUtils.initLabeledCheckComboBox(recipeTypeField);
        cookbooksField.setConverter(ComboBoxUtils.createAsymmetricStringConverter(CookbookRef::getName, string -> null));

        //setup key controls for rating field
        ratingField.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP || code == KeyCode.RIGHT) {
                ratingField.setRating(Math.min(ratingField.getMax(), ratingField.getRating() + 1));
            } else if (code == KeyCode.DOWN || code == KeyCode.LEFT) {
                ratingField.setRating(Math.max(0, ratingField.getRating() - 1));
            }
        });

        StringConverter<Ingredient> ingredientConverter = ComboBoxUtils.createAsymmetricStringConverter(Ingredient::getName, string -> null);
        AutoCompletionBinding<Ingredient> ingredientBinding = TextFields.bindAutoCompletion(ingredientNameField, ingredientSuggestionProvider, ingredientConverter);
        ingredientBinding.setOnAutoCompleted(event -> pickIngredient(event.getCompletion()));

        ingredientNameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String ingredientName = ingredientNameField.getText();
                Optional<Ingredient> ingredient = ingredientService.findByName(ingredientName);
                if (ingredient.isPresent()) {
                    pickIngredient(ingredient.get());
                } else {
                    Platform.runLater(this::addIngredient);
                }
                event.consume();
            }
        });
        ingredientNameField.setOnKeyReleased(event -> {
            String ingredientName = ingredientNameField.getText();
            ingredientAddButton.setDisable(StringUtils.isEmpty(ingredientName) || ingredientService.existByName(ingredientName));
        });

        //listener which enables quantity field only when ingredient is selected
        selectedIngredient.addListener((observable, oldValue, newValue) -> ingredientQuantityField.setDisable(newValue == null));
        //process enter key on quantity field, which adds ingredient to table
        ingredientQuantityField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                float servings = ingredientServingsField.getFloatOrZero();
                float divisionCoefficient = servings == 0 ? 1f : servings;
                ingredientTable.getItems().add(
                        recipeService.getRecipeIngredientCreateObject()
                                .setQuantity(ingredientQuantityField.getFloat() / divisionCoefficient)
                                .setIngredient(selectedIngredient.getValue())
                );
                selectedIngredient.setValue(null);
                ingredientQuantityField.setText(StringUtils.EMPTY);
                ingredientNameField.setText(StringUtils.EMPTY);
                ingredientNameField.requestFocus();
                event.consume();
            }
        });

        //hides side dishes when main dish is not selected in recipe types
        recipeTabs.getTabs().remove(sideDishTab);
        recipeTypeField.getCheckModel().getCheckedItems().addListener((ListChangeListener<RecipeType>) change -> {
            if (change.getList().contains(RecipeType.MAIN_DISH)) {
                if (!recipeTabs.getTabs().contains(sideDishTab)) {
                    recipeTabs.getTabs().add(sideDishTab);
                }
            } else {
                recipeTabs.getTabs().remove(sideDishTab);
            }
        });
        //setup side dish suggestion
        ComboBoxSuggestion.initSuggestion(sideDishField, RecipeRef::getName);
        ListBinding.bindToComboBox(sideDishList, sideDishField, RecipeRef::getName);
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.NEXT, ButtonType.PREVIOUS, ButtonType.CANCEL);
        finishButton = (Button) getDialogPane().lookupButton(ButtonType.FINISH);
        nextButton = (Button) getDialogPane().lookupButton(ButtonType.NEXT);
        previousButton = (Button) getDialogPane().lookupButton(ButtonType.PREVIOUS);
        translateButtons();

        recipeTabs.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = newValue.intValue();
            updateButtons(selectedIndex);
            Platform.runLater(() -> updateFocus(selectedIndex));
        });

        nextButton.addEventFilter(ActionEvent.ACTION, event -> {
            recipeTabs.getSelectionModel().selectNext();
            event.consume();
        });
        previousButton.addEventFilter(ActionEvent.ACTION, event -> {
            recipeTabs.getSelectionModel().selectPrevious();
            event.consume();
        });
    }

    private void updateButtons(final int selectedIndex) {
        boolean first = selectedIndex == 0;
        boolean last = selectedIndex == recipeTabs.getTabs().size() - 1;
        nextButton.setDisable(last);
        previousButton.setDisable(first);
        finishButton.setDisable(!(recipeUpdateObject.getId().isPresent() || last));
    }

    private void updateFocus(final int selectedIndex) {
        if (selectedIndex == 0) {
            nameField.requestFocus();
        } else if (selectedIndex == 1) {
            ingredientServingsField.requestFocus();
        } else if (selectedIndex == 2) {
            preparationField.requestFocus();
        } else if (selectedIndex == 3) {
            sideDishField.requestFocus();
        }
    }

    private void setupIngredientTable() {
        ingredientTable.setEditable(true);
        ingredientNameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(riuo -> riuo.getIngredient().map(Ingredient::getName).orElse(StringUtils.EMPTY)));
        ingredientQuantityColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(riuo -> riuo.getQuantity().orElse(null), 0F));
        ingredientQuantityColumn.setCellFactory(param -> new IngredientQuantityCell(() -> ingredientServingsField.getFloat()));
        ingredientQuantityColumn.setEditable(true);
        ingredientQuantityColumn.setOnEditCommit(event -> event.getRowValue().setQuantity(event.getNewValue()));
        ingredientKeywordColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(riuo -> riuo.getIngredient().map(Ingredient::getKeywords).orElse(Collections
                .emptySet()), Collections.emptySet()));
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
        recipeUpdateObject.setSideDishes(new HashSet<>(sideDishList.getItems()));
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
        //this must be before clear dialog, because recipeUpdateObject is used in buttons setup
        recipeUpdateObject = recipeService.getCreateObject();
        clearDialog();
        setupDynamicFieldOptions();
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
        //this must be before prefill dialog, because recipeUpdateObject is used in buttons setup
        recipeUpdateObject = recipeService.getUpdateObject(recipe);
        setupDynamicFieldOptions();
        prefillDialog(recipe);
        return showAndWait();
    }

    /**
     * Clears all dialog fields.
     */
    private void clearDialog() {
        resetDialog();
        nameField.setText(StringUtils.EMPTY);
        preparationField.setText(StringUtils.EMPTY);
        ratingField.setRating(5);
        recipeTypeField.getCheckModel().clearChecks();
        tasteField.getSelectionModel().clearSelection();
        preparationTimeField.setText(StringUtils.EMPTY);
        cookingTimeField.setText(StringUtils.EMPTY);
        ListBinding.fillBoundedList(sideDishList, sideDishField, recipeService.getAllSideDishRefs(), Collections.emptySet());
        ingredientTable.getItems().clear();
        cookbooksField.getCheckModel().clearChecks();
        keywordField.clearSelectedKeywords();
    }

    /**
     * Prefill dialog fields with values from specified recipe.
     *
     * @param recipe (NotNull)
     */
    private void prefillDialog(final Recipe recipe) {
        Validate.notNull(recipe);
        resetDialog();
        nameField.setText(recipe.getName());
        FillUtils.checkItems(cookbooksField, recipe.getCookbooks());
        preparationField.setText(recipe.getPreparation());
        ratingField.setRating(recipe.getRating());
        FillUtils.checkItems(recipeTypeField, recipe.getRecipeTypes());
        tasteField.getSelectionModel().select(recipe.getTaste());
        preparationTimeField.setDuration(recipe.getIngredientPreparationTime());
        cookingTimeField.setDuration(recipe.getCookingTime());
        ListBinding.fillBoundedList(sideDishList, sideDishField, recipeService.getAllSideDishRefs(), recipe.getSideDishes());
        ingredientTable.getItems().clear();
        ingredientTable.getItems().addAll(recipeService.toUpdateObjects(recipe.getIngredients()));
        keywordField.setSelectedKeywords(recipe.getKeywords());
    }

    /**
     * Reset dialog to its initial state. This is common for clearing and prefilling dialog.
     * It sets values which does not depend on recipe, like default selected tab or ingredient fields excluding table.
     */
    private void resetDialog() {
        ingredientServingsField.setFloat(1f);
        recipeTabs.getSelectionModel().select(0);
        ingredientNameField.setText(StringUtils.EMPTY);
        ingredientQuantityField.setText(StringUtils.EMPTY);
        selectedIngredient.setValue(null);
        ingredientQuantityField.setDisable(true);
        ingredientAddButton.setDisable(true);
        updateButtons(0);
    }

    /**
     * Pick specified ingredient in ingredient tab so its quantity can be specified afterwards.
     *
     * @param ingredient (NotNull)
     */
    private void pickIngredient(final Ingredient ingredient) {
        Validate.notNull(ingredient);
        selectedIngredient.setValue(ingredient);
        unitLabel.setText(I18n.getText(selectedIngredient.getValue().getIngredientUnit().getLabelKey()));
        ingredientQuantityField.requestFocus();
    }

    /**
     * Event handler for button for adding of new ingredients.
     */
    @FXML
    private void addIngredient() {
        LOGGER.debug("Add ingredient action triggered.");
        ingredientDialog.addIngredient(ingredientNameField.getText())
                .ifPresent(ingredientUpdateObject -> {
                    Ingredient newIngredient = ingredientService.createOrUpdate(ingredientUpdateObject);
                    pickIngredient(newIngredient);
                });
    }
}
