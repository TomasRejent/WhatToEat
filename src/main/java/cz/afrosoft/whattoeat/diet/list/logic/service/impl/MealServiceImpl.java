package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.MealRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealService;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;

/**
 * @author Tomas Rejent
 */
@Service
class MealServiceImpl implements MealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealServiceImpl.class);

    @Autowired
    private RecipeRefService recipeRefService;
    @Autowired
    private MealRepository repository;

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

    @Override
    public MealUpdateObject getMealUpdateObject(final Meal meal) {
        LOGGER.debug("Getting update object for meal {}.", meal);
        Validate.notNull(meal);

        return new MealImpl.Builder(meal.getId())
            .setServings(meal.getServings())
            .setRecipe(meal.getRecipe());
    }

    @Override
    public MealUpdateObject copyMeal(final Meal meal) {
        Validate.notNull(meal);

        return new MealImpl.Builder()
                .setServings(meal.getServings())
                .setRecipe(meal.getRecipe());
    }

    @Override
    public MealUpdateObject getMealUpdateObject(final MealEntity mealEntity) {
        LOGGER.debug("Getting update object for meal entity {}.", mealEntity);
        Validate.notNull(mealEntity);

        return new MealImpl.Builder(mealEntity.getId())
            .setServings(mealEntity.getServings())
            .setRecipe(recipeRefService.fromEntity(mealEntity.getRecipe()));
    }

    @Override
    public MealEntity mealToEntity(final MealUpdateObject mealUpdateObject) {
        LOGGER.debug("Converting update object to entity: {}.", mealUpdateObject);
        Validate.notNull(mealUpdateObject);

        MealEntity mealEntity = mealUpdateObject.getId().map(repository::getOne).orElse(new MealEntity());
        mealEntity.setServings(mealUpdateObject.getServings().get())
            .setRecipe(recipeRefService.toEntity(mealUpdateObject.getRecipe().get()));

        return mealEntity;
    }
}
