/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.data.BaseDao;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import java.util.List;
import java.util.Set;

/**
 * Interface for {@link Recipe} entity DAO to provide Recipe specific data services.
 * @author Tomas Rejent
 */
public interface RecipeDao extends BaseDao<Recipe, String>{

    /**
     * Get recipes which type matches at least one of specified {@link RecipeType}.
     * @param types (NotNull) Types by which recipes are filtered.
     * @return (NotNull)(ReadOnly) List of matching recipes or empty list if none was found.
     */
    List<Recipe> getRecipeByType(RecipeType... types);
    
    /**
     * Get set of keywords from all recipes.
     * @return (NotNull)(ReadOnly) Set of keywords.
     */
    Set<String> getRecipeKeywords();

}
