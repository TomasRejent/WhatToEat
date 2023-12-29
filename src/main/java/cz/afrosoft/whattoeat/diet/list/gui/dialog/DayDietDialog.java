package cz.afrosoft.whattoeat.diet.list.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientFilter;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.*;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.suggestion.GenericSuggestionProvider;
import cz.afrosoft.whattoeat.core.gui.suggestion.NamedEntitySuggestionProvider;
import cz.afrosoft.whattoeat.core.gui.table.*;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;
import cz.afrosoft.whattoeat.diet.list.gui.table.RecipeLinkCell;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.model.RecipeDataForDayDietDialog;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealService;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.comparator.BooleanComparator;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/DayDietDialog.fxml")
public class DayDietDialog extends Dialog<List<MealUpdateObject>> {

    @FXML
    private TextField recipeField;
    @FXML
    private FloatField servingsField;
    @FXML
    private TextField ingredientField;
    @FXML
    private IntegerField amountField;
    @FXML
    private TableView<MealUpdateObject> mealTable;
    @FXML
    private TableColumn<MealUpdateObject, String> eatableColumn;
    @FXML
    private TableColumn<MealUpdateObject, Float> servingsColumn;
    @FXML
    private TableColumn<MealUpdateObject, Integer> amountColumn;
    @FXML
    private TableColumn<MealUpdateObject, Void> removeColumn;

    @FXML
    private TextField nameFilter;
    @FXML
    private MultiSelect<CookbookRef> cookbookFilter;
    @FXML
    private MultiSelect<RecipeType> typeFilter;
    @FXML
    private KeywordField keywordFilter;

    @FXML
    private TableView<RecipeDataForDayDietDialog> recipeTable;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, RecipeRef> nameColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, MealNutritionFacts> nutritionFactsColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> energyColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> fatColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> saturatedFatColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> carbohydrateColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> sugarColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> proteinColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> saltColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Float> fibreColumn;
    @FXML
    private TableColumn<RecipeDataForDayDietDialog, Collection<Keyword>> keywordColumn;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MealService mealService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private CookbookService cookbookService;
    @Autowired
    private NutritionFactsService nutritionFactsService;

    private final NamedEntitySuggestionProvider<Recipe> recipeSuggestionProvider = new NamedEntitySuggestionProvider<>();
    private final Property<Recipe> selectedRecipe = new SimpleObjectProperty<>();

    private final GenericSuggestionProvider<Ingredient> ingredientSuggestionProvider = new GenericSuggestionProvider<>(IngredientRef::getFullName);
    private final Property<Ingredient> selectedIngredient = new SimpleObjectProperty<>();

    @PostConstruct
    private void initialize() {
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setTitle(I18n.getText("cz.afrosoft.whattoeat.dietview.dialog.meal.title"));

        setupButtons();
        setupResultConverter();
        setupTableColumns();
        setupFields();
        setupFilter();
        setupListeners();
    }

