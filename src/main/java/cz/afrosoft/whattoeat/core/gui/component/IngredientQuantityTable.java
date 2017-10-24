package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.KeywordCell;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.commons.lang3.Validate;

import java.util.*;

/**
 * @author Tomas Rejent
 */
public class IngredientQuantityTable extends TableView<IngredientQuantityTable.IngredientQuantity> {

    private static final String FXML_PATH = "/component/IngredientQuantityTable.fxml";

    @FXML
    private TableColumn<IngredientQuantity, Float> quantityColumn;
    @FXML
    private TableColumn<IngredientQuantity, String> nameColumn;
    @FXML
    private TableColumn<IngredientQuantity, Float> priceColumn;
    @FXML
    private TableColumn<IngredientQuantity, Collection<Keyword>> keywordColumn;

    private final Map<Ingredient, IngredientQuantity> lookupMap;

    public IngredientQuantityTable() {
        ComponentUtil.initFxmlComponent(this, FXML_PATH);
        lookupMap = new HashMap<>();
        setupColumnCellFactories();
    }

    private void setupColumnCellFactories() {
        quantityColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(IngredientQuantity::getQuantity, 0f));
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(IngredientQuantity::getName));
        priceColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(IngredientQuantity::getPrice, 0f));
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

    static class IngredientQuantity {

        private float quantity;
        private final Ingredient ingredient;

        IngredientQuantity(final float quantity, final Ingredient ingredient) {
            Validate.notNull(ingredient);
            Validate.isTrue(quantity >= 0, "Quantity cannot be negative");

            this.quantity = quantity;
            this.ingredient = ingredient;
        }

        void addQuantity(final float newQuantity) {
            Validate.isTrue(newQuantity >= 0);
            quantity += newQuantity;
        }

        void setQuantity(final float quantity) {
            Validate.isTrue(quantity >= 0);
            this.quantity = quantity;
        }

        public Ingredient getIngredient() {
            return ingredient;
        }

        public float getQuantity() {
            return quantity;
        }

        public float getPrice() {
            return quantity * ingredient.getPrice();
        }

        public String getName() {
            return ingredient.getName();
        }

        public Set<Keyword> getKeywords() {
            return ingredient.getKeywords();
        }
    }
}
