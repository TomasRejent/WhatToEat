package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link IngredientRef} used to represent related ingredients in other entities.
 *
 * @author Tomas Rejent
 */
public class IngredientRefImpl implements IngredientRef {

    private final Integer id;

    public IngredientRefImpl(final Integer id) {
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
