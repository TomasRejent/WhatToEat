package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Represents reference to {@link Recipe} which can be used in related entities.
 * Reference provides only limited subset of attributes and does not contain any other
 * related entities. It always contains id and usually contains name.
 */
public interface RecipeRef extends IdEntity, Comparable<RecipeRef> {

    /**
     * @return (NotNull) Gets name of recipe. This is not unique identifier.
     */
    String getName();

}
