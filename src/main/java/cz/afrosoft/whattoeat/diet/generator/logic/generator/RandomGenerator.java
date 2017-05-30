/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.generator.logic.generator;

import static cz.afrosoft.whattoeat.core.data.util.ParameterCheckUtils.checkNotNull;
import cz.afrosoft.whattoeat.diet.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.diet.generator.logic.model.GeneratorParameters;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javafx.util.Callback;

/**
 *
 * @author Tomas Rejent
 */
public class RandomGenerator extends AbstractGenerator implements Generator{

    private static final String GENERATOR_ID = "RandomGenerator.v1";
    private static final String GENERATOR_NAME = "Basic random generator";

    private Random random = new Random();

    @Override
    public String getId() {
        return GENERATOR_ID;
    }

    @Override
    public String getName() {
        return GENERATOR_NAME;
    }

    @Override
    public Diet generate(Collection<Recipe> recipes, GeneratorParameters parameters) {
        checkNotNull(recipes, "Recipe collection cannot be null.");
        checkNotNull(parameters, "Generator parameters cannot be null.");
        if(recipes.isEmpty()){
            throw new IllegalArgumentException("Recipes collection is empty. Cannot generate diet from nothing.");
        }

        final Diet diet = createDietSkeleton(parameters);
        List<DayDiet> dayDiets = createDayDietSkeleton(parameters);

        generateIfNeeded(parameters.getBreakfast(), dayDiets, recipes, RecipeType.BREAKFAST, (callbackParams) -> {callbackParams.dayDiet.setBreakfast(new Meal(callbackParams.recipeKey)); return null;});
        generateIfNeeded(parameters.getMorningSnack(), dayDiets, recipes, RecipeType.SNACK, (callbackParams) -> {callbackParams.dayDiet.setMorningSnack(new Meal(callbackParams.recipeKey)); return null;});
        generateIfNeeded(parameters.getSoup(), dayDiets, recipes, RecipeType.SOUP, (callbackParams) -> {callbackParams.dayDiet.setSoup(new Meal(callbackParams.recipeKey)); return null;});
        generateIfNeeded(parameters.getLunch(), dayDiets, recipes, RecipeType.LUNCH, (callbackParams) -> {callbackParams.dayDiet.setLunch(new Meal(callbackParams.recipeKey)); return null;});
        generateIfNeeded(parameters.getSideDish(), dayDiets, recipes, RecipeType.SIDE_DISH, (callbackParams) -> {callbackParams.dayDiet.setSideDish(new Meal(callbackParams.recipeKey)); return null;});
        generateIfNeeded(parameters.getAfternoonSnack(), dayDiets, recipes, RecipeType.SNACK, (callbackParams) -> {callbackParams.dayDiet.setAfternoonSnack(new Meal(callbackParams.recipeKey)); return null;});
        generateIfNeeded(parameters.getDinner(), dayDiets, recipes, RecipeType.DINNER, (callbackParams) -> {callbackParams.dayDiet.setDinner(new Meal(callbackParams.recipeKey)); return null;});

        diet.setDays(dayDiets);
        return diet;
    }

    private boolean isTrue(Boolean value){
        return value != null && value;
    }

    private void generateIfNeeded(Boolean enabled, List<DayDiet> dayDiets, Collection<Recipe> recipes, RecipeType type, Callback<CallbackParams, Void> callback){
        if(isTrue(enabled)){
            List<Recipe> filteredRecipes = new ArrayList<>(filterRecipesByType(recipes, type));
            dayDiets.stream().forEach((dayDiet) -> {
                final String recipeKey = getRandomRecipe(filteredRecipes);
                callback.call(new CallbackParams(recipeKey, dayDiet));
            });
        }
    }

    private String getRandomRecipe(List<Recipe> recipes){
        final int recipeIndex = random.nextInt(recipes.size());
        Recipe choosenRecipe = recipes.get(recipeIndex);
        return choosenRecipe.getKey();
    }

    private static class CallbackParams{

        private final String recipeKey;
        private final DayDiet dayDiet;

        public CallbackParams(String recipeKey, DayDiet dayDiet) {
            this.recipeKey = recipeKey;
            this.dayDiet = dayDiet;
        }

        public String getRecipeKey() {
            return recipeKey;
        }

        public DayDiet getDayDiet() {
            return dayDiet;
        }

    }

}
