package cz.afrosoft.whattoeat.diet.list.logic.service;

import java.util.Optional;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;

/**
 * @author tomas.rejent
 */
public interface MealUpdateObject {

    Optional<Integer> getId();

    Optional<Integer> getServings();

    Optional<RecipeRef> getRecipe();

    MealUpdateObject setServings(int servings);

    MealUpdateObject setRecipe(RecipeRef recipeRef);
}
