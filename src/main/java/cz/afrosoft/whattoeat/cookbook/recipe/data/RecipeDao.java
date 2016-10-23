/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.data.BaseDao;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;

/**
 * Interface for {@link Recipe} entity DAO to provide Recipe specific data services.
 * @author Tomas Rejent
 */
public interface RecipeDao extends BaseDao<Recipe, String>{

}
