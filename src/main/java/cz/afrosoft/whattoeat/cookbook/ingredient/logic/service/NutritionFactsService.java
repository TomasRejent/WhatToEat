package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.NutritionFactsEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.model.RecipeDataForDayDietDialog;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public interface NutritionFactsService {

    NutritionFactsUpdateObject getCreateObject();

    NutritionFactsUpdateObject getUpdateObject(NutritionFacts nutritionFacts);

    NutritionFactsEntity toEntity(NutritionFactsUpdateObject nutritionFacts);

    Optional<NutritionFacts> toNutritionFacts(NutritionFactsEntity nutritionFacts);

    /**
     * Converts basic energy rate joule/g to human readable kJ/100g.
     * @param baseEnergy
     * @return
     */
    Float energyToHumanReadable(Float baseEnergy);

    /**
     * Converts human readable energy kJ/100g to base joule/g.
     * @param humanReadableEnergy
     * @return
     */
    Float energyToBase(Float humanReadableEnergy);

    /**
     * Converts from base nutrition /g to human readable /100g.
     * @param baseNutrition
     * @return
     */
    Float nutritionToHumanReadable(Float baseNutrition);

    /**
     * Converts from human readable /100g to base /g.
     * @param humanReadableNutrition
     * @return
     */
    Float nutritionToBase(Float humanReadableNutrition);

    MealNutritionFacts getMealNutritionFacts(Meal meal);

    MealNutritionFacts getMealNutritionFacts(final RecipeRef recipeRef, final float servings);

    List<MealNutritionFacts> getDayDietNutritionFacts(DayDiet dayDiet);

    Collection<RecipeDataForDayDietDialog> toDayDietView(Collection<Recipe> recipes);
}
