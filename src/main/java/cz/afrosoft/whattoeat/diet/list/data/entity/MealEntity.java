package cz.afrosoft.whattoeat.diet.list.data.entity;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * Represents one meal in diet. This adds number of servings (quantity) property to recipe. Quantity is needed for
 * generating of shopping list.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "MEAL")
public class MealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "SERVINGS")
    private Float servings;

    @Column(name = "AMOUNT")
    private Integer amount;

    @ManyToOne
    private RecipeEntity recipe;

    @ManyToOne
    private IngredientEntity ingredient;

    public Integer getId() {
        return id;
    }

    public MealEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public Float getServings() {
        return servings;
    }

    public MealEntity setServings(final Float servings) {
        this.servings = servings;
        return this;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public MealEntity setRecipe(final RecipeEntity recipe) {
        this.recipe = recipe;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public MealEntity setAmount(final Integer amount) {
        this.amount = amount;
        return this;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }

    public MealEntity setIngredient(final IngredientEntity ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .toString();
    }
}
