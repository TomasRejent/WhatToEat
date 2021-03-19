package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

final class MealImpl implements Meal {

    private final Integer id;
    private final float servings;
    private final RecipeRef recipe;
    private final IngredientRef ingredient;
    private final int amount;

    private MealImpl(final Integer id, final float servings, final RecipeRef recipe, final IngredientRef ingredient, int amount) {
        this.id = id;
        this.servings = servings;
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public float getServings() {
        return servings;
    }

    @Override
    public RecipeRef getRecipe() {
        return recipe;
    }

    @Override
    public IngredientRef getIngredient() {
        return ingredient;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        final MealImpl meal = (MealImpl) o;

        return new EqualsBuilder()
            .append(id, meal.id)
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
            .append("servings", servings)
            .append("amount", amount)
            .append("recipe", recipe)
            .append("ingredient", ingredient)
            .toString();
    }

    static class Builder implements MealUpdateObject {

        private final Integer id;
        private Float servings;
        private RecipeRef recipe;
        private IngredientRef ingredient;
        private int amount;

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
        public Optional<Float> getServings() {
            return Optional.ofNullable(servings);
        }

        @Override
        public Optional<RecipeRef> getRecipe() {
            return Optional.ofNullable(recipe);
        }

        @Override
        public Optional<IngredientRef> getIngredient() {
            return Optional.ofNullable(ingredient);
        }

        @Override
        public Optional<Integer> getAmount() {
            return Optional.of(amount);
        }

        @Override
        public Builder setIngredient(final IngredientRef ingredientRef) {
            this.ingredient = ingredientRef;
            return this;
        }

        @Override
        public Builder setAmount(final int amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public Builder setServings(final float servings) {
            Validate.isTrue(servings >= 0, "Number of servings cannot be negative number.");
            this.servings = servings;
            return this;
        }

        @Override
        public Builder setRecipe(final RecipeRef recipeRef) {
            this.recipe = recipeRef;
            return this;
        }

        Meal build() {
            Validate.notNull(id);
            Validate.notNull(servings);
            // only one of recipe or ingredient can be set
            Validate.isTrue(recipe != null || ingredient != null);
            Validate.isTrue(recipe == null || ingredient == null);

            return new MealImpl(id, servings, recipe, ingredient, amount);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("id", id)
                .append("servings", servings)
                .append("amount", amount)
                .append("recipe", recipe)
                .append("ingredient", ingredient)
                .toString();
        }
    }
}
