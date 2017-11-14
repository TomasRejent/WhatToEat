package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;
import java.util.Set;

/**
 * Immutable class representing recipe filter. Use {@link RecipeFilter.Builder} to construct filter instance.
 */
public final class RecipeFilter {

    private final String name;
    private final Set<CookbookRef> cookbooks;
    private final Set<RecipeType> type;

    private RecipeFilter(final String name, final Set<CookbookRef> cookbooks, final Set<RecipeType> type) {
        this.name = name;
        this.cookbooks = cookbooks;
        this.type = type;
    }

    /**
     * @return (NotNull) Empty optional if name is not filtered. Optional with name to filter otherwise.
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    /**
     * @return (NotNull) Empty optional if cookbooks are not filtered. Optional with cookbooks to filter otherwise.
     */
    public Optional<Set<CookbookRef>> getCookbooks() {
        return Optional.ofNullable(cookbooks);
    }

    /**
     * @return (NotNull) Empty optional if recipe types are not filtered. Optional with recipe types to filter otherwise.
     */
    public Optional<Set<RecipeType>> getType() {
        return Optional.ofNullable(type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("cookbooks", cookbooks)
                .append("type", type)
                .toString();
    }

    /**
     * Builder for recipe filter.
     */
    public static class Builder {

        private String name;
        private Set<CookbookRef> cookbooks;
        private Set<RecipeType> type;

        /**
         * @param name (Nullable) Name to filter by. If null, empty or blank value is specified, then recipes are not filtered by name.
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
         * @param type (Nullable) Recipe types to filter by. If null or empty set is specified, then recipes are not filtered by recipe types.
         * @return (NotNull) This builder to enable method chaining.
         */
        public Builder setType(final Set<RecipeType> type) {
            this.type = emptyToNull(type);
            return this;
        }

        /**
         * @param cookbooks (Nullable) Cookbooks to filter by. If null or empty set is specified, then recipes are not filtered by cookbooks.
         * @return (NotNull) This builder to enable method chaining.
         */
        public Builder setCookbooks(final Set<CookbookRef> cookbooks) {
            this.cookbooks = emptyToNull(cookbooks);
            return this;
        }

        private <T> Set<T> emptyToNull(final Set<T> set) {
            if (set == null || set.isEmpty()) {
                return null;
            } else {
                return set;
            }
        }

        /**
         * Builds filter from this builder with previously specified filtering criteria.
         * @return (NotNull)
         */
        public RecipeFilter build() {
            return new RecipeFilter(name, cookbooks, type);
        }
    }
}
