package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.Validate;

import java.util.Set;

/**
 * Represents item of {@link IngredientQuantityTable}. Composite of {@link Ingredient} and its quantity.
 *
 * @author Tomas Rejent
 */
class IngredientQuantity {

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
