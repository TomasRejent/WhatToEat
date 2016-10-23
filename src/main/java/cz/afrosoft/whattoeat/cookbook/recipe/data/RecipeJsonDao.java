/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.data.JsonDao;
import cz.afrosoft.whattoeat.data.util.LocationUtils;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;

/**
 * Implementation of persistence service of JSON type for entity {@link Recipe}.
 * @author Tomas Rejent
 */
public class RecipeJsonDao extends JsonDao<Recipe, String> implements RecipeDao{

    public RecipeJsonDao() {
        super(LocationUtils.getRecipeFile(), Recipe[].class);
    }
}
