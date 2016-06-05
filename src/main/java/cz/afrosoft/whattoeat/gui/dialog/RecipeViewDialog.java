/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.gui.dialog;

import com.sun.webkit.dom.KeyboardEventImpl;
import cz.afrosoft.whattoeat.ServiceHolder;
import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;
import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.gui.view.IngredientList;
import cz.afrosoft.whattoeat.gui.view.KeywordLabelFactory;

import cz.afrosoft.whattoeat.logic.model.Recipe;
import cz.afrosoft.whattoeat.logic.services.PriceCalculatorService;
import java.util.ArrayList;
import java.util.Collection;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Rejent
 */
public class RecipeViewDialog extends Dialog<Void>{

    private static final String I18N_PROPERTY_PANE_TITLE = "cz.afrosoft.whattoeat.recipes.view.properties.title";
    private static final String I18N_PREPARATION_PANE_TITLE = "cz.afrosoft.whattoeat.recipes.view.preparation.title";
    private static final String I18N_INGREDIENT_PANE_TITLE = "cz.afrosoft.whattoeat.recipes.view.ingredients.title";
    private static final String I18N_RECIPE_TYPE = "cz.afrosoft.whattoeat.recipes.table.header.recipeType";
    private static final String I18N_TASTE = "cz.afrosoft.whattoeat.recipes.table.header.taste";
    private static final String I18N_TIME = "cz.afrosoft.whattoeat.recipes.table.header.preparationTime";
    private static final String I18N_RATING = "cz.afrosoft.whattoeat.recipes.table.header.rating";
    private static final String I18N_SERVINGS = "cz.afrosoft.whattoeat.recipes.view.ingredients.servings";
    private static final String I18N_PRICE = "cz.afrosoft.whattoeat.recipes.view.ingredients.price";

    private static final double WIDTH = 600;
    private static final double HEIGHT = 400;

    private static final double PROPERTY_H_GAP = 5;
    private static final double PROPERTY_V_GAP = 5;
    private static final String PROPERTY_LABEL_SEPARATOR = ":";

    private static final double KEYWORD_H_GAP = 5;

    private static final String DEFAULT_SERVINGS_VALUE = "1";
    private static final double INGREDIENT_H_GAP = 5;
    private static final double INGREDIENT_V_GAP = 5;


    private final VBox verticalBox = new VBox();
    private final TitledPane propertiesPane = new TitledPane();
    private final TitledPane preparationPane = new TitledPane();
    private final TitledPane ingredientsPane = new TitledPane();

    private final GridPane propertiesLayout = new GridPane();
    private final Label typeLabel = new Label();
    private final Label tasteLabel = new Label();
    private final Label timeLabel = new Label();
    private final Label ratingLabel = new Label();
    private final Label typeText = new Label();
    private final Label tasteText = new Label();
    private final Label timeText = new Label();
    private final Label ratingText = new Label();
    private final FlowPane keywords = new FlowPane(Orientation.HORIZONTAL);

    private final TextArea preparationText = new TextArea();

    private final GridPane ingredientsLayout = new GridPane();
    private final Label servingsLabel = new Label();
    private final TextField servingsField = new TextField();
    private final Label priceLabel = new Label();
    private final Label priceText = new Label();
    private final IngredientList ingredientList = new IngredientList();
    
    private final PriceCalculatorService priceCalculatorService = ServiceHolder.getPriceCalculatorService();
 
    private Recipe recipe;
    
    public RecipeViewDialog() {
        super();
        this.setResizable(true);
        this.initModality(Modality.NONE);
        this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        setupLayout();
    }


    public void showRecipe(final Recipe recipe){
        checkNotNull(recipe, "Recipe to show cannot be null.");
        
        this.recipe = recipe;

        this.setTitle(recipe.getName());
        typeText.setText(StringUtils.join(recipe.getRecipeTypes().stream().map(recipeType -> I18n.getText(recipeType.getLabelKey())).toArray(), ","));
        tasteText.setText(I18n.getText(recipe.getTaste().getLabelKey()));
        timeText.setText(I18n.getText(recipe.getPreparationTime().getLabelKey()));
        ratingText.setText(String.valueOf(recipe.getRating()));

        keywords.getChildren().clear();
        for(String keyword : recipe.getKeywords()){
            keywords.getChildren().add(KeywordLabelFactory.createKeywordLabel(keyword));
        }

        preparationText.setText(recipe.getPreparation());

        ingredientList.setIngredients(recipe.getIngredients());
 
        updatePrice(1);
        this.show();
    }

    private void setupLayout(){
        setupPropertiesPane();
        setupPreparationPane();
        setupIngredientsPane();

        verticalBox.getChildren().addAll(propertiesPane, preparationPane, ingredientsPane);
        verticalBox.setFillWidth(true);
        verticalBox.setPadding(Insets.EMPTY);

        this.getDialogPane().setContent(verticalBox);
    }

