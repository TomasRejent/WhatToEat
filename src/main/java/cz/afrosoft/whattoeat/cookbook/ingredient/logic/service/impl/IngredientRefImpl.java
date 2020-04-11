package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link IngredientRef} used to represent related ingredients in other entities.
 *
 * @author Tomas Rejent
 */
public class IngredientRefImpl implements IngredientRef {

    private final Integer id;
    private final String name;

    public IngredientRefImpl(final Integer id, final String name) {
        Validate.notNull(id);
        Validate.notEmpty(name);
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof IngredientRef)) {
            return false;
        }

        IngredientRef that = (IngredientRef) o;

        return new EqualsBuilder()
                .append(id, that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
