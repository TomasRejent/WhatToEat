package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;

import java.util.Set;

/**
 * Service providing methods for operating on {@link Ingredient}.
 *
 * @author Tomas Rejent
 */
public interface IngredientService {

    /**
     * @return (NotNull) Return all ingredients defined in application.
     */
    Set<Ingredient> getAllIngredients();

    /**
     * Deletes specified ingredient.
     *
     * @param ingredient (NotNull) Ingredient to be deleted.
     */
    void delete(Ingredient ingredient);

    /**
     * Gets createOrUpdate object for new Ingredient. After data are filled it can be persisted using
     * {@link #createOrUpdate(IngredientUpdateObject)} method.
     *
     * @return (NotNull)
     */
    IngredientUpdateObject getCreateObject();

    /**
     * Gets update object for specified ingredient. Update object is used to modify ingredient.
     * Changes are not persisted until {@link #createOrUpdate(IngredientUpdateObject)} is called.
     *
     * @param ingredient (NotNull) Ingredient to modify.
     * @return (NotNull) Update object which enables you to specify changes to ingredient.
     */
    IngredientUpdateObject getUpdateObject(Ingredient ingredient);

    /**
     * Applies changes specified by ingredientChanges to ingredient for which ingredientChanges was constructed.
     * It can also be used to persist new ingredients.
     *
     * @param ingredientChanges (NotNull) Changes to persist.
     * @return (NotNull) Cookbook with updated values.
     */
    Ingredient createOrUpdate(IngredientUpdateObject ingredientChanges);

}
