package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientFilter;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;

import java.util.List;

/**
 * @author Tomas Rejent
 */
public interface IngredientRepositoryCustom {

    /**
     * Find ingredients matching specified filter. Filter items are used with AND operator.
     *
     * @param filter (NotNull) Filter to filter by. Empty optional means that field is skipped.
     * @return (NotNull) List of matching ingredients.
     */
    List<IngredientEntity> findIngredientsByFilter(IngredientFilter filter);

}
