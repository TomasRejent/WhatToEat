package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;

/**
 * Immutable implementation of {@link RecipeRef} used to represent related recipes in other entities.
 *
 * @author Tomas Rejent
 */
final class RecipeRefImpl implements RecipeRef {

    private final Integer id;
    private final String name;

    RecipeRefImpl(final Integer id, final String name) {
        Validate.notNull(id);
        Validate.notBlank(name);

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
    public int compareTo(final RecipeRef otherRecipe) {
        return RecipeComparator.INSTANCE.compare(this, otherRecipe);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeRefImpl recipeRef = (RecipeRefImpl) o;

        return new EqualsBuilder()
                .append(id, recipeRef.id)
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
