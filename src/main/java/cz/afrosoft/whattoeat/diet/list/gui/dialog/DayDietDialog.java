package cz.afrosoft.whattoeat.diet.list.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.IntegerField;
import cz.afrosoft.whattoeat.core.gui.component.RemoveButton;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.suggestion.NamedEntitySuggestionProvider;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.RemoveCell;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
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

import javax.annotation.PostConstruct;
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
    private IntegerField servingsField;
    @FXML
    private TableView<MealUpdateObject> mealTable;
    @FXML
    private TableColumn<MealUpdateObject, String> recipeColumn;
    @FXML
    private TableColumn<MealUpdateObject, Integer> servingsColumn;
    @FXML
    private TableColumn<MealUpdateObject, Void> removeColumn;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MealService mealService;
    @Autowired
    private RecipeService recipeService;

    private final NamedEntitySuggestionProvider<Recipe> recipeSuggestionProvider = new NamedEntitySuggestionProvider<>();
    private final Property<Recipe> selectedRecipe = new SimpleObjectProperty<>();

    @PostConstruct
    private void initialize() {
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);

        setupButtons();
        setupResultConverter();
        setupTableColumns();
        setupFields();
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
        recipeColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(mealUpdateObject -> mealUpdateObject.getRecipe().map(RecipeRef::getName).orElse(StringUtils.EMPTY)));
        servingsColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(mealUpdateObject -> mealUpdateObject.getServings().orElse(0)));
        removeColumn.setCellFactory(param -> new RemoveCell<>(applicationContext.getBean(RemoveButton.class)));
    }

    private void setupFields(){
        StringConverter<Recipe> recipeConverter = ComboBoxUtils.createAsymmetricStringConverter(Recipe::getName, string -> null);
        AutoCompletionBinding<Recipe> recipeBinding = TextFields.bindAutoCompletion(recipeField, recipeSuggestionProvider, recipeConverter);
        recipeBinding.setOnAutoCompleted(event -> pickRecipe(event.getCompletion()));

        recipeField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                String recipeName = recipeField.getText();
                recipeService.findRecipeByName(recipeName).ifPresent(
                        this::pickRecipe
                );
            }
        });

        servingsField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ENTER){
                int servings = servingsField.getIntOrZero();

                mealTable.getItems().add(
                        mealService.getMealCreateObject()
                        .setRecipe(selectedRecipe.getValue())
                        .setServings(servings)
                );
                selectedRecipe.setValue(null);
                recipeField.setText(StringUtils.EMPTY);
                servingsField.setText(StringUtils.EMPTY);
                recipeField.requestFocus();
                event.consume();
            }
        });
    }

    private void pickRecipe(final Recipe recipe){
        Validate.notNull(recipe);
        selectedRecipe.setValue(recipe);
        servingsField.requestFocus();
    }

    private List<MealUpdateObject> fillUpdateObject() {
        return mealTable.getItems();
    }

    private void clearDialog(){
        recipeField.setText(StringUtils.EMPTY);
        servingsField.setText(StringUtils.EMPTY);
    }

    private void setupDynamicFieldOptions(){
        recipeSuggestionProvider.setPossibleSuggestions(recipeService.getAllRecipes());
    }

    public Optional<List<MealUpdateObject>> editMeals(List<Meal> meals) {
        clearDialog();
        setupDynamicFieldOptions();
        return showAndWait();
    }
}
