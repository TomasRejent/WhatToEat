package cz.afrosoft.whattoeat.cookbook.recipe.data.entity;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.*;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;

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
public class RecipeEntity {

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

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(final String preparation) {
        this.preparation = preparation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final Integer rating) {
        this.rating = rating;
    }

    public Set<RecipeType> getRecipeTypes() {
        return recipeTypes;
    }

    public void setRecipeTypes(final Set<RecipeType> recipeTypes) {
        this.recipeTypes = recipeTypes;
    }

    public Taste getTaste() {
        return taste;
    }

    public void setTaste(final Taste taste) {
        this.taste = taste;
    }

    public Duration getIngredientPreparationTime() {
        return ingredientPreparationTime;
    }

    public void setIngredientPreparationTime(final Duration ingredientPreparationTime) {
        this.ingredientPreparationTime = ingredientPreparationTime;
    }

    public Duration getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(final Duration cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Set<RecipeEntity> getSideDishes() {
        return sideDishes;
    }

    public void setSideDishes(final Set<RecipeEntity> sideDishes) {
        this.sideDishes = sideDishes;
    }

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

    public Set<KeywordEntity> getKeywords() {
        return keywords;
    }

    public void setKeywords(final Set<KeywordEntity> keywords) {
        this.keywords = keywords;
    }

    public PreparationTime getTotalPreparationTime() {
        Duration totalDuration = getIngredientPreparationTime().plus(getCookingTime());
        return PreparationTime.fromDuration(totalDuration);
    }
}
