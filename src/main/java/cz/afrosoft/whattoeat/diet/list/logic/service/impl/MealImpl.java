package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;

final class MealImpl implements Meal {

    private final Integer id;
    private final int servings;
    private final RecipeRef recipe;

    private MealImpl(final Integer id, final int servings, final RecipeRef recipe) {
        this.id = id;
        this.servings = servings;
        this.recipe = recipe;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int getServings() {
        return servings;
    }

    @Override
    public RecipeRef getRecipe() {
        return recipe;
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
            .append("recipe", recipe)
            .toString();
    }

    static class Builder implements MealUpdateObject {

        private final Integer id;
        private Integer servings;
        private RecipeRef recipe;

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
        public Optional<Integer> getServings() {
            return Optional.ofNullable(servings);
        }

        @Override
        public Optional<RecipeRef> getRecipe() {
            return Optional.ofNullable(recipe);
        }

        @Override
        public MealUpdateObject setServings(final int servings) {
            Validate.isTrue(servings >= 0, "Number of servings cannot be negative number.");
            this.servings = servings;
            return this;
        }

        @Override
        public MealUpdateObject setRecipe(final RecipeRef recipeRef) {
            Validate.notNull(recipeRef);
            this.recipe = recipe;
            return this;
        }

        Meal build() {
            Validate.notNull(id);
            Validate.notNull(servings);
            Validate.notNull(recipe);

            return new MealImpl(id, servings, recipe);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("id", id)
                .append("servings", servings)
                .append("recipe", recipe)
                .toString();
        }
    }
}
