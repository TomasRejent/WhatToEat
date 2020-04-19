package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.NutritionFactsEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsUpdateObject;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.model.RecipeDataForDayDietDialog;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
@Service
public class NutritionFactsServiceImpl implements NutritionFactsService {

    private static final String TOTAL_KEY = "cz.afrosoft.whattoeat.common.total";

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;

    @Override
    public NutritionFactsUpdateObject getCreateObject() {
        return new NutritionFactsImpl.Builder();
    }

    @Override
    public NutritionFactsUpdateObject getUpdateObject(final NutritionFacts nutritionFacts) {
        Validate.notNull(nutritionFacts);
        return new NutritionFactsImpl.Builder(nutritionFacts.getId())
                .setEnergy(nutritionFacts.getEnergy())
                .setFat(nutritionFacts.getFat())
                .setSaturatedFat(nutritionFacts.getSaturatedFat())
                .setCarbohydrate(nutritionFacts.getCarbohydrate())
                .setSugar(nutritionFacts.getSugar())
                .setProtein(nutritionFacts.getProtein())
                .setSalt(nutritionFacts.getSalt())
                .setFiber(nutritionFacts.getFiber());
    }

    @Override
    public NutritionFactsEntity toEntity(final NutritionFactsUpdateObject nutritionFacts) {
        if(nutritionFacts == null || !nutritionFacts.hasAnyUsefulValue()){
            return null;
        } else {
            NutritionFactsEntity entity = new NutritionFactsEntity();
            entity.setId(nutritionFacts.getId().orElse(null))
                    .setEnergy(nutritionFacts.getEnergy().orElse(null))
                    .setFat(nutritionFacts.getFat().orElse(null))
                    .setSaturatedFat(nutritionFacts.getSaturatedFat().orElse(null))
                    .setCarbohydrate(nutritionFacts.getCarbohydrate().orElse(null))
                    .setSugar(nutritionFacts.getSugar().orElse(null))
                    .setProtein(nutritionFacts.getProtein().orElse(null))
                    .setSalt(nutritionFacts.getSalt().orElse(null))
                    .setFiber(nutritionFacts.getFiber().orElse(null));
            return entity;
        }
    }

    @Override
    public Optional<NutritionFacts> toNutritionFacts(final NutritionFactsEntity entity) {
        if(entity == null){
            return Optional.empty();
        }else{
            return Optional.of(new NutritionFactsImpl.Builder(entity.getId())
                    .setEnergy(entity.getEnergy())
                    .setFat(entity.getFat())
                    .setSaturatedFat(entity.getSaturatedFat())
                    .setCarbohydrate(entity.getCarbohydrate())
                    .setSugar(entity.getSugar())
                    .setProtein(entity.getProtein())
                    .setSalt(entity.getSalt())
                    .setFiber(entity.getFiber())
                    .build());
        }
    }

    @Override
    public Float energyToHumanReadable(final Float baseEnergy) {
        if(baseEnergy == null){
            return null;
        }
        return baseEnergy/10;
    }

    @Override
    public Float energyToBase(final Float humanReadableEnergy) {
        if(humanReadableEnergy == null){
            return null;
        }
        return humanReadableEnergy*10;
    }

    @Override
    public Float nutritionToHumanReadable(final Float baseNutrition) {
        if(baseNutrition == null){
            return null;
        }
        return baseNutrition*100;
    }

    @Override
    public Float nutritionToBase(final Float humanReadableNutrition) {
        if(humanReadableNutrition == null){
            return null;
        }
        return humanReadableNutrition/100;
    }

    @Override
    public MealNutritionFacts getMealNutritionFacts(final Meal meal) {
        return getMealNutritionFacts(meal.getRecipe(), meal.getServings());
    }

