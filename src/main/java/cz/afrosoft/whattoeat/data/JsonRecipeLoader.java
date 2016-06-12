/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import cz.afrosoft.whattoeat.data.util.ParameterCheckUtils;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public class JsonRecipeLoader implements RecipeLoader{

    private static final JsonRecipeLoader INSTANCE = new JsonRecipeLoader();
    
    private final Gson gson = new Gson();

    private JsonRecipeLoader(){}
    
    public static JsonRecipeLoader getInstance(){
        return INSTANCE;
    }
    
    @Override
    public Set<Recipe> loadRecipes(File recipeFile) throws FileNotFoundException{
        ParameterCheckUtils.checkNotNull(recipeFile, "Recipe file cannot be null.");

        BufferedReader fileReader = new BufferedReader(new FileReader(recipeFile));
        Recipe[] recipes = gson.fromJson(fileReader, Recipe[].class);
        return ImmutableSet.copyOf(recipes);
    }   
    
}
