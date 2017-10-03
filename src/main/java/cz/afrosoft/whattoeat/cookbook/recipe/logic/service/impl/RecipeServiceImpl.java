package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookRefService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientRefService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeIngredientEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeIngredientRepository;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeRepository;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.*;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

/**
 * Implementation of {@link RecipeService} which uses {@link cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl.RecipeImpl} as implementation of
 * {@link Recipe} and provides its own implementation of {@link RecipeUpdateObject}.
 *
 * @author Tomas Rejent
 */
@Service
@Transactional(readOnly = true)
public class RecipeServiceImpl implements RecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private CookbookRefService cookbookRefService;

    @Autowired
    private RecipeRefService recipeRefService;

    @Autowired
    private RecipeIngredientRefService recipeIngredientRefService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientRefService ingredientRefService;

    @Autowired
    private KeywordService keywordService;

    @Override
    public Set<Recipe> getAllRecipes() {
        LOGGER.debug("Getting all recipes.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToRecipe);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(final Recipe recipe) {
        LOGGER.debug("Deleting recipe: {}", recipe);
        Validate.notNull(recipe);
        repository.delete(recipe.getId());
    }

    @Override
    public RecipeUpdateObject getCreateObject() {
        return new RecipeImpl.Builder();
    }

    @Override
    public RecipeUpdateObject getUpdateObject(final Recipe recipe) {
        LOGGER.debug("Getting update object for recipe: {}", recipe);
        Validate.notNull(recipe);

        return new RecipeImpl.Builder(recipe.getId())
                .setName(recipe.getName())
                .setPreparation(recipe.getPreparation())
                .setRating(recipe.getRating())
                .setRecipeTypes(recipe.getRecipeTypes())
                .setTaste(recipe.getTaste())
                .setIngredientPreparationTime(recipe.getIngredientPreparationTime())
                .setCookingTime(recipe.getCookingTime())
                .setIngredients(ConverterUtil.convertToSet(recipe.getIngredients(), this::toUpdateObject))
                .setSideDishes(recipe.getSideDishes())
                .setCookbooks(recipe.getCookbooks())
                .setKeywords(recipe.getKeywords());
    }

    @Override
    @Transactional(readOnly = false)
    public Recipe createOrUpdate(final RecipeUpdateObject recipeChanges) {
        LOGGER.debug("Updating recipe: {}", recipeChanges);

        Validate.notNull(recipeChanges, "Cannot createOrUpdate recipe with null changes.");

        RecipeEntity entity = new RecipeEntity();
        entity.setId(recipeChanges.getId().orElse(null))
                .setName(recipeChanges.getName().get())
                .setPreparation(recipeChanges.getPreparation().get())
                .setRating(recipeChanges.getRating().get())
                .setRecipeTypes(recipeChanges.getRecipeTypes())
                .setTaste(recipeChanges.getTaste().get())
                .setIngredientPreparationTime(recipeChanges.getIngredientPreparationTime().get())
                .setCookingTime(recipeChanges.getCookingTime().get())
                .setSideDishes(ConverterUtil.convertToSet(recipeChanges.getSideDishes(), recipeRefService::toEntity))
                .setKeywords(ConverterUtil.convertToSet(recipeChanges.getKeywords(), keywordService::keywordToEntity))
                .setCookbooks(ConverterUtil.convertToSet(recipeChanges.getCookbooks(), cookbookRefService::toEntity));

        RecipeEntity entityWithoutIngredients = repository.save(entity);
        Set<RecipeIngredientEntity> recipeIngredients = ConverterUtil.convertToSet(recipeChanges.getIngredients(), this::fromUpdateObject);
        recipeIngredients.forEach(recipeIngredient -> recipeIngredient.setRecipe(entityWithoutIngredients));
        entityWithoutIngredients.setIngredients(recipeIngredients);
        return entityToRecipe(repository.save(entityWithoutIngredients));
    }

    @Override
    public RecipeIngredientUpdateObject getRecipeIngredientCreateObject() {
        return new RecipeIngredientImpl.Builder();
    }

    @Override
    public Collection<RecipeIngredientUpdateObject> toUpdateObjects(final Collection<RecipeIngredientRef> recipeIngredients) {
        return ConverterUtil.convertToSet(recipeIngredients, this::toUpdateObject);
    }

    /**
     * Converts {@link RecipeEntity} to {@link Recipe} using {@link RecipeImpl}.
     *
     * @param entity (NotNull) Entity to convert.
     * @return (NotNull) New {@link Recipe} with data from entity.
     */
    private Recipe entityToRecipe(final RecipeEntity entity) {
        Validate.notNull(entity);

        RecipeImpl.Builder builder = new RecipeImpl.Builder(entity.getId());
        builder.setName(entity.getName())
                .setPreparation(entity.getPreparation())
                .setRating(entity.getRating())
                .setRecipeTypes(entity.getRecipeTypes())
                .setTaste(entity.getTaste())
                .setIngredientPreparationTime(entity.getIngredientPreparationTime())
                .setCookingTime(entity.getCookingTime())
                .setCookbooks(ConverterUtil.convertToSortedSet(entity.getCookbooks(), cookbookRefService::fromEntity))
                .setSideDishes(ConverterUtil.convertToSortedSet(entity.getSideDishes(), recipeRefService::fromEntity))
                .setKeywords(ConverterUtil.convertToSortedSet(entity.getKeywords(), keywordService::entityToKeyword));

        builder.setExistingIngredients(ConverterUtil.convertToSet(entity.getIngredients(), recipeIngredientRefService::fromEntity));
        return builder.build();
    }

    private RecipeIngredientEntity fromUpdateObject(final RecipeIngredientUpdateObject updateObject) {
        Validate.notNull(updateObject);

        return new RecipeIngredientEntity()
                .setId(updateObject.getId().orElse(null))
                .setQuantity(updateObject.getQuantity().get())
                .setIngredient(ingredientRefService.toEntity(updateObject.getIngredient().get()));
    }

    private RecipeIngredientUpdateObject toUpdateObject(final RecipeIngredientRef recipeIngredient) {
        RecipeIngredientEntity entity = recipeIngredientRepository.getOne(recipeIngredient.getId());
        return new RecipeIngredientImpl.Builder(entity.getId())
                .setQuantity(entity.getQuantity())
                .setIngredient(ingredientService.entityToIngredient(entity.getIngredient()));
    }
}
