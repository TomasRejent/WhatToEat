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
public class RecipeIngredientEntity implements RecipeIngredient {

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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(final Float quantity) {
        this.quantity = quantity;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(final RecipeEntity recipe) {
        this.recipe = recipe;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }

    public void setIngredient(final IngredientEntity ingredient) {
        this.ingredient = ingredient;
    }
}
