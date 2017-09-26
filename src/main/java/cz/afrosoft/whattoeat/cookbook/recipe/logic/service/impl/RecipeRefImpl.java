package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link RecipeRef} used to represent related recipes in other entities.
 *
 * @author Tomas Rejent
 */
final class RecipeRefImpl implements RecipeRef {

    private final Integer id;
    private final String name;

    public RecipeRefImpl(final Integer id, final String name) {
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
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
