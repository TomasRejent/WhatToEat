package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.UnitConversionUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link UnitConversion}.
 * <p>
 * Is used by {@link IngredientService} to provide {@link UnitConversion} instances for front end.
 *
 * @author Tomas Rejent
 */
final class UnitConversionImpl implements UnitConversion {

    private final Integer id;
    private final Float gramsPerPiece;
    private final Float milliliterPerGram;
    private final Float gramsPerPinch;
    private final Float gramsPerCoffeeSpoon;
    private final Float gramsPerSpoon;

    private UnitConversionImpl(final Integer id, final Float gramsPerPiece, final Float milliliterPerGram, final Float gramsPerPinch, final Float gramsPerCoffeeSpoon, final Float gramsPerSpoon) {
        this.id = id;
        this.gramsPerPiece = gramsPerPiece;
        this.milliliterPerGram = milliliterPerGram;
        this.gramsPerPinch = gramsPerPinch;
        this.gramsPerCoffeeSpoon = gramsPerCoffeeSpoon;
        this.gramsPerSpoon = gramsPerSpoon;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Float getGramsPerPiece() {
        return gramsPerPiece;
    }

    @Override
    public Float getMilliliterPerGram() {
        return milliliterPerGram;
    }

    @Override
    public Float getGramsPerPinch() {
        return gramsPerPinch;
    }

    @Override
    public Float getGramsPerCoffeeSpoon() {
        return gramsPerCoffeeSpoon;
    }

    @Override
    public Float getGramsPerSpoon() {
        return gramsPerSpoon;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof UnitConversion)) {
            return false;
        }

        UnitConversion that = (UnitConversion) o;

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
                .append("gramsPerPiece", gramsPerPiece)
                .append("milliliterPerGram", milliliterPerGram)
                .append("gramsPerPinch", gramsPerPinch)
                .append("gramsPerCoffeeSpoon", gramsPerCoffeeSpoon)
                .append("gramsPerSpoon", gramsPerSpoon)
                .toString();
    }

    static final class Builder implements UnitConversionUpdateObject {

        private Integer id;
        private Float gramsPerPiece;
        private Float milliliterPerGram;
        private Float gramsPerPinch;
        private Float gramsPerCoffeeSpoon;
        private Float gramsPerSpoon;

        @Override
        public Integer getId() {
            return id;
        }

        public Builder setId(final Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public Float getGramsPerPiece() {
            return gramsPerPiece;
        }

        public Builder setGramsPerPiece(final Float gramsPerPiece) {
            this.gramsPerPiece = gramsPerPiece;
            return this;
        }

        @Override
        public Float getMilliliterPerGram() {
            return milliliterPerGram;
        }

        public Builder setMilliliterPerGram(final Float milliliterPerGram) {
            this.milliliterPerGram = milliliterPerGram;
            return this;
        }

        @Override
        public Float getGramsPerPinch() {
            return gramsPerPinch;
        }

        public Builder setGramsPerPinch(final Float gramsPerPinch) {
            this.gramsPerPinch = gramsPerPinch;
            return this;
        }

        @Override
        public Float getGramsPerCoffeeSpoon() {
            return gramsPerCoffeeSpoon;
        }

        public Builder setGramsPerCoffeeSpoon(final Float gramsPerCoffeeSpoon) {
            this.gramsPerCoffeeSpoon = gramsPerCoffeeSpoon;
            return this;
        }

        @Override
        public Float getGramsPerSpoon() {
            return gramsPerSpoon;
        }

        public Builder setGramsPerSpoon(final Float gramsPerSpoon) {
            this.gramsPerSpoon = gramsPerSpoon;
            return this;
        }

        public UnitConversion build() {
            Validate.notNull(id);
            return new UnitConversionImpl(id, gramsPerPiece, milliliterPerGram, gramsPerPinch, gramsPerCoffeeSpoon, gramsPerSpoon);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("id", id)
                    .append("gramsPerPiece", gramsPerPiece)
                    .append("milliliterPerGram", milliliterPerGram)
                    .append("gramsPerPinch", gramsPerPinch)
                    .append("gramsPerCoffeeSpoon", gramsPerCoffeeSpoon)
                    .append("gramsPerSpoon", gramsPerSpoon)
                    .toString();
        }
    }
}