    private void setupPropertiesPane(){
        propertiesPane.setText(I18n.getText(I18N_PROPERTY_PANE_TITLE));

        typeLabel.setText(I18n.getText(I18N_RECIPE_TYPE) + PROPERTY_LABEL_SEPARATOR);
        tasteLabel.setText(I18n.getText(I18N_TASTE) + PROPERTY_LABEL_SEPARATOR);
        timeLabel.setText(I18n.getText(I18N_TIME) + PROPERTY_LABEL_SEPARATOR);
        ratingLabel.setText(I18n.getText(I18N_RATING) + PROPERTY_LABEL_SEPARATOR);

        keywords.setHgap(KEYWORD_H_GAP);
        keywords.setVgap(PROPERTY_V_GAP);

        int column = 0;
        propertiesLayout.add(typeLabel, column++, 0);
        propertiesLayout.add(typeText, column++, 0);
        propertiesLayout.add(new Separator(Orientation.VERTICAL), column++, 0);
        propertiesLayout.add(tasteLabel, column++, 0);
        propertiesLayout.add(tasteText, column++, 0);
        propertiesLayout.add(new Separator(Orientation.VERTICAL), column++, 0);
        propertiesLayout.add(timeLabel, column++, 0);
        propertiesLayout.add(timeText, column++, 0);
        propertiesLayout.add(new Separator(Orientation.VERTICAL), column++, 0);
        propertiesLayout.add(ratingLabel, column++, 0);
        propertiesLayout.add(ratingText, column++, 0);
        propertiesLayout.add(keywords, 0, 1, column, 1);

        propertiesLayout.setHgap(PROPERTY_H_GAP);
        propertiesLayout.setVgap(PROPERTY_V_GAP);

        ColumnConstraints defaultColumnConstraints = new ColumnConstraints();
        ColumnConstraints lastColumnConstraints = new ColumnConstraints();
        lastColumnConstraints.setHgrow(Priority.SOMETIMES);
        propertiesLayout.getColumnConstraints().addAll(constructColumnConstraints(defaultColumnConstraints, column, lastColumnConstraints));

        propertiesPane.setContent(propertiesLayout);
        propertiesPane.setExpanded(false);
    }

    private Collection<ColumnConstraints> constructColumnConstraints(ColumnConstraints defaultConstraint, int numberOfColumns ,ColumnConstraints lastConstraint){
        Collection<ColumnConstraints> constraints = new ArrayList<>(numberOfColumns);
        for(int idx = 0; idx < numberOfColumns - 1 ; idx++){
            constraints.add(defaultConstraint);
        }
        constraints.add(lastConstraint);
        return constraints;
    }

    private void setupPreparationPane(){
        preparationPane.setText(I18n.getText(I18N_PREPARATION_PANE_TITLE));
        
        preparationText.setEditable(false);
        preparationText.setWrapText(true);
        preparationPane.setContent(preparationText);
    }

    private void setupIngredientsPane(){
        ingredientsPane.setText(I18n.getText(I18N_INGREDIENT_PANE_TITLE));

        servingsLabel.setText(I18n.getText(I18N_SERVINGS));
        priceLabel.setText(I18n.getText(I18N_PRICE));
        servingsField.setText(DEFAULT_SERVINGS_VALUE);
        servingsField.setPrefWidth(50);      

        int column = 0;
        ingredientsLayout.add(servingsLabel, column++, 0);
        ingredientsLayout.add(servingsField, column++, 0);
        ingredientsLayout.add(priceLabel, column++, 0);
        ingredientsLayout.add(priceText, column++, 0);
        ingredientsLayout.add(ingredientList, 0, 1, column, 1);

        ingredientsLayout.setHgap(INGREDIENT_H_GAP);
        ingredientsLayout.setVgap(INGREDIENT_V_GAP);
        ingredientsLayout.setPadding(new Insets(8, 0, 0, 0));
        GridPane.setMargin(servingsLabel, new Insets(0, 0, 0, 10));

        ColumnConstraints defaultColumnConstraints = new ColumnConstraints();
        ColumnConstraints lastColumnConstraints = new ColumnConstraints();
        lastColumnConstraints.setHgrow(Priority.SOMETIMES);
        ingredientsLayout.getColumnConstraints().addAll(constructColumnConstraints(defaultColumnConstraints, column, lastColumnConstraints));

        ingredientsPane.setContent(ingredientsLayout);

        setupIngredientPaneEvents();
    }

    private void updatePrice(int servings) {
        float price = priceCalculatorService.calculatePrice(recipe, servings);
        priceText.setText(String.valueOf(price));
    }
    
    private void setupIngredientPaneEvents(){
        servingsField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(KeyCode.ENTER.equals(event.getCode()) && StringUtils.isNumeric(servingsField.getText())){
                    int servings =Integer.parseInt(servingsField.getText());
                    ingredientList.setServings(servings);
                    
                    updatePrice(servings);
                }
            }
        });

        servingsField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!StringUtils.isNumeric(event.getCharacter())){
                    event.consume();
                }
            }
        });
    }

}
