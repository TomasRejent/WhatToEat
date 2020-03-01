package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.KeywordCell;
import cz.afrosoft.whattoeat.core.gui.table.NutritionFactsIconCell;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/IngredientQuantityTable.fxml")
public class IngredientQuantityTable extends TableView<IngredientQuantity> {

    @FXML
    private TableColumn<IngredientQuantity, Float> quantityColumn;
    @FXML
    private TableColumn<IngredientQuantity, String> nameColumn;
    @FXML
    private TableColumn<IngredientQuantity, Float> priceColumn;
    @FXML
    private TableColumn<IngredientQuantity, Optional<NutritionFacts>> nutritionFactsColumn;
    @FXML
    private TableColumn<IngredientQuantity, Collection<Keyword>> keywordColumn;

    @Autowired
    private IngredientQuantityService quantityService;

    private final Map<Ingredient, IngredientQuantity> lookupMap;

    public IngredientQuantityTable() {
        lookupMap = new HashMap<>();
    }

    @PostConstruct
    private void initialize() {
        setupColumnCellFactories();
    }

    private void setupColumnCellFactories() {
        quantityColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(IngredientQuantity::getQuantity, 0f));
        quantityColumn.setCellFactory(param -> new IngredientQuantityCell(quantityService));
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(IngredientQuantity::getName));
        priceColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(IngredientQuantity::getPrice, 0f));
        nutritionFactsColumn.setCellFactory(column -> new NutritionFactsIconCell<>());
        nutritionFactsColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper((ingredientQuantity) -> ingredientQuantity.getIngredient().getNutritionFacts(), null));
        keywordColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(IngredientQuantity::getKeywords, Collections.emptySet()));
        keywordColumn.setCellFactory(param -> new KeywordCell<>());
    }

    public void addRecipeIngredients(final float servings, final Collection<RecipeIngredient> recipeIngredients) {
        Validate.isTrue(servings > 0);
        Validate.noNullElements(recipeIngredients);

        recipeIngredients.forEach(recipeIngredient -> {
            if (lookupMap.containsKey(recipeIngredient.getIngredient())) {
                IngredientQuantity ingredientQuantity = lookupMap.get(recipeIngredient.getIngredient());
                ingredientQuantity.addQuantity(servings * recipeIngredient.getQuantity());
            } else {
                IngredientQuantity ingredientQuantity = new IngredientQuantity(servings * recipeIngredient.getQuantity(), recipeIngredient.getIngredient());
                lookupMap.put(ingredientQuantity.getIngredient(), ingredientQuantity);
                getItems().add(ingredientQuantity);
            }
        });
    }

    public void setRecipeIngredientsQuantities(final float servings, final Collection<RecipeIngredient> recipeIngredients) {
        recipeIngredients.forEach(recipeIngredient -> {
            if (lookupMap.containsKey(recipeIngredient.getIngredient())) {
                IngredientQuantity ingredientQuantity = lookupMap.get(recipeIngredient.getIngredient());
                ingredientQuantity.setQuantity(servings * recipeIngredient.getQuantity());
            } else {
                IngredientQuantity ingredientQuantity = new IngredientQuantity(servings * recipeIngredient.getQuantity(), recipeIngredient.getIngredient());
                lookupMap.put(ingredientQuantity.getIngredient(), ingredientQuantity);
                getItems().add(ingredientQuantity);
            }
        });
        this.refresh();
    }

    public void clear() {
        getItems().clear();
        lookupMap.clear();
    }

    public float getTotalPrice() {
        return getItems().stream().map(IngredientQuantity::getPrice).reduce((price1, price2) -> price1 + price2).orElse(0f);
    }

}
