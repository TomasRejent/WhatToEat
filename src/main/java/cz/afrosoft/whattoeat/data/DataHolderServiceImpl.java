/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import cz.afrosoft.whattoeat.data.exception.DataLoadException;
import cz.afrosoft.whattoeat.data.util.LocationUtils;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandra
 */
public final class DataHolderServiceImpl implements DataHolderService{

    private final Map<String, Recipe> recipes;
    private final Map<String, IngredientInfo> ingredients;
    
    public DataHolderServiceImpl() {
        recipes = new HashMap<>();
        ingredients = new HashMap<>();
        
        loadRecipes();
        loadIngredients();
    }

    @Override
    public Collection<Recipe> getRecipes() {
        return Collections.unmodifiableCollection(recipes.values());
    }

    @Override
    public Collection<IngredientInfo> getIngredients() {
        return Collections.unmodifiableCollection(ingredients.values());
    }

    @Override
    public IngredientInfo getIngredientByName(String name) {
        return ingredients.get(name);
    }



    private void loadRecipes(){
        try {
            File recipeFile = LocationUtils.getRecipeFile();
            RecipeLoader recipeLoader = JsonRecipeLoader.getInstance();
            Set<Recipe> recipeSet = recipeLoader.loadRecipes(recipeFile);
            for(Recipe recipe : recipeSet){
                recipes.put(recipe.getName(), recipe);
            }
        } catch (DataLoadException | FileNotFoundException ex) {
            Logger.getLogger(DataHolderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadIngredients(){
        try {
            File ingredientFile = LocationUtils.getIngredientFile();
            IngredientInfoLoader ingredientLoader = JsonIngredientInfoLoader.getInstance();
            Set<IngredientInfo> ingredientSet = ingredientLoader.loadIngredientsInfo(ingredientFile);
            for(IngredientInfo ingredient : ingredientSet){
                ingredients.put(ingredient.getName(), ingredient);
            }
        } catch (DataLoadException | FileNotFoundException ex) {
            Logger.getLogger(DataHolderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
