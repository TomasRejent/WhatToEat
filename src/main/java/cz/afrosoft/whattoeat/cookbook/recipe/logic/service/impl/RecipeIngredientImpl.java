package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

/**
 * Immutable implementation of {@link RecipeIngredient}.
 *
 * @author Tomas Rejent
 */
final class RecipeIngredientImpl implements RecipeIngredient {

    private final Integer id;
    private final float quantity;
    private final Ingredient ingredient;

    private RecipeIngredientImpl(final Integer id, final float quantity, final Ingredient ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public float getQuantity() {
        return quantity;
    }

    @Override
    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeIngredientImpl that = (RecipeIngredientImpl) o;

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
                .append("quantity", quantity)
                .append("ingredientId", ingredient.getId())
                .append("ingredientName", ingredient.getName())
                .toString();
    }

    /**
     * Builder for {@link RecipeIngredient}. Serves as implementation of {@link RecipeIngredientUpdateObject}.
     */
    static class Builder implements RecipeIngredientUpdateObject {

        private final Integer id;
        private Float quantity;
        private Ingredient ingredient;

        public Builder() {
            id = null;
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
        public Optional<Float> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        @Override
        public Optional<Ingredient> getIngredient() {
            return Optional.ofNullable(ingredient);
        }

        @Override
        public Builder setQuantity(final float quantity) {
            Validate.isTrue(quantity >= 0);
            this.quantity = quantity;
            return this;
        }

        @Override
        public Builder setIngredient(final Ingredient ingredient) {
            Validate.notNull(ingredient);
            this.ingredient = ingredient;
            return this;
        }

        public RecipeIngredient build() {
            Validate.notNull(id);
            Validate.isTrue(quantity >= 0);
            Validate.notNull(ingredient);

            return new RecipeIngredientImpl(id, quantity, ingredient);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("id", id)
                    .append("quantity", quantity)
                    .append("ingredient", ingredient)
                    .toString();
        }
    }
}
