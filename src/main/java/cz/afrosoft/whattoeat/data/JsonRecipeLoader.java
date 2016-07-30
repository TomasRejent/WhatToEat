/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import cz.afrosoft.whattoeat.data.util.ParameterCheckUtils;
import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

    @Override
    public void saveRecipe(File recipeFile, Recipe recipe) throws FileNotFoundException, IOException {
        ParameterCheckUtils.checkNotNull(recipeFile, "File to which recipe should be saved cannot be null.");
        ParameterCheckUtils.checkNotNull(recipe, "There is no reason to save null recipe.");

        List<Recipe> recipeList = loadWritableRecipeList(recipeFile);
        recipeList.add(recipe);
        String serializedRecipes = gson.toJson(recipeList);

        FileWriter writter = new FileWriter(recipeFile);
        writter.write(serializedRecipes);
        writter.close();
    }

    private List<Recipe> loadWritableRecipeList(File recipeFile) throws FileNotFoundException, IOException{
        BufferedReader fileReader = new BufferedReader(new FileReader(recipeFile));
        Recipe[] recipes = gson.fromJson(fileReader, Recipe[].class);
        fileReader.close();
        if(recipes == null){
            return new LinkedList<>();
        }else{
            return new LinkedList<>(Arrays.asList(recipes));
        }
    }


    
}
