package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

import java.util.Optional;

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
     * @return Amount of meal in grams.
     */
    int getAmount();

    /**
     * @return Recipe of this meal. Can be null if meal is defined by ingredient.
     */
    RecipeRef getRecipe();

    /**
     * @return Ingredient of this meal. Can be null if meal is defined by recipe.
     */
    IngredientRef getIngredient();

    /**
     * @return Gets ingredient map belonging to recipe of this meal.
     */
    Optional<IngredientMap> getIngredientMap();

}
