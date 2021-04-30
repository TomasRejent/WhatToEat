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
    private final String manufacturer;
    private final Boolean general;
    private final Set<Integer> excludedIds;
    private final Boolean isEdible;
    private final Boolean isPurchasable;
    private final Boolean hasNutritionFacts;

    private IngredientFilter(final String name, final String manufacturer, final Boolean general, final Set<Integer> excludedIds, final Boolean isEdible, final Boolean isPurchasable, final Boolean hasNutritionFacts) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.general = general;
        this.excludedIds = excludedIds;
        this.isEdible = isEdible;
        this.isPurchasable = isPurchasable;
        this.hasNutritionFacts = hasNutritionFacts;
    }

    /**
     * @return (NotNull) Empty optional if name is not filtered. Optional with name to filter otherwise.
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getManufacturer(){
        return Optional.ofNullable(manufacturer);
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

    public Optional<Boolean> getIsEdible(){
        return Optional.ofNullable(isEdible);
    }

    public Optional<Boolean> getIsPurchasable(){
        return Optional.ofNullable(isPurchasable);
    }

    public Optional<Boolean> getHasNutritionFacts(){
        return Optional.ofNullable(hasNutritionFacts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("manufacturer", manufacturer)
                .append("general", general)
                .append("isPurchasable", isPurchasable)
                .append("isEdible", isEdible)
                .append("hasNutritionFacts", hasNutritionFacts)
                .toString();
    }

    public static class Builder {

        private String name;
        private String manufacturer;
        private Boolean general;
        private Set<Integer> excludedIds;
        private Boolean isEdible;
        private Boolean isPurchasable;
        private Boolean hasNutritionFacts;

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

        public Builder setManufacturer(final String manufacturer){
            if(StringUtils.isBlank(manufacturer)){
                this.manufacturer = null;
            } else {
                this.manufacturer = manufacturer;
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

        public Builder setEdible(final Boolean edible) {
            isEdible = edible;
            return this;
        }

        public Builder setPurchasable(final Boolean purchasable) {
            isPurchasable = purchasable;
            return this;
        }

        public Builder setHasNutritionFacts(final Boolean hasNutritionFacts){
            this.hasNutritionFacts = hasNutritionFacts;
            return this;
        }

        /**
         * Builds filter from this builder with previously specified filtering criteria.
         *
         * @return (NotNull)
         */
        public IngredientFilter build() {
            return new IngredientFilter(name, manufacturer, general, excludedIds, isEdible, isPurchasable, hasNutritionFacts);
        }
    }
}
