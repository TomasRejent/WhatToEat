package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;

/**
 * @author Tomas Rejent
 */
public class RecipeDataForDayDietDialog {

    private Recipe recipe;
    private MealNutritionFacts nutritionFacts;

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeDataForDayDietDialog setRecipe(final Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public MealNutritionFacts getNutritionFacts() {
        return nutritionFacts;
    }

    public RecipeDataForDayDietDialog setNutritionFacts(final MealNutritionFacts nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
        return this;
    }
}
