/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.PieceConversionInfo;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public interface DataHolderService {
    
    Collection<Recipe> getRecipes();

    Set<String> getAllRecipeKeywords();

    void addRecipe(Recipe recipe);
    
    Recipe getRecipeByName(String name);
    
    Collection<IngredientInfo> getIngredients();

    Set<String> getIngredientNames();

    IngredientInfo getIngredientByName(String name);

    Collection<Diet> getDiets();

    void addDiet(Diet diet);

    Collection<? extends PieceConversionInfo> getPieceConversionInfo();
}
