package cz.afrosoft.whattoeat.cookbook.recipe.data.entity;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.*;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import javax.persistence.*;
import java.time.Duration;
import java.util.Set;

/**
 * Entity for {@link Recipe}
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "RECIPE")
public class RecipeEntity implements Recipe {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PREPARATION")
    private String preparation;

    @Column(name = "RATING")
    private Integer rating;

    @ElementCollection(targetClass = RecipeType.class)
    @JoinTable(name = "RECIPE_TYPES")
    private Set<RecipeType> recipeTypes;

    @Column(name = "TASTE")
    private Taste taste;

    @Column(name = "INGREDIENT_PREPARATION_TIME")
    private Duration ingredientPreparationTime;

    @Column(name = "COOKING_TIME")
    private Duration cookingTime;

    @ManyToMany
    @JoinTable(name = "RECIPE_SIDE_DISHES")
    private Set<RecipeEntity> sideDishes;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeIngredientEntity> recipeIngredients;

    @ManyToMany
    @JoinTable(name = "RECIPE_KEYWORDS")
    private Set<KeywordEntity> keywords;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(final String preparation) {
        this.preparation = preparation;
    }

    @Override
    public int getRating() {
        return rating;
    }

    public void setRating(final Integer rating) {
        this.rating = rating;
    }

    @Override
    public Set<RecipeType> getRecipeTypes() {
        return recipeTypes;
    }

    public void setRecipeTypes(final Set<RecipeType> recipeTypes) {
        this.recipeTypes = recipeTypes;
    }

    @Override
    public Taste getTaste() {
        return taste;
    }

    public void setTaste(final Taste taste) {
        this.taste = taste;
    }

    @Override
    public Duration getIngredientPreparationTime() {
        return ingredientPreparationTime;
    }

    public void setIngredientPreparationTime(final Duration ingredientPreparationTime) {
        this.ingredientPreparationTime = ingredientPreparationTime;
    }

    @Override
    public Duration getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(final Duration cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public Set<? extends Recipe> getSideDishes() {
        return sideDishes;
    }

    public void setSideDishes(final Set<RecipeEntity> sideDishes) {
        this.sideDishes = sideDishes;
    }

    @Override
    public Set<? extends RecipeIngredient> getIngredients() {
        return recipeIngredients;
    }

    public void setIngredients(final Set<RecipeIngredientEntity> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public Set<RecipeIngredientEntity> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(final Set<RecipeIngredientEntity> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    @Override
    public Set<? extends Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(final Set<KeywordEntity> keywords) {
        this.keywords = keywords;
    }

    @Override
    public PreparationTime getTotalPreparationTime() {
        Duration totalDuration = getIngredientPreparationTime().plus(getCookingTime());
        return PreparationTime.fromDuration(totalDuration);
    }
}
