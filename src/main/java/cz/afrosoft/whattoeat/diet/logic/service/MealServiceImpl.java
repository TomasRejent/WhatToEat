package cz.afrosoft.whattoeat.diet.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.diet.gui.view.MealView;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tomas Rejent on 13. 6. 2017.
 */
public class MealServiceImpl implements MealService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MealServiceImpl.class);

    final RecipeService recipeService;

    public MealServiceImpl(RecipeService recipeService) {
        Validate.notNull(recipeService);
        this.recipeService = recipeService;
    }

    @Override
    public MealView getMealView(Meal meal) {
        LOGGER.debug("Getting MealView for Meal: {}.", meal);
        Validate.notNull(meal);
        Recipe recipe = recipeService.getRecipeByKey(meal.getRecipeKey());
        return new MealView(meal, recipe.getName());
    }
}
