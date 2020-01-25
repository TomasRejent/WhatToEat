package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public final class NutritionFactsImpl implements NutritionFacts {

    private final Integer id;
    private final Float energy;
    private final Float fat;
    private final Float saturatedFat;
    private final Float carbohydrate;
    private final Float sugar;
    private final Float protein;
    private final Float salt;
    private final Float fiber;

    public NutritionFactsImpl(final Integer id, final Float energy, final Float fat, final Float saturatedFat, final Float carbohydrate, final Float sugar, final Float protein, final Float salt, final Float fiber) {
        this.id = id;
        this.energy = energy;
        this.fat = fat;
        this.saturatedFat = saturatedFat;
        this.carbohydrate = carbohydrate;
        this.sugar = sugar;
        this.protein = protein;
        this.salt = salt;
        this.fiber = fiber;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Float getEnergy() {
        return energy;
    }

    @Override
    public Float getFat() {
        return fat;
    }

    @Override
    public Float getSaturatedFat() {
        return saturatedFat;
    }

    @Override
    public Float getCarbohydrate() {
        return carbohydrate;
    }

    @Override
    public Float getSugar() {
        return sugar;
    }

    @Override
    public Float getProtein() {
        return protein;
    }

    @Override
    public Float getSalt() {
        return salt;
    }

    @Override
    public Float getFiber() {
        return fiber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NutritionFactsImpl that = (NutritionFactsImpl) o;

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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("energy", energy)
                .append("fat", fat)
                .append("saturatedFat", saturatedFat)
                .append("carbohydrate", carbohydrate)
                .append("sugar", sugar)
                .append("protein", protein)
                .append("salt", salt)
                .append("fiber", fiber)
                .toString();
    }

    static final class Builder implements NutritionFactsUpdateObject{

        private final Integer id;
        private Float energy;
        private Float fat;
        private Float saturatedFat;
        private Float carbohydrate;
        private Float sugar;
        private Float protein;
        private Float salt;
        private Float fiber;

        public Builder() {
            this.id = null;
        }

        public Builder(final Integer id) {
            Validate.notNull(id);
            this.id = id;
        }

        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        @Override
        public Optional<Float> getEnergy() {
            return Optional.ofNullable(energy);
        }

        @Override
        public Builder setEnergy(final Float energy) {
            Validate.isTrue(isValid(energy));
            this.energy = energy;
            return this;
        }

        @Override
        public Optional<Float> getFat() {
            return Optional.ofNullable(fat);
        }

        @Override
        public Builder setFat(final Float fat) {
            Validate.isTrue(isValid(fat));
            this.fat = fat;
            return this;
        }

        @Override
        public Optional<Float> getSaturatedFat() {
            return Optional.ofNullable(saturatedFat);
        }

        @Override
        public Builder setSaturatedFat(final Float saturatedFat) {
            Validate.isTrue(isValidSubsetValue(saturatedFat, fat));
            this.saturatedFat = saturatedFat;
            return this;
        }

        @Override
        public Optional<Float> getCarbohydrate() {
            return Optional.ofNullable(carbohydrate);
        }

        @Override
        public Builder setCarbohydrate(final Float carbohydrate) {
            Validate.isTrue(isValid(carbohydrate));
            this.carbohydrate = carbohydrate;
            return this;
        }

        @Override
        public Optional<Float> getSugar() {
            return Optional.ofNullable(sugar);
        }

        @Override
        public Builder setSugar(final Float sugar) {
            Validate.isTrue(isValidSubsetValue(sugar, carbohydrate));
            this.sugar = sugar;
            return this;
        }

        @Override
        public Optional<Float> getProtein() {
            return Optional.ofNullable(protein);
        }

        @Override
        public Builder setProtein(final Float protein) {
            Validate.isTrue(isValid(protein));
            this.protein = protein;
            return this;
        }

        @Override
        public Optional<Float> getSalt() {
            return Optional.ofNullable(salt);
        }

        @Override
        public Builder setSalt(final Float salt) {
            this.salt = salt;
            return this;
        }

        @Override
        public Optional<Float> getFiber() {
            return Optional.ofNullable(fiber);
        }

        @Override
        public Builder setFiber(final Float fiber) {
            this.fiber = fiber;
            return this;
        }

        private boolean hasUsefulValue(final Float value) {
            return (value != null && value >= 0);
        }

        private boolean hasAnyUsefulValue(final Float... values) {
            Validate.notNull(values);
            return Arrays.stream(values).anyMatch(this::hasUsefulValue);
        }

        @Override
        public boolean hasAnyUsefulValue() {
            return hasAnyUsefulValue(energy, fat, saturatedFat, carbohydrate, sugar, protein, salt, fiber);
        }

        private boolean isValid(Float value){
            return value == null || value >= 0;
        }

        private boolean isValidSubsetValue(Float value, Float whole){
            return isValid(value) && whole != null && whole >= value;
        }

        public NutritionFacts build(){
            return new NutritionFactsImpl(id, energy, fat, saturatedFat, carbohydrate, sugar, protein, salt, fiber);
        }
    }
}
