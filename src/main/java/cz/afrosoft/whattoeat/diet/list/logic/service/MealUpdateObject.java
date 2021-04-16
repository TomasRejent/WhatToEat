package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.IngredientMap;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;

import java.util.Optional;

/**
 * @author tomas.rejent
 */
public interface MealUpdateObject {

    Optional<Integer> getId();

    Optional<Float> getServings();

    Optional<RecipeRef> getRecipe();

    Optional<Integer> getAmount();

    Optional<IngredientRef> getIngredient();

    Optional<IngredientMap> getIngredientMap();

    MealUpdateObject setServings(float servings);

    MealUpdateObject setRecipe(RecipeRef recipeRef);

    MealUpdateObject setIngredient(IngredientRef ingredientRef);

    MealUpdateObject setAmount(int amount);

    MealUpdateObject setIngredientMap(IngredientMap ingredientMap);
}
