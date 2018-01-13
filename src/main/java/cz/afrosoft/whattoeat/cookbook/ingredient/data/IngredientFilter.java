package cz.afrosoft.whattoeat.cookbook.ingredient.data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

/**
 * Immutable class representing recipe filter. Use {@link IngredientFilter.Builder} to construct filter instance.
 *
 * @author Tomas Rejent
 */
public final class IngredientFilter {

    private final String name;

    private IngredientFilter(final String name) {
        this.name = name;
    }

    /**
     * @return (NotNull) Empty optional if name is not filtered. Optional with name to filter otherwise.
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .toString();
    }

    public static class Builder {

        private String name;

        /**
         * @param name (Nullable) Name to filter by. If null, empty or blank value is specified, then ingredients are not filtered by name.
         * @return (NotNull) This builder to enable method chaining.
         */
        public Builder setName(final String name) {
            if (StringUtils.isBlank(name)) {
                this.name = null;
            } else {
                this.name = name;
            }
            return this;
        }

        /**
         * Builds filter from this builder with previously specified filtering criteria.
         *
         * @return (NotNull)
         */
        public IngredientFilter build() {
            return new IngredientFilter(name);
        }
    }
}
