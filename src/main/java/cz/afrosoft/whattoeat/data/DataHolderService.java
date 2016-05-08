/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import com.google.common.collect.ImmutableMap;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public interface DataHolderService {
    
    Collection<Recipe> getRecipes();
    
    Collection<IngredientInfo> getIngredients();

    IngredientInfo getIngredientByName(String name);
    
}
