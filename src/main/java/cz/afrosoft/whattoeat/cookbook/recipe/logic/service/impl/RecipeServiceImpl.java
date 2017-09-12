package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeRepository;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
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
    private KeywordService keywordService;

    @Override
    public Set<Recipe> getAllRecipes() {
        LOGGER.debug("Getting all recipes.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToRecipe);
    }

    @Override
    public void delete(final Recipe recipe) {

    }

    @Override
    public RecipeUpdateObject getCreateObject() {
        return null;
    }

    @Override
    public RecipeUpdateObject getUpdateObject(final Recipe recipe) {
        return null;
    }

    @Override
    public Recipe createOrUpdate(final RecipeUpdateObject recipeChanges) {
        return null;
    }

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
                //TODO ingredients, sideDishes, cookbooks
                .setKeywords(ConverterUtil.convertToSortedSet(entity.getKeywords(), keywordService::entityToKeyword))
                .build();
    }
}
