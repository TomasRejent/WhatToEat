package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;

import java.util.Optional;

/**
 * Update object for {@link RecipeIngredient}. Serves for its creation or editing.
 *
 * @author Tomas Rejent
 */
public interface RecipeIngredientUpdateObject {

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity or optional filled with id of entity
     * which is edited.
     */
    Optional<Integer> getId();

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity and value was not yet set by
     * {@link #setQuantity(float)}. Otherwise filled optional with quantity of linked ingredient. This is original value
     * from edited entity if {@link #setQuantity(float)} was not used. Otherwise it is value set by this method.
     */
    Optional<Float> getQuantity();

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity and value was not yet set by
     * {@link #setIngredient(Ingredient)}. Otherwise filled optional with linked ingredient. This is original value
     * from edited entity if {@link #setIngredient(Ingredient)} was not used. Otherwise it is value set by this method.
     */
    Optional<Ingredient> getIngredient();

    /**
     * @param quantity Quantity of ingredient for linked recipe. Cannot be negative value. Can be zero, which means
     *                 that quantity is not determined exactly, but ingredient is still needed for recipe.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeIngredientUpdateObject setQuantity(float quantity);

    /**
     * @param ingredient (NotNull) Ingredient for which this recipe ingredient holds quantity. Ingredient does not have
     *                   reference object, because it is displayed with majority of data (name, unit, price, keywords)
     *                   everywhere. This is why full ingredient object is needed by this setter.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeIngredientUpdateObject setIngredient(Ingredient ingredient);

}