    private void setupListeners(){
        recipeTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ENTER){
                pickRecipeFromTable();
                event.consume();
            }
        });
        recipeTable.setOnMouseClicked(event -> {
            if(event.getClickCount() > 1){
                pickRecipeFromTable();
            }
        });
    }

    private void pickRecipeFromTable(){
        RecipeDataForDayDietDialog selectedRecipe = recipeTable.getSelectionModel().getSelectedItem();
        if(selectedRecipe != null){
            Recipe recipe = selectedRecipe.getRecipe();
            pickRecipe(recipe);
            recipeField.setText(recipe.getName());
        }
    }

    /**
     * Setup filter fields.
     */
    private void setupFilter() {
        cookbookFilter.getItems().addAll(cookbookService.getAllCookbookRefs());
        typeFilter.getItems().addAll(RecipeType.values());
        cookbookFilter.setConverter(ComboBoxUtils.createStringConverter(cookbookFilter, CookbookRef::getName));
        ComboBoxUtils.initLabeledCheckComboBox(typeFilter);
        nameFilter.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                filterRecipes();
            }
        });
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);
        DialogUtils.translateButtons(this);
    }

    private void setupResultConverter() {
        this.setResultConverter((buttonType) -> {
            if (ButtonType.FINISH.equals(buttonType)) {
                return fillUpdateObject();
            } else {
                return null;
            }
        });
    }

    private void setupTableColumns() {
        eatableColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(
            mealUpdateObject -> mealUpdateObject.getRecipe().map(RecipeRef::getName).orElse(mealUpdateObject.getIngredient().map(IngredientRef::getFullName).orElse(StringUtils.EMPTY)))
        );
        servingsColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(mealUpdateObject -> mealUpdateObject.getServings().orElse(0f)));
        amountColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(mealUpdateObject -> mealUpdateObject.getAmount().orElse(0)));
        removeColumn.setCellFactory(param -> new RemoveCell<>(applicationContext.getBean(RemoveButton.class)));

        nameColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(RecipeDataForDayDietDialog::getRecipe));
        nutritionFactsColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(RecipeDataForDayDietDialog::getNutritionFacts));
        energyColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getEnergy()));
        fatColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getFat()));
        saturatedFatColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getSaturatedFat()));
        carbohydrateColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getCarbohydrate()));
        sugarColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getSugar()));
        proteinColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getProtein()));
        saltColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getSalt()));
        fibreColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getNutritionFacts().getFibre()));
        keywordColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(recipeDataForDayDietDialog -> recipeDataForDayDietDialog.getRecipe().getKeywords(), Collections.emptySet()));

        nameColumn.setCellFactory(param -> new RecipeLinkCell<>(applicationContext));
        nutritionFactsColumn.setCellFactory(param -> new MealNutritionFactsIconCell<>());
        nutritionFactsColumn.setComparator((firstNutritionFact, secondNutritionFact) -> BooleanComparator.TRUE_LOW.compare(firstNutritionFact.isNutritionFactMissing(), secondNutritionFact.isNutritionFactMissing()));
        energyColumn.setCellFactory(param -> new FloatCell<>(0));
        fatColumn.setCellFactory(param -> new FloatCell<>(1));
        saturatedFatColumn.setCellFactory(param -> new FloatCell<>(1));
        carbohydrateColumn.setCellFactory(param -> new FloatCell<>(1));
        sugarColumn.setCellFactory(param -> new FloatCell<>(1));
        proteinColumn.setCellFactory(param -> new FloatCell<>(1));
        saltColumn.setCellFactory(param -> new FloatCell<>(3));
        fibreColumn.setCellFactory(param -> new FloatCell<>(3));
        keywordColumn.setCellFactory(column -> new KeywordCell<>());
    }

    private void setupFields() {
        StringConverter<Recipe> recipeConverter = ComboBoxUtils.createAsymmetricStringConverter(Recipe::getName, string -> null);
        AutoCompletionBinding<Recipe> recipeBinding = TextFields.bindAutoCompletion(recipeField, recipeSuggestionProvider, recipeConverter);
        recipeBinding.setOnAutoCompleted(event -> pickRecipe(event.getCompletion()));

        recipeField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String recipeName = recipeField.getText();
                recipeService.findRecipeByName(recipeName).ifPresent(
                    this::pickRecipe
                );
            }
        });

        StringConverter<Ingredient> ingredientConverter = ComboBoxUtils.createAsymmetricStringConverter(Ingredient::getFullName, string -> null);
        AutoCompletionBinding<Ingredient> ingredientBinding = TextFields.bindAutoCompletion(ingredientField, ingredientSuggestionProvider, ingredientConverter);
        ingredientBinding.setOnAutoCompleted(event -> pickIngredient(event.getCompletion()));

        ingredientField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String ingredientName = ingredientField.getText();
                ingredientService.findByName(ingredientName).ifPresent(
                    this::pickIngredient
                );
            }
        });

        servingsField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.addMeal();
                recipeField.requestFocus();
                event.consume();
            }
        });

        amountField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.addMeal();
                ingredientField.requestFocus();
                event.consume();
            }
        });
    }

    private void addMeal(){
        float servings = servingsField.getFloatOrZero();
        int amount = amountField.getIntOrZero();

        Recipe selectedRecipeValue = selectedRecipe.getValue();
        if(selectedRecipeValue != null){
            amount = Optional.ofNullable(selectedRecipeValue.getDefaultServingWeight()).map(Math::round).orElse(0);
        }

        mealTable.getItems().add(
            mealService.getMealCreateObject()
                .setRecipe(selectedRecipeValue)
                .setServings(servings)
                .setAmount(amount)
                .setIngredient(selectedIngredient.getValue())
        );
        selectedRecipe.setValue(null);
        recipeField.setText(StringUtils.EMPTY);
        ingredientField.setText(StringUtils.EMPTY);
        servingsField.setText(StringUtils.EMPTY);
        amountField.setText(StringUtils.EMPTY);
    }

    private void pickRecipe(final Recipe recipe) {
        Validate.notNull(recipe);
        selectedIngredient.setValue(null);
        selectedRecipe.setValue(recipe);
        servingsField.requestFocus();
    }

    private void pickIngredient(final Ingredient ingredient){
        Validate.notNull(ingredient);
        if(!ingredient.isEdible() || !ingredient.isPurchasable()){
            DialogUtils.showInfoDialog(
                I18n.getText("cz.afrosoft.whattoeat.dayDietDialog.illegalIngredientHeader"),
                I18n.getText("cz.afrosoft.whattoeat.dayDietDialog.illegalIngredient")
            );
            return;
        }

        selectedRecipe.setValue(null);
        servingsField.setText(StringUtils.EMPTY);
        selectedIngredient.setValue(ingredient);
        amountField.requestFocus();
    }

    private List<MealUpdateObject> fillUpdateObject() {
        return mealTable.getItems();
    }

    private void clearDialog() {
        recipeField.setText(StringUtils.EMPTY);
        ingredientField.setText(StringUtils.EMPTY);
        servingsField.setText(StringUtils.EMPTY);
        amountField.setText(StringUtils.EMPTY);
        mealTable.getItems().clear();
    }

    private void prefillDialog(final List<Meal> meals) {
        mealTable.getItems().addAll(ConverterUtil.convertToList(meals, mealService::getMealUpdateObject));
    }

    private void setupDynamicFieldOptions() {
        recipeSuggestionProvider.setPossibleSuggestions(recipeService.getAllRecipes());
        ingredientSuggestionProvider.setPossibleSuggestions(ingredientService.getFilteredIngredients(
            new IngredientFilter.Builder()
                .setEdible(true)
                .setPurchasable(true)
                .build()
        ));
    }

    public Optional<List<MealUpdateObject>> editMeals(final List<Meal> meals) {
        clearDialog();
        setupDynamicFieldOptions();
        prefillDialog(meals);
        return showAndWait();
    }

    @FXML
    public void filterRecipes() {
        RecipeFilter filter = new RecipeFilter.Builder()
                .setName(nameFilter.getText())
                .setType(typeFilter.getValues())
                .setCookbooks(cookbookFilter.getValues())
                .setKeywords(keywordFilter.getSelectedKeywords())
                .build();
        recipeTable.getItems().clear();
        recipeTable.getItems().addAll(nutritionFactsService.toDayDietView(recipeService.getFilteredRecipes(filter)));
    }

    @FXML
    public void clearRecipe() {
        nameFilter.setText(StringUtils.EMPTY);
        typeFilter.getCheckModel().clearChecks();
        cookbookFilter.getCheckModel().clearChecks();
        recipeTable.getItems().clear();
        recipeTable.getItems().addAll(nutritionFactsService.toDayDietView(recipeService.getAllRecipes()));
    }
}
