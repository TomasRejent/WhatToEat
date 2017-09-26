package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link RecipeIngredientRef} used to represent related recipe ingredients in other entities.
 *
 * @author Tomas Rejent
 */
final class RecipeIngredientRefImpl implements RecipeIngredientRef {

    private final Integer id;

    public RecipeIngredientRefImpl(final Integer id) {
        Validate.notNull(id);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .toString();
    }
}
