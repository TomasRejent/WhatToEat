package cz.afrosoft.whattoeat.diet.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeOld;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.controller.suggestion.FullWordSuggestionProvider;
import cz.afrosoft.whattoeat.diet.gui.view.MealView;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.ServiceHolder;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.Optional;

/**
 * Represents dialog for choosing / editing meal in diet. This includes recipe and servings.
 * @author Tomas Rejent
 */
public class MealDialog extends Dialog<MealView>{

    private static final String I18N_TITLE = "cz.afrosoft.whattoeat.dietview.dialog.meal.title";
    private static final String I18N_RECIPE = "cz.afrosoft.whattoeat.dietview.dialog.meal.recipe";
    private static final String I18N_SERVINGS = "cz.afrosoft.whattoeat.dietview.dialog.meal.servings";

    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;

    private final VBox verticalBox = new VBox();
    private final HBox firstRow = new HBox();
    private final HBox secondRow = new HBox();
    private final Label recipeLabel = new Label();
    private final TextField recipeField = new TextField();
    private final Label servingsLabel = new Label();
    private final TextField servingsField = new TextField();

    private final RecipeService recipeService = ServiceHolder.getRecipeService();

    private MealView editedMeal;

    public MealDialog() {
        this.setTitle(I18n.getText(I18N_TITLE));
        this.setResizable(true);
        this.initModality(Modality.APPLICATION_MODAL);
        this.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        setupLayout();
        setupRecipeSuggestion();
        setupResultConverter();
    }

    public Optional<MealView> showMealDialog(MealView meal){
        if(meal != null){
            recipeField.setText(meal.getRecipeName());
            servingsField.setText(String.valueOf(meal.getServings()));
            editedMeal = meal;
        }

        return showAndWait();
    }

    private void setupLayout(){
        recipeLabel.setText(I18n.getText(I18N_RECIPE));
        servingsLabel.setText(I18n.getText(I18N_SERVINGS));

        firstRow.getChildren().addAll(recipeLabel, recipeField);
        secondRow.getChildren().addAll(servingsLabel, servingsField);

        verticalBox.getChildren().addAll(firstRow, secondRow);
        verticalBox.setFillWidth(true);
        verticalBox.setPadding(Insets.EMPTY);

        this.getDialogPane().setContent(verticalBox);
    }

    private void setupRecipeSuggestion(){
        AutoCompletionBinding<String> autoCompletion = TextFields.bindAutoCompletion(recipeField, new FullWordSuggestionProvider(recipeService.getAllRecipeNames()));
        autoCompletion.setOnAutoCompleted((completionEvent -> servingsField.requestFocus()));

        recipeField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    keyEvent.consume();
                    break;
                default:
                    break;
            }
        });
    }

    private void setupResultConverter(){
        this.setResultConverter((buttonType) -> {
            if(ButtonType.FINISH.equals(buttonType)){
                return createMealView();
            }else{
                return null;
            }
        });
    }

    private MealView createMealView(){
        String recipeName = recipeField.getText();
        if(StringUtils.isBlank(recipeName)){
            return null;
        }

        RecipeOld recipe = recipeService.getRecipeByName(recipeName);
        if(recipe == null){
            return null;
        }

        String servingsString = servingsField.getText();
        if(!StringUtils.isNumeric(servingsString)){
            return null;
        }

        editedMeal.setRecipeName(recipeName);
        editedMeal.setRecipeKey(recipe.getKey());
        editedMeal.setServings(Integer.parseInt(servingsString));
        return editedMeal;
    }
}
