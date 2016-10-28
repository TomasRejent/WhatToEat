/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.data.exception.NotFoundException;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import java.util.Set;

/**
 * Service which handles work with {@link Recipe} and related entities on business layer.
 * @author Tomas Rejent
 */
public interface RecipeService {

    /**
     * Gets recipe by name.
     * @param name (NotNull) Name of recipe.
     * @return (NotNull) Recipe with specified name.
     * @throws NotFoundException If recipe with given name does not exist.
     */
    Recipe getRecipeByName(String name);

    /**
     * Gets all recipes.
     * @return (NotNull)(ReadOnly) Set of recipes.
     */
    Set<Recipe> getAllRecipes();

    /**
     * Gets all recipes which contains type {@link RecipeType#SIDE_DISH}.
     * @return (NotNull)(ReadOnly)
     */
    Set<Recipe> getAllSideDishes();

    /**
     * Add new Recipe.
     * @param recipe (NotNull) Recipe to add.
     */
    void addRecipe(Recipe recipe);

    /**
     * @return (NotNull)(ReadOnly) Set of keywords from all recipes.
     */
    Set<String> getAllRecipeKeywords();

}
