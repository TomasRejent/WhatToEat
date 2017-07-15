package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Represents connection between {@link Recipe} and {@link Ingredient}.
 * Specifies how much/many of ingredient should be used to make one
 * serving of meal from recipe.
 *
 * @author Tomas Rejent
 */
public interface RecipeIngredient extends IdEntity {

    /**
     * @return Quantity of ingredient needed to prepare one serving of meal
     * from recipe. Unit of this quantity is determined by {@link Ingredient#getIngredientUnit()}.
     */
    float getQuantity();
}
