package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.*;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientUpdateObject;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeUpdateObject;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
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
    private final Set<RecipeIngredientRef> ingredients;
    private final Set<RecipeRef> sideDishes;
    private final Set<Keyword> keywords;
    private final Set<CookbookRef> cookbooks;

    private RecipeImpl(final Integer id, final String name, final String preparation, final Integer rating, final Set<RecipeType> recipeTypes,
                       final Taste taste, final Duration ingredientPreparationTime, final Duration cookingTime,
                       final Set<RecipeIngredientRef> ingredients, final Set<RecipeRef> sideDishes, final Set<Keyword> keywords,
                       final Set<CookbookRef> cookbooks) {
        this.id = id;
        this.name = name;
        this.preparation = preparation;
        this.rating = rating;
        this.recipeTypes = Collections.unmodifiableSet(new HashSet<>(recipeTypes));
        this.taste = taste;
        this.ingredientPreparationTime = ingredientPreparationTime;
        this.cookingTime = cookingTime;
        this.ingredients = Collections.unmodifiableSet(new HashSet<>(ingredients));
        this.sideDishes = Collections.unmodifiableSet(new HashSet<>(sideDishes));
        this.keywords = Collections.unmodifiableSet(new HashSet<>(keywords));
        this.cookbooks = Collections.unmodifiableSet(new HashSet<>(cookbooks));
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
    public Set<RecipeIngredientRef> getIngredients() {
        return ingredients;
    }

    @Override
    public Set<RecipeRef> getSideDishes() {
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
    public Set<CookbookRef> getCookbooks() {
        return cookbooks;
    }

    @Override
    public int compareTo(final RecipeRef otherRecipe) {
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

        private final Integer id;
        private String name;
        private String preparation;
        private Integer rating;
        private Set<RecipeType> recipeTypes;
        private Taste taste;
        private Duration ingredientPreparationTime;
        private Duration cookingTime;
        private Set<RecipeIngredientRef> existingIngredients;
        private Set<RecipeIngredientUpdateObject> ingredients;
        private Set<RecipeRef> sideDishes;
        private Set<Keyword> keywords;
        private Set<CookbookRef> cookbooks;

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
        public Optional<String> getPreparation() {
            return Optional.ofNullable(preparation);
        }

        @Override
        public Optional<Integer> getRating() {
            return Optional.ofNullable(rating);
        }

        @Override
        public Set<RecipeType> getRecipeTypes() {
            return Optional.ofNullable(recipeTypes).orElse(Collections.emptySet());
        }

        @Override
        public Optional<Taste> getTaste() {
            return Optional.ofNullable(taste);
        }

        @Override
        public Optional<Duration> getIngredientPreparationTime() {
            return Optional.ofNullable(ingredientPreparationTime);
        }

        @Override
        public Optional<Duration> getCookingTime() {
            return Optional.ofNullable(cookingTime);
        }

        @Override
        public Set<RecipeRef> getSideDishes() {
            return Optional.ofNullable(sideDishes).orElse(Collections.emptySet());
        }

        @Override
        public Set<RecipeIngredientUpdateObject> getIngredients() {
            return Optional.ofNullable(ingredients).orElse(Collections.emptySet());
        }

        @Override
        public Set<CookbookRef> getCookbooks() {
            return Optional.ofNullable(cookbooks).orElse(Collections.emptySet());
        }

        @Override
        public Set<Keyword> getKeywords() {
            return Optional.ofNullable(keywords).orElse(Collections.emptySet());
        }

        @Override
        public RecipeUpdateObject setName(final String name) {
            Validate.notBlank(name);
            this.name = name;
            return this;
        }

        @Override
        public RecipeUpdateObject setPreparation(final String preparation) {
            Validate.notBlank(preparation);
            this.preparation = preparation;
            return this;
        }

        @Override
        public RecipeUpdateObject setRating(final int rating) {
            Validate.isTrue(validateRating(rating));
            this.rating = rating;
            return this;
        }

        @Override
        public RecipeUpdateObject setRecipeTypes(final Set<RecipeType> recipeTypes) {
            Validate.notEmpty(recipeTypes);
            Validate.noNullElements(recipeTypes);
            this.recipeTypes = recipeTypes;
            return this;
        }

        @Override
        public RecipeUpdateObject setTaste(final Taste taste) {
            Validate.notNull(taste);
            this.taste = taste;
            return this;
        }

        @Override
        public RecipeUpdateObject setIngredientPreparationTime(final Duration ingredientPreparationTime) {
            Validate.notNull(ingredientPreparationTime);
            this.ingredientPreparationTime = ingredientPreparationTime;
            return this;
        }

        @Override
        public RecipeUpdateObject setCookingTime(final Duration cookingTime) {
            Validate.notNull(cookingTime);
            this.cookingTime = cookingTime;
            return this;
        }

        @Override
        public RecipeUpdateObject setIngredients(final Set<RecipeIngredientUpdateObject> ingredients) {
            Validate.notEmpty(ingredients);
            Validate.noNullElements(ingredients);
            this.ingredients = ingredients;
            return this;
        }

        /**
         * Sets recipe ingredients. These ingredients are used for building {@link Recipe} instance and are not intended
         * for editing. If you want to edit recipe ingredient then use {@link #setIngredients(Set)}.
         *
         * @param existingIngredients (NotEmpty)(NoNullElements) Recipe ingredients to set.
         * @return (NotNull) This createOrUpdate object so setter calls can be chained.
         */
        public RecipeUpdateObject setExistingIngredients(final Set<RecipeIngredientRef> existingIngredients) {
            Validate.notEmpty(existingIngredients);
            Validate.noNullElements(existingIngredients);
            this.existingIngredients = existingIngredients;
            return this;
        }

        @Override
        public RecipeUpdateObject setSideDishes(final Set<RecipeRef> sideDishes) {
            Validate.noNullElements(sideDishes);
            this.sideDishes = sideDishes;
            return this;
        }

        @Override
        public RecipeUpdateObject setCookbooks(final Set<CookbookRef> cookbooks) {
            Validate.notEmpty(cookbooks);
            Validate.noNullElements(cookbooks);
            this.cookbooks = cookbooks;
            return this;
        }

        @Override
        public RecipeUpdateObject setKeywords(final Set<Keyword> keywords) {
            Validate.noNullElements(keywords);
            this.keywords = keywords;
            return this;
        }

        private boolean validateRating(final Integer rating) {
            return rating != null && rating > 0 && rating <= 10;
        }

        Recipe build() {
            Validate.notNull(id);
            Validate.notBlank(name);
            Validate.notBlank(preparation);
            Validate.isTrue(validateRating(rating));
            Validate.noNullElements(recipeTypes);
            Validate.notEmpty(recipeTypes);
            Validate.notNull(taste);
            Validate.notNull(ingredientPreparationTime);
            Validate.notNull(cookingTime);
            Validate.noNullElements(existingIngredients);
            Validate.notEmpty(existingIngredients);
            Validate.noNullElements(sideDishes);
            Validate.noNullElements(keywords);
            Validate.noNullElements(cookbooks);
            Validate.notEmpty(cookbooks);

            return new RecipeImpl(id, name, preparation, rating, recipeTypes, taste, ingredientPreparationTime, cookingTime, existingIngredients, sideDishes, keywords, cookbooks);
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
                    .append("existingIngredients", existingIngredients)
                    .append("ingredients", ingredients)
                    .append("sideDishes", sideDishes)
                    .append("keywords", keywords)
                    .append("cookbooks", cookbooks)
                    .toString();
        }
    }
}
