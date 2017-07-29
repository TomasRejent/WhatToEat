/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.oldclassesformigrationonly.BaseDao;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldRecipeType;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.RecipeOld;

import java.util.List;
import java.util.Set;

/**
 * Interface for {@link RecipeOld} entity DAO to provide Recipe specific data services.
 * @author Tomas Rejent
 */
public interface RecipeDao extends BaseDao<RecipeOld, String> {

    /**
     * Gets recipe by its name.
     * @param name (Required) Name of recipe.
     * @return (NotNull)
     */
    RecipeOld getRecipeByName(String name);

    /**
     * Get recipes which type matches at least one of specified {@link OldRecipeType}.
     * @param types (NotNull) Types by which recipes are filtered.
     * @return (NotNull)(ReadOnly) List of matching recipes or empty list if none was found.
     */
    List<RecipeOld> getRecipeByType(OldRecipeType... types);
    
    /**
     * Get set of keywords from all recipes.
     * @return (NotNull)(ReadOnly) Set of keywords.
     */
    Set<String> getRecipeKeywords();

}
