package cz.afrosoft.whattoeat.cookbook.ingredient.data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;
import java.util.Set;

/**
 * Immutable class representing recipe filter. Use {@link IngredientFilter.Builder} to construct filter instance.
 *
 * @author Tomas Rejent
 */
public final class IngredientFilter {

    private final String name;
    private final Boolean general;
    private final Set<Integer> excludedIds;

    private IngredientFilter(final String name, final Boolean general, final Set<Integer> excludedIds) {
        this.name = name;
        this.general = general;
        this.excludedIds = excludedIds;
    }

    /**
     * @return (NotNull) Empty optional if name is not filtered. Optional with name to filter otherwise.
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Boolean> getGeneral(){
        return Optional.ofNullable(general);
    }

    public Optional<Set<Integer>> getExcludedIds(){
        if(this.excludedIds == null || excludedIds.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(excludedIds);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("general", general)
                .toString();
    }

    public static class Builder {

        private String name;
        private Boolean general;
        private Set<Integer> excludedIds;

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

        public Builder setExcludedIds(Set<Integer> excludedIds){
            Validate.notNull(excludedIds);
            this.excludedIds = excludedIds;
            return this;
        }

        public Builder setGeneral(final Boolean general){
            this.general = general;
            return this;
        }

        /**
         * Builds filter from this builder with previously specified filtering criteria.
         *
         * @return (NotNull)
         */
        public IngredientFilter build() {
            return new IngredientFilter(name, general, excludedIds);
        }
    }
}
