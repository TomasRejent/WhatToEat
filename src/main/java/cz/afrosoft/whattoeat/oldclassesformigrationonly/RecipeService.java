/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.core.data.exception.NotFoundException;

import java.util.Set;

/**
 * Service which handles work with {@link RecipeOld} and related entities on business layer.
 *
 * @author Tomas Rejent
 */
public interface RecipeService {

    /**
     * Gets recipe by key.
     *
     * @param recipeKey (NotNull) Key of recipe.
     * @return (NotNull) Recipe with specified key.
     * @throws NotFoundException If recipe with given key does not exist.
     */
    RecipeOld getRecipeByKey(String recipeKey);

    /**
     * Gets recipe by name.
     *
     * @param name (NotNull) Name of recipe.
     * @return (NotNull) Recipe with specified name.
     * @throws NotFoundException If recipe with given name does not exist.
     */
    RecipeOld getRecipeByName(String name);

    /**
     * Gets all recipes.
     *
     * @return (NotNull)(ReadOnly) Set of recipes.
     */
    Set<RecipeOld> getAllRecipes();

    /**
     * Gets all recipes which contains type {@link OldRecipeType#SIDE_DISH}.
     *
     * @return (NotNull)(ReadOnly)
     */
    Set<RecipeOld> getAllSideDishes();

    /**
     * Add new Recipe.
     *
     * @param recipe (NotNull) Recipe to add.
     */
    void addRecipe(RecipeOld recipe);

    /**
     * Saves specified recipe to persistent storage. It must already exist in storage.
     *
     * @param recipe (Required) Recipe to createOrUpdate.
     */
    void updateRecipe(RecipeOld recipe);

    /**
     * Deletes specified recipe from storage.
     *
     * @param recipe (Required) recipe to delete.
     */
    void deleteRecipe(RecipeOld recipe);

    /**
     * @return (NotNull)(ReadOnly) Set of keywords from all recipes.
     */
    Set<String> getAllRecipeKeywords();

    /**
     * @return (NotNull)(ReadOnly) Set containing names of all recipes.
     */
    Set<String> getAllRecipeNames();

}
