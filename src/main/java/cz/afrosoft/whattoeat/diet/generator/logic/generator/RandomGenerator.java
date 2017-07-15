/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.generator.logic.generator;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeOld;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.diet.generator.logic.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

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
    public Diet generate(Collection<RecipeOld> recipes, GeneratorParameters parameters) {
        checkNotNull(recipes, "Recipe collection cannot be null.");
        checkNotNull(parameters, "Generator parameters cannot be null.");
        if(recipes.isEmpty()){
            throw new IllegalArgumentException("Recipes collection is empty. Cannot generate diet from nothing.");
        }

        final Diet diet = createDietSkeleton(parameters);
        List<DayDiet> dayDiets = createDayDietSkeleton(parameters);

        GenerationData generationData = new GenerationData(dayDiets, recipes, parameters);
        generateIfNeeded(generationData, MealGenerationType.BREAKFAST);
        generateIfNeeded(generationData, MealGenerationType.MORNING_SNACK);
        generateIfNeeded(generationData, MealGenerationType.SOUP);
        generateIfNeeded(generationData, MealGenerationType.LUNCH);
        generateIfNeeded(generationData, MealGenerationType.SIDE_DISH);
        generateIfNeeded(generationData, MealGenerationType.AFTERNOON_SNACK);
        generateIfNeeded(generationData, MealGenerationType.DINNER);

        diet.setDays(dayDiets);
        return diet;
    }

    private void generateIfNeeded(GenerationData generationData, MealGenerationType type){
        if(type.enablingMethod.apply(generationData.getParameters())){
            List<RecipeOld> filteredRecipes = new ArrayList<>(filterRecipesByType(generationData.getRecipes(), type.recipeType));
            if(!filteredRecipes.isEmpty()){
                generationData.getDayDiets().forEach((dayDiet) -> type.resultSetter.accept(dayDiet, getRandomMeal(filteredRecipes)));
            }
        }
    }

    private Meal getRandomMeal(List<RecipeOld> recipes) {
        return recipeToMeal(getRandomRecipe(recipes));
    }

    private RecipeOld getRandomRecipe(List<RecipeOld> recipes) {
        final int recipeIndex = random.nextInt(recipes.size());
        return recipes.get(recipeIndex);
    }

    private Meal recipeToMeal(RecipeOld recipe) {
        return new Meal(recipe.getKey());
    }

    private enum MealGenerationType{
        BREAKFAST(RecipeType.BREAKFAST, GeneratorParameters::getBreakfast, DayDiet::setBreakfast),
        MORNING_SNACK(RecipeType.SNACK, GeneratorParameters::getMorningSnack, DayDiet::setMorningSnack),
        SOUP(RecipeType.SOUP, GeneratorParameters::getSoup, DayDiet::setSoup),
        LUNCH(RecipeType.LUNCH, GeneratorParameters::getLunch, DayDiet::setLunch),
        SIDE_DISH(RecipeType.SIDE_DISH, GeneratorParameters::getSideDish, DayDiet::setSideDish),
        AFTERNOON_SNACK(RecipeType.SNACK, GeneratorParameters::getAfternoonSnack, DayDiet::setAfternoonSnack),
        DINNER(RecipeType.DINNER, GeneratorParameters::getDinner, DayDiet::setDinner);

        private final RecipeType recipeType;
        private final BiConsumer<DayDiet, Meal> resultSetter;
        private final Function<GeneratorParameters, Boolean> enablingMethod;

        MealGenerationType(RecipeType recipeType, Function<GeneratorParameters, Boolean> enablingMethod, BiConsumer<DayDiet, Meal> resultSetter) {
            this.recipeType = recipeType;
            this.resultSetter = resultSetter;
            this.enablingMethod = enablingMethod;
        }
    }

    private class GenerationData {
        final List<DayDiet> dayDiets;
        final Collection<RecipeOld> recipes;
        final GeneratorParameters parameters;

        public GenerationData(List<DayDiet> dayDiets, Collection<RecipeOld> recipes, GeneratorParameters parameters) {
            this.dayDiets = dayDiets;
            this.recipes = recipes;
            this.parameters = parameters;
        }

        public List<DayDiet> getDayDiets() {
            return dayDiets;
        }

        public Collection<RecipeOld> getRecipes() {
            return recipes;
        }

        public GeneratorParameters getParameters() {
            return parameters;
        }
    }
}
