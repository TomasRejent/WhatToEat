package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Represents meal cooked by some recipe.
 *
 * @author Tomas Rejent
 */
public interface Meal extends IdEntity, MealRef {

    /**
     * @return Number of servings. Should be 1 or greater.
     */
    float getServings();

    /**
     * @return (NotNull) Recipe of this meal.
     */
    RecipeRef getRecipe();

}
