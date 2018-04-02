package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealService;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomas Rejent
 */
@Service
class MealServiceImpl implements MealService {

    @Autowired
    private RecipeRefService recipeRefService;

    @Override
    @Transactional(readOnly = true)
    public Meal entityToMeal(final MealEntity entity) {
        Validate.notNull(entity);

        return new MealImpl.Builder(entity.getId())
                .setServings(entity.getServings())
                .setRecipe(recipeRefService.fromEntity(entity.getRecipe()))
                .build();
    }

    @Override
    public MealEntity mealToEntity(final Meal meal) {
        return null;
    }

    @Override
    public MealUpdateObject getMealCreateObject() {
        return new MealImpl.Builder();
    }
}
