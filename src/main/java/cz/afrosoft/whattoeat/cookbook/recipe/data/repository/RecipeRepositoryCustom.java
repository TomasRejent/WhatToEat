package cz.afrosoft.whattoeat.cookbook.recipe.data.repository;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;

import java.util.List;

/**
 * Provides recipe queries which require specific implementation which cannot be provided by {@link RecipeRepository}.
 */
public interface RecipeRepositoryCustom {

    /**
     * Find recipes matching specified filter. Filter items are used with AND operator.
     *
     * @param filter (NotNull) Filter to filter by. Empty optional means that field is skipped.
     * @return (NotNull) List of matching recipes.
     */
    List<RecipeEntity> findRecipesByFilter(RecipeFilter filter);

}
