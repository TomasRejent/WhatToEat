package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.*;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeUpdateObject;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

/**
 * Immutable and comparable implementation of {@link Recipe}. Natural ordering is based on {@link Recipe#getName()}.
 * <p>
 * It is used by {@link cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService} to provide {@link Recipe}
 * instances for front end.
 *
 * @author Tomas Rejent
 */
final class RecipeImpl implements Recipe {

    private final Integer id;
    private final String name;
    private final String preparation;
    private final int rating;
    private final Set<RecipeType> recipeTypes;
    private final Taste taste;
    private final Duration ingredientPreparationTime;
    private final Duration cookingTime;
    private final Set<RecipeIngredient> ingredients;
    private final Set<Recipe> sideDishes;
    private final Set<Keyword> keywords;

    private RecipeImpl(final Integer id, final String name, final String preparation, final Integer rating, final Set<RecipeType> recipeTypes, final Taste taste, final Duration ingredientPreparationTime, final Duration cookingTime, final Set<RecipeIngredient> ingredients, final Set<Recipe> sideDishes, final Set<Keyword> keywords) {
        this.id = id;
        this.name = name;
        this.preparation = preparation;
        this.rating = rating;
        this.recipeTypes = Collections.unmodifiableSet(recipeTypes);
        this.taste = taste;
        this.ingredientPreparationTime = ingredientPreparationTime;
        this.cookingTime = cookingTime;
        this.ingredients = Collections.unmodifiableSet(ingredients);
        this.sideDishes = Collections.unmodifiableSet(sideDishes);
        this.keywords = Collections.unmodifiableSet(keywords);
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
    public String getPreparation() {
        return preparation;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public Set<RecipeType> getRecipeTypes() {
        return recipeTypes;
    }

    @Override
    public Taste getTaste() {
        return taste;
    }

    @Override
    public Duration getIngredientPreparationTime() {
        return ingredientPreparationTime;
    }

    @Override
    public Duration getCookingTime() {
        return cookingTime;
    }

    @Override
    public Set<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    @Override
    public Set<Recipe> getSideDishes() {
        return sideDishes;
    }

    @Override
    public Set<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public PreparationTime getTotalPreparationTime() {
        Duration totalDuration = getIngredientPreparationTime().plus(getCookingTime());
        return PreparationTime.fromDuration(totalDuration);
    }

    @Override
    public int compareTo(final Recipe otherRecipe) {
        return RecipeComparator.INSTANCE.compare(this, otherRecipe);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeImpl recipe = (RecipeImpl) o;

        return new EqualsBuilder()
                .append(id, recipe.id)
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
                .append("name", name)
                .toString();
    }

    static final class Builder implements RecipeUpdateObject {

        private Integer id;
        private String name;
        private String preparation;
        private int rating;
        private Set<RecipeType> recipeTypes;
        private Taste taste;
        private Duration ingredientPreparationTime;
        private Duration cookingTime;
        private Set<RecipeIngredient> ingredients;
        private Set<Recipe> sideDishes;
        private Set<Keyword> keywords;
        private Set<Cookbook> cookbooks;

        @Override
        public Integer getId() {
            return id;
        }

        public Builder setId(final Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        @Override
        public String getPreparation() {
            return preparation;
        }

        @Override
        public Builder setPreparation(final String preparation) {
            this.preparation = preparation;
            return this;
        }

        @Override
        public int getRating() {
            return rating;
        }

        @Override
        public Builder setRating(final int rating) {
            this.rating = rating;
            return this;
        }

        @Override
        public Set<RecipeType> getRecipeTypes() {
            return recipeTypes;
        }

        @Override
        public Builder setRecipeTypes(final Set<RecipeType> recipeTypes) {
            this.recipeTypes = recipeTypes;
            return this;
        }

        @Override
        public Taste getTaste() {
            return taste;
        }

        @Override
        public Builder setTaste(final Taste taste) {
            this.taste = taste;
            return this;
        }

        @Override
        public Duration getIngredientPreparationTime() {
            return ingredientPreparationTime;
        }

        @Override
        public Builder setIngredientPreparationTime(final Duration ingredientPreparationTime) {
            this.ingredientPreparationTime = ingredientPreparationTime;
            return this;
        }

        @Override
        public Duration getCookingTime() {
            return cookingTime;
        }

        @Override
        public Builder setCookingTime(final Duration cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        @Override
        public Set<RecipeIngredient> getIngredients() {
            return ingredients;
        }

        @Override
        public Builder setIngredients(final Set<RecipeIngredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        @Override
        public Set<Recipe> getSideDishes() {
            return sideDishes;
        }

        @Override
        public Builder setSideDishes(final Set<Recipe> sideDishes) {
            this.sideDishes = sideDishes;
            return this;
        }

        @Override
        public Set<Keyword> getKeywords() {
            return keywords;
        }

        @Override
        public Builder setKeywords(final Set<Keyword> keywords) {
            this.keywords = keywords;
            return this;
        }

        public Set<Cookbook> getCookbooks() {
            return cookbooks;
        }

        @Override
        public Builder setCookbooks(final Set<Cookbook> cookbooks) {
            this.cookbooks = cookbooks;
            return this;
        }

        @Override
        public PreparationTime getTotalPreparationTime() {
            Duration totalDuration = getIngredientPreparationTime().plus(getCookingTime());
            return PreparationTime.fromDuration(totalDuration);
        }

        @Override
        public int compareTo(final Recipe otherRecipe) {
            return RecipeComparator.INSTANCE.compare(this, otherRecipe);
        }

        public Recipe build() {
            Validate.notNull(id);
            Validate.notBlank(name);
            Validate.notBlank(preparation);
            Validate.notEmpty(recipeTypes);
            Validate.notNull(taste);
            Validate.notNull(ingredientPreparationTime);
            Validate.notNull(cookingTime);
            Validate.notEmpty(ingredients);

            return new RecipeImpl(id, name, preparation, rating, recipeTypes, taste, ingredientPreparationTime, cookingTime, ingredients, sideDishes, keywords);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("name", name)
                    .append("preparation", preparation)
                    .append("rating", rating)
                    .append("recipeTypes", recipeTypes)
                    .append("taste", taste)
                    .append("ingredientPreparationTime", ingredientPreparationTime)
                    .append("cookingTime", cookingTime)
                    .append("ingredients", ingredients)
                    .append("sideDishes", sideDishes)
                    .append("keywords", keywords)
                    .append("cookbooks", cookbooks)
                    .toString();
        }
    }
}
