package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Holds mapping between recipe ingredients. Commonly used for mapping generic ingredients to real ingredients.
 * @author Tomas Rejent
 */
public interface IngredientMap extends IdEntity {

    /**
     * Gets mapped ingredient for specified ingredient.
     * @param genericIngredient (NotNull)
     * @return If specified ingredient does not have mapping, returns specified ingredient, else return mapped ingredient.
     */
    RecipeIngredient getMappedIngredient(RecipeIngredient genericIngredient);

}
