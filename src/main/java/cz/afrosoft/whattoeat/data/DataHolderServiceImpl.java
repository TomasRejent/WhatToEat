/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.afrosoft.whattoeat.data.exception.DataLoadException;
import cz.afrosoft.whattoeat.data.util.LocationUtils;
import cz.afrosoft.whattoeat.logic.model.BasicConversionInfo;
import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.PieceConversionInfo;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexandra
 */
public final class DataHolderServiceImpl implements DataHolderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DataHolderServiceImpl.class);

    private final Map<String, Recipe> recipes;
    private final Map<String, IngredientInfo> ingredients;
    private final Set<String> recipeKeywords = new HashSet<>();
    
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
    public void addRecipe(Recipe recipe) {
        try {
            File recipeFile = LocationUtils.getRecipeFile();

            RecipeLoader recipeLoader = JsonRecipeLoader.getInstance();
            recipeLoader.saveRecipe(recipeFile, recipe);
            recipes.put(recipe.getName(), recipe);
            if(recipe.getKeywords() != null){
                recipeKeywords.addAll(recipe.getKeywords());
            }
        } catch (DataLoadException | IOException ex) {
            LOGGER.error("Cannot add diet.", ex);
        }
    }

    @Override
    public Recipe getRecipeByName(String name) {
        return recipes.get(name);
    }

    @Override
    public Collection<IngredientInfo> getIngredients() {
        return Collections.unmodifiableCollection(ingredients.values());
    }

    @Override
    public Set<String> getIngredientNames() {
        return Collections.unmodifiableSet(ingredients.keySet());
    }

    @Override
    public IngredientInfo getIngredientByName(String name) {
        return ingredients.get(name);
    }

    @Override
    public Set<String> getAllRecipeKeywords() {
        return Collections.unmodifiableSet(recipeKeywords);
    }

    private void loadRecipes(){
        try {
            File recipeFile = LocationUtils.getRecipeFile();
            RecipeLoader recipeLoader = JsonRecipeLoader.getInstance();
            Set<Recipe> recipeSet = recipeLoader.loadRecipes(recipeFile);
            for(Recipe recipe : recipeSet){
                recipes.put(recipe.getName(), recipe);
                Set<String> keywords = recipe.getKeywords();
                if(keywords != null){
                    recipeKeywords.addAll(keywords);
                }
            }
        } catch (DataLoadException | FileNotFoundException ex) {
            LOGGER.error("Cannot load recipes.", ex);
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
            LOGGER.error("Cannot load ingredients.", ex);
        }
    }

    @Override
    public Collection<Diet> getDiets() {
        try {
            File dietFile = LocationUtils.getDietFile();
            DietLoader dietLoader = JsonDietLoader.getInstance();
            return dietLoader.getAllDiets(dietFile);
        } catch (DataLoadException | IOException ex) {
            LOGGER.error("Cannot load diets.", ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void addDiet(Diet diet) {
        try {
            File dietFile = LocationUtils.getDietFile();

            DietLoader dietLoader = JsonDietLoader.getInstance();
            dietLoader.saveDiet(dietFile, diet);
        } catch (DataLoadException | IOException ex) {
            LOGGER.error("Cannot add diet.", ex);
        }
    }

    @Override
    public Collection<? extends PieceConversionInfo> getPieceConversionInfo() {
        try {
            Gson gson = new Gson();
            File pieceConversionFile = LocationUtils.getPieceConversionFile();
            BufferedReader fileReader = new BufferedReader(new FileReader(pieceConversionFile));
            PieceConversionInfo[] recipes = gson.fromJson(fileReader, BasicConversionInfo[].class);
            return ImmutableSet.copyOf(recipes);
        } catch (DataLoadException | IOException ex) {
            LOGGER.error("Cannot add diet.", ex);
            return Collections.emptyList();
        }
    }


}
