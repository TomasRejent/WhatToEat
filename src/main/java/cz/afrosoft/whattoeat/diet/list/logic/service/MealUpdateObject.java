package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;

import java.util.Optional;

/**
 * @author tomas.rejent
 */
public interface MealUpdateObject {

    Optional<Integer> getId();

    Optional<Float> getServings();

    Optional<RecipeRef> getRecipe();

    MealUpdateObject setServings(float servings);

    MealUpdateObject setRecipe(RecipeRef recipeRef);
}
