package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.Optional;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.UnitConversionUpdateObject;

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

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnitConversionImpl that = (UnitConversionImpl) o;

        return new EqualsBuilder()
                .append(id, that.id)
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

        private final Integer id;
        private Float gramsPerPiece;
        private Float milliliterPerGram;
        private Float gramsPerPinch;
        private Float gramsPerCoffeeSpoon;
        private Float gramsPerSpoon;

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
        public Optional<Float> getGramsPerPiece() {
            return Optional.ofNullable(gramsPerPiece);
        }

        public Builder setGramsPerPiece(final Float gramsPerPiece) {
            validateNotNegative(gramsPerPiece, "gramsPerPiece");
            this.gramsPerPiece = gramsPerPiece;
            return this;
        }

        @Override
        public Optional<Float> getMilliliterPerGram() {
            return Optional.ofNullable(milliliterPerGram);
        }

        public Builder setMilliliterPerGram(final Float milliliterPerGram) {
            validateNotNegative(milliliterPerGram, "milliliterPerGram");
            this.milliliterPerGram = milliliterPerGram;
            return this;
        }

        @Override
        public Optional<Float> getGramsPerPinch() {
            return Optional.ofNullable(gramsPerPinch);
        }

        public Builder setGramsPerPinch(final Float gramsPerPinch) {
            validateNotNegative(gramsPerPinch, "gramsPerPinch");
            this.gramsPerPinch = gramsPerPinch;
            return this;
        }

        @Override
        public Optional<Float> getGramsPerCoffeeSpoon() {
            return Optional.ofNullable(gramsPerCoffeeSpoon);
        }

        public Builder setGramsPerCoffeeSpoon(final Float gramsPerCoffeeSpoon) {
            validateNotNegative(gramsPerCoffeeSpoon, "gramsPerCoffeeSpoon");
            this.gramsPerCoffeeSpoon = gramsPerCoffeeSpoon;
            return this;
        }

        @Override
        public Optional<Float> getGramsPerSpoon() {
            return Optional.ofNullable(gramsPerSpoon);
        }

        public Builder setGramsPerSpoon(final Float gramsPerSpoon) {
            validateNotNegative(gramsPerSpoon, "gramsPerSpoon");
            this.gramsPerSpoon = gramsPerSpoon;
            return this;
        }

        private void validateNotNegative(final Float value, final String fieldName) {
            if (value != null && value < 0) {
                throw new IllegalArgumentException(String.format("Conversion rate for %s cannot be negative.", fieldName));
            }
        }

        private boolean hasUsefulValue(final Float value) {
            return (value != null && value > 0);
        }

        private boolean hasAnyUsefulValue(final Float... values) {
            Validate.notNull(values);
            return Arrays.stream(values).anyMatch(this::hasUsefulValue);
        }

        /**
         * @return (Nullable) Conversion info or null when no values other than null or zero were set.
         */
        public UnitConversion build() {
            if (hasAnyUsefulValue(gramsPerPiece, milliliterPerGram, gramsPerPinch, gramsPerCoffeeSpoon, gramsPerSpoon)) {
                Validate.notNull(id);
                return new UnitConversionImpl(id, gramsPerPiece, milliliterPerGram, gramsPerPinch, gramsPerCoffeeSpoon, gramsPerSpoon);
            } else {
                return null;
            }
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
