package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;

import java.util.Optional;
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
     * Check if ingredient with specified name exists. Check is case sensitive.
     *
     * @param ingredientName (NotEmpty) Name to search.
     * @return True if ingredient exist. False otherwise.
     */
    boolean existByName(String ingredientName);

    /**
     * Finds ingredient by name.
     *
     * @param ingredientName (NotEmpty) Name of ingredient.
     * @return (NotNull) Optional with ingredient with specified name or empty optional if such ingredient does not exist.
     */
    Optional<Ingredient> findByName(String ingredientName);

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
     * @return (NotNull) Ingredient with updated values.
     */
    Ingredient createOrUpdate(IngredientUpdateObject ingredientChanges);

    /**
     * Converts entity to Ingredient.
     *
     * @param entity (NotNull) Entity to convert.
     * @return (NotNull)
     */
    Ingredient entityToIngredient(final IngredientEntity entity);

}
