package cz.afrosoft.whattoeat.cookbook.recipe.data.entity;

import org.apache.commons.lang3.Validate;

import java.time.Duration;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;

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

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PREPARATION")
    private String preparation;

    @Column(name = "RATING", nullable = false)
    private Integer rating;

    @ElementCollection(targetClass = RecipeType.class, fetch = FetchType.EAGER)
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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeIngredientEntity> recipeIngredients;

    @ManyToMany
    @JoinTable(name = "RECIPE_KEYWORDS")
    private Set<KeywordEntity> keywords;

    @ManyToMany
    @JoinTable(name = "COOKBOOK_RECIPES")
    private Set<CookbookEntity> cookbooks;

    public Integer getId() {
        return id;
    }

    public RecipeEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeEntity setPreparation(final String preparation) {
        this.preparation = preparation;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public RecipeEntity setRating(final Integer rating) {
        this.rating = rating;
        return this;
    }

    public Set<RecipeType> getRecipeTypes() {
        return recipeTypes;
    }

    public RecipeEntity setRecipeTypes(final Set<RecipeType> recipeTypes) {
        this.recipeTypes = recipeTypes;
        return this;
    }

    public Taste getTaste() {
        return taste;
    }

    public RecipeEntity setTaste(final Taste taste) {
        this.taste = taste;
        return this;
    }

    public Duration getIngredientPreparationTime() {
        return ingredientPreparationTime;
    }

    public RecipeEntity setIngredientPreparationTime(final Duration ingredientPreparationTime) {
        this.ingredientPreparationTime = ingredientPreparationTime;
        return this;
    }

    public Duration getCookingTime() {
        return cookingTime;
    }

    public RecipeEntity setCookingTime(final Duration cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public Set<RecipeEntity> getSideDishes() {
        return sideDishes;
    }

    public RecipeEntity setSideDishes(final Set<RecipeEntity> sideDishes) {
        this.sideDishes = sideDishes;
        return this;
    }

    public Set<RecipeIngredientEntity> getIngredients() {
        return recipeIngredients;
    }

    public RecipeEntity setIngredients(final Set<RecipeIngredientEntity> recipeIngredients) {
        Validate.noNullElements(recipeIngredients);
        this.recipeIngredients = recipeIngredients;
        this.recipeIngredients.forEach(recipeIngredient -> recipeIngredient.setRecipe(this));
        return this;
    }

    public Set<KeywordEntity> getKeywords() {
        return keywords;
    }

    public RecipeEntity setKeywords(final Set<KeywordEntity> keywords) {
        this.keywords = keywords;
        return this;
    }

    public Set<CookbookEntity> getCookbooks() {
        return cookbooks;
    }

    public RecipeEntity setCookbooks(final Set<CookbookEntity> cookbooks) {
        this.cookbooks = cookbooks;
        return this;
    }
}
