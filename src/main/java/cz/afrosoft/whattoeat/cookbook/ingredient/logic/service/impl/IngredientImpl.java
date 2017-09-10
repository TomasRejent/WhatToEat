package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientUpdateObject;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    private final Set<Keyword> keywords;

    private IngredientImpl(final Integer id, final String name, final IngredientUnit ingredientUnit, final float price, final UnitConversion unitConversion, final Set<Keyword> keywords) {
        this.id = id;
        this.name = name;
        this.ingredientUnit = ingredientUnit;
        this.price = price;
        this.unitConversion = unitConversion;
        this.keywords = keywords;
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
    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public UnitConversion getUnitConversion() {
        return unitConversion;
    }

    @Override
    public Set<Keyword> getKeywords() {
        return keywords;
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

        if (o == null || !(o instanceof Ingredient)) {
            return false;
        }

        Ingredient that = (Ingredient) o;

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

        private Integer id;
        private String name;
        private IngredientUnit ingredientUnit;
        private float price;
        private UnitConversion unitConversion;
        private Set<Keyword> keywords;

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
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
        public UnitConversion getUnitConversion() {
            return unitConversion;
        }

        @Override
        public Set<Keyword> getKeywords() {
            return keywords;
        }

        public Builder setId(final Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder setIngredientUnit(final IngredientUnit ingredientUnit) {
            this.ingredientUnit = ingredientUnit;
            return this;
        }

        @Override
        public Builder setPrice(final float price) {
            this.price = price;
            return this;
        }

        @Override
        public Builder setUnitConversion(final UnitConversion unitConversion) {
            this.unitConversion = unitConversion;
            return this;
        }

        @Override
        public IngredientUpdateObject setUnitConversion(final Float gramsPerPiece, final Float milliliterPerGram, final Float gramsPerPinch, final Float gramsPerCoffeeSpoon, final Float gramsPerSpoon) {
            this.unitConversion = new UnitConversionImpl.Builder()
                    .setGramsPerPiece(gramsPerPiece)
                    .setMilliliterPerGram(milliliterPerGram)
                    .setGramsPerPinch(gramsPerPinch)
                    .setGramsPerCoffeeSpoon(gramsPerCoffeeSpoon)
                    .setGramsPerSpoon(gramsPerSpoon);
            return this;
        }

        @Override
        public Builder setKeywords(final Set<Keyword> keywords) {
            this.keywords = keywords;
            return this;
        }

        Ingredient build() {
            Validate.notNull(id);
            Validate.notBlank(name);
            Validate.notNull(ingredientUnit);

            return new IngredientImpl(id, name, ingredientUnit, price, unitConversion, keywords);
        }

        @Override
        public int compareTo(final Ingredient otherIngredient) {
            return IngredientComparator.INSTANCE.compare(this, otherIngredient);
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
