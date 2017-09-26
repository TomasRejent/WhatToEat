package cz.afrosoft.whattoeat.cookbook.recipe.data.entity;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;

import javax.persistence.*;

/**
 * Entity for {@link RecipeIngredient}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "RECIPE_INGREDIENT")
public class RecipeIngredientEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "QUANTITY", nullable = false)
    private Float quantity;

    @ManyToOne(optional = false)
    private RecipeEntity recipe;

    @ManyToOne(optional = false)
    private IngredientEntity ingredient;

    public Integer getId() {
        return id;
    }

    public RecipeIngredientEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public float getQuantity() {
        return quantity;
    }

    public RecipeIngredientEntity setQuantity(final Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public RecipeIngredientEntity setRecipe(final RecipeEntity recipe) {
        this.recipe = recipe;
        return this;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }

    public RecipeIngredientEntity setIngredient(final IngredientEntity ingredient) {
        this.ingredient = ingredient;
        return this;
    }
}