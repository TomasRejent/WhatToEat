package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientRefService;
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

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
@Service
class MealServiceImpl implements MealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealServiceImpl.class);

    @Autowired
    private RecipeRefService recipeRefService;
    @Autowired
    private IngredientRefService ingredientRefService;
    @Autowired
    private MealRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Meal entityToMeal(final MealEntity entity) {
        Validate.notNull(entity);

        return new MealImpl.Builder(entity.getId())
            .setServings(Optional.ofNullable(entity.getServings()).orElse(0f))
            .setAmount(Optional.ofNullable(entity.getAmount()).orElse(0))
            .setRecipe(Optional.ofNullable(entity.getRecipe()).map(recipeRefService::fromEntity).orElse(null))
            .setIngredient(Optional.ofNullable(entity.getIngredient()).map(ingredientRefService::fromEntity).orElse(null))
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
                .setAmount(meal.getAmount())
            .setRecipe(meal.getRecipe())
                .setIngredient(meal.getIngredient());
    }

    @Override
    public MealUpdateObject copyMeal(final Meal meal) {
        Validate.notNull(meal);

        return new MealImpl.Builder()
                .setServings(meal.getServings())
                .setAmount(meal.getAmount())
                .setRecipe(meal.getRecipe())
                .setIngredient(meal.getIngredient());
    }

    @Override
    public MealUpdateObject getMealUpdateObject(final MealEntity mealEntity) {
        LOGGER.debug("Getting update object for meal entity {}.", mealEntity);
        Validate.notNull(mealEntity);

        return new MealImpl.Builder(mealEntity.getId())
            .setServings(mealEntity.getServings())
            .setAmount(Optional.ofNullable(mealEntity.getAmount()).orElse(0))
            .setRecipe(Optional.ofNullable(mealEntity.getRecipe()).map(recipeRefService::fromEntity).orElse(null))
            .setIngredient(Optional.ofNullable(mealEntity.getIngredient()).map(ingredientRefService::fromEntity).orElse(null));
    }

    @Override
    public MealEntity mealToEntity(final MealUpdateObject mealUpdateObject) {
        LOGGER.debug("Converting update object to entity: {}.", mealUpdateObject);
        Validate.notNull(mealUpdateObject);

        MealEntity mealEntity = mealUpdateObject.getId().map(repository::getOne).orElse(new MealEntity());
        mealEntity
                .setServings(mealUpdateObject.getServings().orElse(0f))
                .setAmount(mealUpdateObject.getAmount().orElse(0))
                .setRecipe(mealUpdateObject.getRecipe().map(recipeRefService::toEntity).orElse(null))
                .setIngredient(mealUpdateObject.getIngredient().map(ingredientRefService::toEntity).orElse(null));

        return mealEntity;
    }
}