    @Override
    public MealNutritionFacts getMealNutritionFacts(final RecipeRef recipeRef, final float servings) {
        boolean nutritionMissing = false;
        float energy = 0;
        float fat = 0;
        float saturatedFat = 0;
        float carbohydrate = 0;
        float sugar = 0;
        float protein = 0;
        float salt = 0;
        float fibre = 0;
        Recipe recipe = recipeService.getRecipeById(recipeRef.getId());
        Collection<RecipeIngredient> recipeIngredients = recipeService.loadRecipeIngredients(recipe.getIngredients());
        for(RecipeIngredient recipeIngredient : recipeIngredients){
            Optional<NutritionFacts> nutritionFactsOpt = recipeIngredient.getIngredient().getNutritionFacts();
            if(nutritionFactsOpt.isPresent()){
                NutritionFacts nutritionFacts = nutritionFactsOpt.get();
                float amountCoefficient = servings*recipeIngredient.getQuantity();
                energy += safeComputeNutrition(amountCoefficient, nutritionFacts.getEnergy());
                fat += safeComputeNutrition(amountCoefficient, nutritionFacts.getFat());
                saturatedFat += safeComputeNutrition(amountCoefficient, nutritionFacts.getSaturatedFat());
                carbohydrate += safeComputeNutrition(amountCoefficient, nutritionFacts.getCarbohydrate());
                sugar += safeComputeNutrition(amountCoefficient, nutritionFacts.getSugar());
                protein += safeComputeNutrition(amountCoefficient, nutritionFacts.getProtein());
                salt += safeComputeNutrition(amountCoefficient, nutritionFacts.getSalt());
                fibre += safeComputeNutrition(amountCoefficient, nutritionFacts.getFiber());
            } else {
                nutritionMissing = true;
            }
        }
        return new MealNutritionFacts()
                .setMealName(recipeRef.getName())
                .setEnergy(energy/1000) // conversion to kJ
                .setFat(fat)
                .setSaturatedFat(saturatedFat)
                .setCarbohydrate(carbohydrate)
                .setSugar(sugar)
                .setProtein(protein)
                .setSalt(salt)
                .setFibre(fibre)
                .setNutritionFactMissing(nutritionMissing);
    }

    private float safeComputeNutrition(float amountCoefficient, Float nutritionValue){
        if(nutritionValue == null){
            return 0;
        } else {
            return amountCoefficient*nutritionValue;
        }
    }

    @Override
    public List<MealNutritionFacts> getDayDietNutritionFacts(final DayDiet dayDiet) {
        Validate.notNull(dayDiet);
        List<Meal> meals = extractMealsFromDayDiet(dayDiet);
        List<MealNutritionFacts> result = new LinkedList<>();
        for(Meal meal : meals){
            result.add(getMealNutritionFacts(meal));
        }
        result.add(0, createTotalItem(result));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<RecipeDataForDayDietDialog> toDayDietView(final Collection<Recipe> recipes) {
        if(recipes == null || recipes.isEmpty()){
            return Collections.emptyList();
        }
        return recipes.stream().map(recipe -> {
            MealNutritionFacts mealNutritionFacts = getMealNutritionFacts(recipe, 1);
            return new RecipeDataForDayDietDialog()
                    .setRecipe(recipe)
                    .setNutritionFacts(mealNutritionFacts);
        }).collect(Collectors.toList());
    }

    private MealNutritionFacts createTotalItem(List<MealNutritionFacts> allMealNutritionFacts){
        float energy = 0;
        float fat = 0;
        float saturatedFat = 0;
        float carbohydrate = 0;
        float sugar = 0;
        float protein = 0;
        float salt = 0;
        float fibre = 0;

        boolean isNutritionFactMissing = false;
        for(MealNutritionFacts mealNutritionFacts : allMealNutritionFacts){
            energy += mealNutritionFacts.getEnergy();
            fat += mealNutritionFacts.getFat();
            saturatedFat += mealNutritionFacts.getSaturatedFat();
            carbohydrate += mealNutritionFacts.getCarbohydrate();
            sugar += mealNutritionFacts.getSugar();
            protein += mealNutritionFacts.getProtein();
            salt += mealNutritionFacts.getSalt();
            fibre += mealNutritionFacts.getFibre();
            isNutritionFactMissing = isNutritionFactMissing || mealNutritionFacts.isNutritionFactMissing();
        }

        return new MealNutritionFacts()
                .setMealName(I18n.getText(TOTAL_KEY))
                .setNutritionFactMissing(isNutritionFactMissing)
                .setEnergy(energy)
                .setFat(fat)
                .setSaturatedFat(saturatedFat)
                .setCarbohydrate(carbohydrate)
                .setSugar(sugar)
                .setProtein(protein)
                .setSalt(salt)
                .setFibre(fibre);
    }

    private List<Meal> extractMealsFromDayDiet(final DayDiet dayDiet){
        List<Meal> meals = new LinkedList<>();
        meals.addAll(dayDiet.getBreakfasts());
        meals.addAll(dayDiet.getSnacks());
        meals.addAll(dayDiet.getLunch());
        meals.addAll(dayDiet.getAfternoonSnacks());
        meals.addAll(dayDiet.getDinners());
        meals.addAll(dayDiet.getOthers());
        return meals;
    }
}
