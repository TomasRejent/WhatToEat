package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;

/**
 * @author Tomas Rejent
 */
public interface RecipeService {

    /**
     * Gets recipe by its id.
     *
     * @param id (NotNull)
     * @return (NotNull) Recipe wit specified id.
     * @throws javax.persistence.EntityNotFoundException When recipe with specified id does not exist.
     */
    Recipe getRecipeById(Integer id);

    /**
     * Finds recipe by name.
     *
     * @param name (NotNull) Name to search.
     * @return (NotNull) Optional with recipe or empty optional if recipe with such name does not exist.
     */
    Optional<Recipe> findRecipeByName(String name);

    /**
     * @return (NotNull) Return all recipes defined in application.
     */
    Set<Recipe> getAllRecipes();

    /**
     * Gets recipes matching specified filter.
     *
     * @param filter (NotNull)
     * @return (NotNull)
     */
    Set<Recipe> getFilteredRecipes(RecipeFilter filter);

    /**
     * @return (NotNull) Return set of references to recipes which contains side dish type.
     */
    Set<RecipeRef> getAllSideDishRefs();

    /**
     * Deletes specified recipe.
     *
     * @param recipe (NotNull) Recipe to delete.
     */
    void delete(Recipe recipe);

    /**
     * Gets createOrUpdate object for new Recipe. After data are filled it can be persisted using
     * {@link #createOrUpdate(RecipeUpdateObject)} method.
     *
     * @return (NotNull)
     */
    RecipeUpdateObject getCreateObject();

    /**
     * Gets update object for specified ingredient. Update object is used to modify ingredient.
     * Changes are not persisted until {@link #createOrUpdate(RecipeUpdateObject)} is called.
     *
     * @param recipe (NotNull) Recipe to modify.
     * @return (NotNull)
     */
    RecipeUpdateObject getUpdateObject(Recipe recipe);

    /**
     * Applies changes specified by recipeChanges to recipe for which recipeChanges was constructed.
     * It can also be used to persist new recipes.
     *
     * @param recipeChanges (NotNull) Changes to persist.
     * @return (NotNull) Recipe with updated values.
     */
    Recipe createOrUpdate(RecipeUpdateObject recipeChanges);

    /**
     * Creates new object for creating recipe ingredient. After object is filled with data it can be set to recipe by
     * {@link RecipeUpdateObject#setIngredients(Set)}. Recipe ingredients are persisted together with recipe.
     *
     * @return (NotNull)
     */
    RecipeIngredientUpdateObject getRecipeIngredientCreateObject();

    /**
     * Converts collection of recipe ingredient references to their update objects.
     *
     * @param recipeIngredients (Nullable)
     * @return (Nullable)
     */
    Collection<RecipeIngredientUpdateObject> toUpdateObjects(Collection<RecipeIngredientRef> recipeIngredients);

    /**
     * Loads {@link RecipeIngredient} for {@link RecipeIngredientRef}.
     *
     * @param references (NotNull) References to load.
     * @return (NotNull) Loaded references.
     */
    Collection<RecipeIngredient> loadRecipeIngredients(Collection<RecipeIngredientRef> references);

}
