package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;

/**
 * Provides services related to ingredient quantity.
 *
 * @author Tomas Rejent
 */
public interface IngredientQuantityService {

    /**
     * Formats quantity of specified ingredient into human readable form. Makes use of {@link cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion}
     * if possible to convert to most suitable unit depending on quantity.
     *
     * @param ingredient (NotNull) Ingredient for which quantity is formatted.
     * @param quantity   Quantity of ingredient. Negative values are not allowed.
     * @return
     */
    String getFormattedQuantity(Ingredient ingredient, float quantity);

}
