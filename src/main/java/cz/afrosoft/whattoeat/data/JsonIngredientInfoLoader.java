/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public class JsonIngredientInfoLoader implements IngredientInfoLoader{

    private static final JsonIngredientInfoLoader INSTANCE = new JsonIngredientInfoLoader();
    
    private final Gson gson = new Gson();

    private JsonIngredientInfoLoader() {   
    }
    
    public static JsonIngredientInfoLoader getInstance(){
        return INSTANCE;
    }
    
    @Override
    public Set<IngredientInfo> loadIngredientsInfo(File ingredientsInfo) throws FileNotFoundException {
        if(ingredientsInfo == null){
            throw new IllegalArgumentException("Recipe file cannot be null.");
        } 
        
        BufferedReader fileReader = new BufferedReader(new FileReader(ingredientsInfo));
        IngredientInfo[] recipes = gson.fromJson(fileReader, IngredientInfo[].class);
        return ImmutableSet.copyOf(recipes);
    }
    
}
