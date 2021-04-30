package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.*;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientUpdateObject;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsUpdateObject;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.UnitConversionUpdateObject;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Immutable and comparable implementation of {@link Ingredient}. Natural ordering is based
 * on {@link Ingredient#getName()}.
 * <p>
 * Is used by {@link IngredientService} to provide {@link Ingredient} instances for front end.
 *
 * @author Tomas Rejent
 */
final class IngredientImpl implements Ingredient {

    private final Integer id;
    private final String name;
    private final IngredientUnit ingredientUnit;
    private final float price;
    private final UnitConversion unitConversion;
    private final NutritionFacts nutritionFacts;
    private final Set<Keyword> keywords;
    private final boolean general;
    private final boolean purchasable;
    private final boolean edible;
    private final String manufacturer;
    private final Set<Shop> shops;
    private final RecipeRef recipe;
    private final IngredientRef parent;
    private final Set<IngredientRef> children;

    private IngredientImpl(final Integer id, final String name, final IngredientUnit ingredientUnit, final float price, final UnitConversion unitConversion, final NutritionFacts nutritionFacts, final Set<Keyword> keywords, final boolean general, final boolean purchasable, final boolean edible, final String manufacturer, final Set<Shop> shops, final RecipeRef recipe, final IngredientRef parent, final Set<IngredientRef> children) {
        this.id = id;
        this.name = name;
        this.ingredientUnit = ingredientUnit;
        this.price = price;
        this.unitConversion = unitConversion;
        this.nutritionFacts = nutritionFacts;
        this.keywords = Collections.unmodifiableSet(keywords);
        this.general = general;
        this.purchasable = purchasable;
        this.edible = edible;
        this.manufacturer = manufacturer;
        this.shops = Collections.unmodifiableSet(shops);
        this.recipe = recipe;
        this.parent = parent;
        this.children = Collections.unmodifiableSet(children);
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
    public String getFullName(){
        if(StringUtils.isNotEmpty(getManufacturer())){
            return getName() + "(" + getManufacturer() + ")";
        } else {
            return getName();
        }
    }

    @Override
    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public Optional<UnitConversion> getUnitConversion() {
        return Optional.ofNullable(unitConversion);
    }

    @Override
    public Optional<NutritionFacts> getNutritionFacts() {
        return Optional.ofNullable(nutritionFacts);
    }

    @Override
    public Set<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public Set<Shop> getShops() {
        return shops;
    }

    @Override
    public boolean isGeneral() {
        return general;
    }

    @Override
    public boolean isEdible() {
        return edible;
    }

    @Override
    public boolean isPurchasable() {
        return purchasable;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public Optional<RecipeRef> getRecipe() {
        return Optional.ofNullable(recipe);
    }

    @Override
    public Optional<IngredientRef> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Set<IngredientRef> getChildren() {
        return null;
    }

    @Override
    public int compareTo(final Ingredient otherIngredient) {
        return IngredientComparator.INSTANCE.compare(this, otherIngredient);
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

    static final class Builder implements IngredientUpdateObject {

        private final Integer id;
        private String name;
        private IngredientUnit ingredientUnit;
        private Float price;
        private UnitConversionUpdateObject unitConversion;
        private UnitConversion existingUnitConversion;
        private NutritionFactsUpdateObject nutritionFacts;
        private NutritionFacts existingNutritionFacts;
        private Set<Keyword> keywords;
        private boolean general = false;
        private boolean purchasable = true;
        private boolean edible = true;
        private String manufacturer;
        private Set<Shop> shops = Collections.emptySet();
        private RecipeRef recipe;
        private IngredientRef parent;
        private Set<IngredientRef> children = Collections.emptySet();

        public Builder() {
            this.id = null;
        }

        public Builder(final Integer id) {
            Validate.notNull(id);
            this.id = id;
        }

        @Override
        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        @Override
        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public Optional<IngredientUnit> getIngredientUnit() {
            return Optional.ofNullable(ingredientUnit);
        }

        @Override
        public Optional<Float> getPrice() {
            return Optional.ofNullable(price);
        }

        @Override
        public Optional<UnitConversionUpdateObject> getUnitConversion() {
            return Optional.ofNullable(unitConversion);
        }

        @Override
        public Set<Keyword> getKeywords() {
            return Optional.ofNullable(keywords).orElse(Collections.emptySet());
        }

        @Override
        public Builder setName(final String name) {
            Validate.notBlank(name);
            this.name = name;
            return this;
        }

        @Override
        public Builder setIngredientUnit(final IngredientUnit ingredientUnit) {
            Validate.notNull(ingredientUnit);
            this.ingredientUnit = ingredientUnit;
            return this;
        }

        @Override
        public Builder setPrice(final float price) {
            Validate.isTrue(price >= 0, "Price cannot be negative.");
            this.price = price;
            return this;
        }

        @Override
        public Builder setUnitConversion(final UnitConversionUpdateObject unitConversion) {
            Validate.notNull(unitConversion);

            setUnitConversionIfUseful(unitConversion);
            return this;
        }

        @Override
        public NutritionFactsUpdateObject getNutritionFacts() {
            return nutritionFacts;
        }

        @Override
        public IngredientUpdateObject setNutritionFacts(final NutritionFactsUpdateObject nutritionFacts) {
            Validate.notNull(nutritionFacts);
            this.nutritionFacts = nutritionFacts;
            return this;
        }

        @Override
        public IngredientUpdateObject setUnitConversion(final Float gramsPerPiece, final Float milliliterPerGram, final Float gramsPerPinch, final Float gramsPerCoffeeSpoon, final Float gramsPerSpoon) {
            setUnitConversionIfUseful(new UnitConversionImpl.Builder()
                    .setGramsPerPiece(gramsPerPiece)
                    .setMilliliterPerGram(milliliterPerGram)
                    .setGramsPerPinch(gramsPerPinch)
                    .setGramsPerCoffeeSpoon(gramsPerCoffeeSpoon)
                    .setGramsPerSpoon(gramsPerSpoon));
            return this;
        }

        /**
         * Set specified conversion to {@link #unitConversion} if it contains useful values (positive), else sets
         * null.
         *
         * @param unitConversion (NotNull) Unit conversion to set.
         */
        private void setUnitConversionIfUseful(final UnitConversionUpdateObject unitConversion) {
            Validate.notNull(unitConversion);

            if (unitConversion.hasAnyUsefulValue()) {
                this.unitConversion = unitConversion;
            } else {
                this.unitConversion = null;
            }
        }

        @Override
        public Builder setKeywords(final Set<Keyword> keywords) {
            Validate.noNullElements(keywords);
            this.keywords = keywords;
            return this;
        }

        public Builder setExistingUnitConversion(final UnitConversion existingUnitConversion) {
            Validate.notNull(existingUnitConversion);
            this.existingUnitConversion = existingUnitConversion;
            return this;
        }

        public Builder setExistingNutritionFacts(final NutritionFacts existingNutritionFacts){
            Validate.notNull(existingNutritionFacts);
            this.existingNutritionFacts = existingNutritionFacts;
            return this;
        }

        public Builder setChildren(final Set<IngredientRef> children){
            this.children = children;
            return this;
        }

        @Override
        public boolean isGeneral() {
            return general;
        }

        @Override
        public boolean isPurchasable() {
            return purchasable;
        }

        @Override
        public boolean isEdible() {
            return edible;
        }

        @Override
        public String getManufacturer() {
            return manufacturer;
        }

        @Override
        public Set<Shop> getShops() {
            return shops;
        }

        @Override
        public Optional<RecipeRef> getRecipe() {
            return Optional.ofNullable(recipe);
        }

        @Override
        public Optional<IngredientRef> getParent() {
            return Optional.ofNullable(parent);
        }

        @Override
        public Set<IngredientRef> getChildren() {
            return children;
        }

        @Override
        public Builder setGeneral(final boolean general) {
            this.general = general;
            return this;
        }

        @Override
        public Builder setPurchasable(final boolean purchasable) {
            this.purchasable = purchasable;
            return this;
        }

        @Override
        public Builder setEdible(final boolean edible) {
            this.edible = edible;
            return this;
        }

        @Override
        public Builder setManufacturer(final String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        @Override
        public Builder setShops(final Set<Shop> shops) {
            Validate.notNull(shops);
            this.shops = shops;
            return this;
        }

        @Override
        public Builder setRecipe(final RecipeRef recipe) {
            this.recipe = recipe;
            return this;
        }

        @Override
        public Builder setParent(final IngredientRef parent) {
            this.parent = parent;
            return this;
        }

        Ingredient build() {
            Validate.notNull(id);
            Validate.notBlank(name);
            Validate.notNull(ingredientUnit);
            Validate.notNull(price);
            Validate.isTrue(price >= 0, "Price cannot be negative.");

            return new IngredientImpl(id, name, ingredientUnit, price, existingUnitConversion, existingNutritionFacts, keywords, general, purchasable, edible, manufacturer, shops, recipe, parent, children);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("id", id)
                    .append("name", name)
                    .append("ingredientUnit", ingredientUnit)
                    .append("price", price)
                    .append("unitConversion", unitConversion)
                    .append("keywords", keywords)
                    .toString();
        }
    }
}
