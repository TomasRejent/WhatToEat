package cz.afrosoft.whattoeat.diet.shopping.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItem;
import org.apache.commons.lang3.Validate;

import java.util.Set;

/**
 * @author Tomas Rejent
 */
class ShoppingItemImpl implements ShoppingItem {

    private final Ingredient ingredient;
    private final float quantity;

    ShoppingItemImpl(final Ingredient ingredient, final float quantity) {
        Validate.notNull(ingredient);

        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return ingredient.getName();
    }

    @Override
    public float getQuantity() {
        return quantity;
    }

    @Override
    public IngredientUnit getUnit() {
        return ingredient.getIngredientUnit();
    }

    @Override
    public Set<Keyword> getKeywords() {
        return ingredient.getKeywords();
    }
}
