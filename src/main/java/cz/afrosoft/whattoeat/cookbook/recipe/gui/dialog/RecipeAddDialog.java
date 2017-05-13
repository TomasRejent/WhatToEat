/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.*;
import cz.afrosoft.whattoeat.core.ServiceHolder;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientInfoService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.controller.suggestion.FullWordSuggestionProvider;
import cz.afrosoft.whattoeat.core.gui.KeywordLabelFactory;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.validator.RecipeValidator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PrefixSelectionChoiceBox;
import org.controlsfx.control.Rating;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Dialog for adding recipes.
 * @author Tomas Rejent
 */
public class RecipeAddDialog extends Dialog<Recipe>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeAddDialog.class);

    private static final String I18N_NAME = "cz.afrosoft.whattoeat.recipes.add.name";
    private static final String I18N_TYPE = "cz.afrosoft.whattoeat.recipes.add.type";
    private static final String I18N_TASTE = "cz.afrosoft.whattoeat.recipes.add.taste";
    private static final String I18N_TIME = "cz.afrosoft.whattoeat.recipes.add.time";
    private static final String I18N_RATING = "cz.afrosoft.whattoeat.recipes.add.rating";
    private static final String I18N_KEYWORDS = "cz.afrosoft.whattoeat.recipes.add.keywords";
    private static final String I18N_INGREDIENTS = "cz.afrosoft.whattoeat.recipes.add.ingredients";
    private static final String I18N_INGREDIENTS_NAME = "cz.afrosoft.whattoeat.recipes.add.ingredients.name";
    private static final String I18N_INGREDIENTS_QUANTITY = "cz.afrosoft.whattoeat.recipes.add.ingredients.quantity";
    private static final String I18N_PREPARATION = "cz.afrosoft.whattoeat.recipes.add.preparation";
    private static final String I18N_SIDE_DISHES = "cz.afrosoft.whattoeat.recipes.add.sideDishes";
    private static final String I18N_SIDE_DISH_NAME = "cz.afrosoft.whattoeat.recipes.add.sideDishes.name";

    private static final String KEYWORD_SEPARATOR = ",";

    private static final double WIDTH = 600;
    private static final double HEIGHT = 800;

    private static final double PREPARATION_FIELD_MIN_HEIGHT = 150;

    private static final int MAX_RATING = 10;

    private final VBox verticalBox = new VBox();

    private final HBox firstRow = new HBox();
    private final HBox secondRow = new HBox();
    private final HBox thirdRow = new HBox();
    private final HBox fourthRow = new HBox();
    private final HBox fifthRow = new HBox();
    private final HBox sixthRow = new HBox();

    private final Label nameLabel = new Label();
    private final TextField nameField = new TextField();

    private final Label typeLabel = new Label();
    private final CheckComboBox<RecipeType> typeField = new CheckComboBox<>();

    private final Label tasteLabel = new Label();
    private final PrefixSelectionChoiceBox<Taste> tasteField = new PrefixSelectionChoiceBox();

    private final Label timeLabel = new Label();
    private final PrefixSelectionChoiceBox<PreparationTime> timeField = new PrefixSelectionChoiceBox();

    private final Label ratingLabel = new Label();
    private final Rating ratingField = new Rating(MAX_RATING);

    private final Label keywordLabel = new Label();
    private final TextField keywordField = new TextField();
    private final FlowPane keywordsView = new FlowPane(Orientation.HORIZONTAL);
    private final Set<String> keywordSet = new HashSet<>();

    private final Label ingredientLabel = new Label();
    private final Label ingredientNameLabel = new Label();
    private final TextField ingredientNameField = new TextField();
    private final Label ingredientQuantityLabel = new Label();
    private final Label ingredientUnitLabel = new Label();
    private final TextField ingredientQuantityField = new TextField();
    private final TableView<IngredientCouple> ingredientView = new TableView();
    private final TableColumn<IngredientCouple, String> nameColumn = new TableColumn<>();
    private final TableColumn<IngredientCouple, String> quantityColumn = new TableColumn<>();
    private final TableColumn<IngredientCouple, String> keywordsColumn = new TableColumn<>();
    private final ObservableList<IngredientCouple> ingredientList = FXCollections.observableArrayList();

    private final Label preparationLabel = new Label();
    private final TextArea preparationField = new TextArea();

    private final Label sideDishLabel = new Label();
    private final Label sideDishNameLabel = new Label();
    private final TextField sideDishNameField = new TextField();
    private final TableView<String> sideDishView = new TableView<>();
    private final TableColumn<String, String> sideDishNameColumn = new TableColumn<>();
    private final ObservableList<String> sideDishList = FXCollections.observableArrayList();

    private final RecipeService recipeService;
    private final IngredientInfoService ingredientInfoService;

    public RecipeAddDialog() {
        super();

        this.recipeService = ServiceHolder.getRecipeService();
        this.ingredientInfoService = ServiceHolder.getIngredientInfoService();

        this.setResizable(true);
        this.initModality(Modality.APPLICATION_MODAL);
        this.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        setupLayout();
        setupResultConverter();
    }

    private void setupResultConverter(){
        this.setResultConverter((buttonType) -> {
            if(ButtonType.FINISH.equals(buttonType)){
                return createRecipe();
            }else{
                return null;
            }
        });
    }

    private Recipe createRecipe(){
        final Recipe recipe = new Recipe();

        recipe.setName(nameField.getText());
        recipe.setRecipeTypes(new HashSet<>(typeField.getCheckModel().getCheckedItems()));
        recipe.setTaste(tasteField.getValue());
        recipe.setPreparationTime(timeField.getValue());
        recipe.setRating(Double.valueOf(ratingField.getRating()).intValue());
        recipe.setKeywords(keywordSet);
        recipe.setPreparation(preparationField.getText());
        recipe.setIngredients(ingredientList.stream().map((ingredientCouple) -> ingredientCouple.getRecipeIngredient()).collect(Collectors.toSet()));
        recipe.setSideDishes(new HashSet<>(sideDishList));

        RecipeValidator validator = new RecipeValidator();
        Map<String, String> validationResult = validator.validate(recipe);
        if(!validationResult.isEmpty()){
            throw new IllegalStateException(validationResult.toString());
        }

        return recipe;
    }

    private void setupLayout(){
        
        nameLabel.setText(I18n.getText(I18N_NAME));      
        firstRow.getChildren().addAll(nameLabel, nameField);

        typeLabel.setText(I18n.getText(I18N_TYPE));
        tasteLabel.setText(I18n.getText(I18N_TASTE));
        timeLabel.setText(I18n.getText(I18N_TIME));
        typeField.getItems().addAll(RecipeType.values());
        tasteField.getItems().addAll(Taste.values());
        timeField.getItems().addAll(PreparationTime.values());
        setupTypeEvents();
        secondRow.getChildren().addAll(typeLabel, typeField, tasteLabel, tasteField, timeLabel, timeField);

        ratingLabel.setText(I18n.getText(I18N_RATING));
        thirdRow.getChildren().addAll(ratingLabel, ratingField);

        keywordLabel.setText(I18n.getText(I18N_KEYWORDS));
        setupKeywordSuggestion();
        fourthRow.getChildren().addAll(keywordLabel, keywordField);

        ingredientLabel.setText(I18n.getText(I18N_INGREDIENTS));
        ingredientNameLabel.setText(I18n.getText(I18N_INGREDIENTS_NAME));
        ingredientQuantityLabel.setText(I18n.getText(I18N_INGREDIENTS_QUANTITY));
        setupIngredientTable();
        setupIngredientSuggestion();
        setupIngredientEvents();
        fifthRow.getChildren().addAll(ingredientNameLabel, ingredientNameField, ingredientQuantityLabel, ingredientQuantityField, ingredientUnitLabel);
        
        preparationLabel.setText(I18n.getText(I18N_PREPARATION));
        preparationField.setWrapText(true);
        preparationField.setMinHeight(PREPARATION_FIELD_MIN_HEIGHT);

        setSideDishesVisibility(false);
        sideDishLabel.setText(I18n.getText(I18N_SIDE_DISHES));
        sideDishNameLabel.setText(I18n.getText(I18N_SIDE_DISH_NAME));
        setupSideDishSuggestions();
        setupSideDishTable();
        sixthRow.getChildren().addAll(sideDishNameLabel, sideDishNameField);

        verticalBox.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, keywordsView, ingredientLabel, fifthRow, ingredientView,  preparationLabel, preparationField, sideDishLabel, sixthRow, sideDishView);
        verticalBox.setFillWidth(true);
        verticalBox.setPadding(Insets.EMPTY);

        this.getDialogPane().setContent(verticalBox);
    }

    private void setupKeywordSuggestion(){
        AutoCompletionBinding<String> autoCompletion = TextFields.bindAutoCompletion(keywordField, new FullWordSuggestionProvider(recipeService.getAllRecipeKeywords()));

        autoCompletion.setOnAutoCompleted((completionEvent -> {
            final String keyword = completionEvent.getCompletion();
            addKeyword(keyword);
        }));

        keywordField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    keyEvent.consume();
                    addKeyword(keywordField.getText());
                    break;
                default:
                    break;
                }
        });
    }

    private void addKeyword(final String keyword) {
        keywordField.clear();
        if (keywordSet.contains(keyword)) {
            return;
        }

        keywordSet.add(keyword);
        final Label keywordLabel = KeywordLabelFactory.createKeywordLabel(keyword);
        keywordsView.getChildren().add(keywordLabel);
    }

    private void setupIngredientSuggestion(){
        AutoCompletionBinding<String> autoCompletion = TextFields.bindAutoCompletion(ingredientNameField, new FullWordSuggestionProvider(ingredientInfoService.getIngredientNames()));
        autoCompletion.setOnAutoCompleted((completionEvent -> {
            final String ingredientName = completionEvent.getCompletion();
            final Ingredient ingredientInfo = ingredientInfoService.getIngredientInfoByName(ingredientName);
            ingredientUnitLabel.setText(I18n.getText(ingredientInfo.getIngredientUnit().getLabelKey()));
            ingredientQuantityField.requestFocus();
        }));

        ingredientNameField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    keyEvent.consume();
                    break;
                default:
                    break;
                }
        });

    }

    private void setupIngredientEvents(){
        ingredientQuantityField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    keyEvent.consume();
                    final String ingredientName = ingredientNameField.getText();
                    final String quantity = ingredientQuantityField.getText();

                    final RecipeIngredient ingredient = new RecipeIngredient(ingredientName, Float.parseFloat(quantity));
                    final Ingredient ingredientInfo = ingredientInfoService.getIngredientInfoByName(ingredientName);
                    final IngredientCouple ingredientCouple = new IngredientCouple(ingredient, ingredientInfo);

                    if(!ingredientList.contains(ingredientCouple)){
                        ingredientList.add(ingredientCouple);
                    }

                    ingredientUnitLabel.setText(StringUtils.EMPTY);
                    ingredientQuantityField.clear();
                    ingredientNameField.clear();
                    ingredientNameField.requestFocus();
                    break;
                default:
                    break;
                }
        });
    }

    private void setupIngredientTable(){
        ingredientView.getColumns().addAll(nameColumn, quantityColumn, keywordsColumn);
        ingredientView.setItems(ingredientList);

        nameColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>( param.getValue().getRecipeIngredient().getIngredientKey()));
        quantityColumn.setCellValueFactory((param) -> {
            final IngredientCouple ingredientCouple = param.getValue();
            final String quantity = ingredientCouple.getRecipeIngredient().getQuantity() + " " + I18n.getText(ingredientCouple.getIngredient().getIngredientUnit().getLabelKey());
            return new ReadOnlyObjectWrapper<>(quantity);
        });
        keywordsColumn.setCellValueFactory((TableColumn.CellDataFeatures<IngredientCouple, String> param) -> getValueFromSet(param.getValue().getIngredient().getKeywords()));
    }

    private ObservableValue<String> getValueFromSet(Set<String> stringSet){
        return new ReadOnlyObjectWrapper<>(StringUtils.join(stringSet, KEYWORD_SEPARATOR));
    }

    private void setupTypeEvents(){
        typeField.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends RecipeType> change) -> {
            change.next();
            final ObservableList<? extends RecipeType> types = change.getList();

            if(types.contains(RecipeType.LUNCH) || types.contains(RecipeType.DINNER)){
                setSideDishesVisibility(true);
            }else{
                setSideDishesVisibility(false);
                sideDishView.getItems().clear();
            }
        });
    }

    private void setSideDishesVisibility(final boolean visible){
        sideDishLabel.setVisible(visible);
        sixthRow.setVisible(visible);
        sideDishView.setVisible(visible);
    }

    private void setupSideDishSuggestions(){
        final Set<String> sideDishNames = recipeService.getAllSideDishes().stream().map((recipe) -> recipe.getName()).collect(Collectors.toSet());

        AutoCompletionBinding<String> autoCompletion = TextFields.bindAutoCompletion(sideDishNameField, new FullWordSuggestionProvider(sideDishNames));
        autoCompletion.setOnAutoCompleted((completionEvent -> {
            final String sideDishName = completionEvent.getCompletion();
            sideDishList.add(sideDishName);
            sideDishNameField.clear();
        }));

        sideDishNameField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    keyEvent.consume();
                    break;
                default:
                    break;
                }
        });
    }

    private void setupSideDishTable(){
        sideDishView.getColumns().addAll(sideDishNameColumn);
        sideDishView.setItems(sideDishList);

        sideDishNameColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>( param.getValue()));
        sideDishNameColumn.setMinWidth(300);
    }

}
