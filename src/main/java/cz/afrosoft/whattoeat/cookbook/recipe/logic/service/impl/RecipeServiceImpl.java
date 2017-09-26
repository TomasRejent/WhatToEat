package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookRefService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeRepository;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientRefService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeUpdateObject;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementation of {@link RecipeService} which uses {@link cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl.RecipeImpl} as implementation of
 * {@link Recipe} and provides its own implementation of {@link RecipeUpdateObject}.
 *
 * @author Tomas Rejent
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private CookbookRefService cookbookRefService;

    @Autowired
    private RecipeRefService recipeRefService;

    @Autowired
    private RecipeIngredientRefService recipeIngredientRefService;

    @Autowired
    private KeywordService keywordService;

    @Override
    public Set<Recipe> getAllRecipes() {
        LOGGER.debug("Getting all recipes.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToRecipe);
    }

    @Override
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

        return new RecipeImpl.Builder()
                .setId(recipe.getId())
                .setName(recipe.getName())
                .setPreparation(recipe.getPreparation())
                .setRating(recipe.getRating())
                .setRecipeTypes(recipe.getRecipeTypes())
                .setTaste(recipe.getTaste())
                .setIngredientPreparationTime(recipe.getIngredientPreparationTime())
                .setCookingTime(recipe.getCookingTime())
                .setIngredients(recipe.getIngredients())
                .setSideDishes(recipe.getSideDishes())
                .setCookbooks(recipe.getCookbooks())
                .setKeywords(recipe.getKeywords());
    }

    @Override
    public Recipe createOrUpdate(final RecipeUpdateObject recipeChanges) {
        LOGGER.debug("Updating recipe: {}", recipeChanges);

        Validate.notNull(recipeChanges, "Cannot createOrUpdate recipe with null changes.");

        RecipeEntity entity = new RecipeEntity();
        entity.setId(recipeChanges.getId())
                .setName(recipeChanges.getName())
                .setPreparation(recipeChanges.getPreparation())
                .setRating(recipeChanges.getRating())
                .setRecipeTypes(recipeChanges.getRecipeTypes())
                .setTaste(recipeChanges.getTaste())
                .setIngredientPreparationTime(recipeChanges.getIngredientPreparationTime())
                .setCookingTime(recipeChanges.getCookingTime())
                .setSideDishes(ConverterUtil.convertToSet(recipeChanges.getSideDishes(), recipeRefService::toEntity))
                .setIngredients(ConverterUtil.convertToSet(recipeChanges.getIngredients(), recipeIngredientRefService::toEntity))
                .setKeywords(ConverterUtil.convertToSet(recipeChanges.getKeywords(), keywordService::keywordToEntity))
                .setCookbooks(ConverterUtil.convertToSet(recipeChanges.getCookbooks(), cookbookRefService::toEntity));

        return entityToRecipe(repository.save(entity));
    }

    /**
     * Converts {@link RecipeEntity} to {@link Recipe} using {@link RecipeImpl}.
     *
     * @param entity (NotNull) Entity to convert.
     * @return (NotNull) New {@link Recipe} with data from entity.
     */
    private Recipe entityToRecipe(final RecipeEntity entity) {
        Validate.notNull(entity);

        return new RecipeImpl.Builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setPreparation(entity.getPreparation())
                .setRating(entity.getRating())
                .setRecipeTypes(entity.getRecipeTypes())
                .setTaste(entity.getTaste())
                .setIngredientPreparationTime(entity.getIngredientPreparationTime())
                .setCookingTime(entity.getCookingTime())
                .setCookbooks(ConverterUtil.convertToSortedSet(entity.getCookbooks(), cookbookRefService::fromEntity))
                .setIngredients(ConverterUtil.convertToSet(entity.getIngredients(), recipeIngredientRefService::fromEntity))
                .setSideDishes(ConverterUtil.convertToSortedSet(entity.getSideDishes(), recipeRefService::fromEntity))
                .setKeywords(ConverterUtil.convertToSortedSet(entity.getKeywords(), keywordService::entityToKeyword))
                .build();
    }
}
