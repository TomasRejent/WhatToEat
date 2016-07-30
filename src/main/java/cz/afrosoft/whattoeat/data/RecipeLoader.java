/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public interface RecipeLoader {

    Set<Recipe> loadRecipes(File recipeFile) throws FileNotFoundException;

    void saveRecipe(File recipeFile, Recipe recipe) throws FileNotFoundException, IOException;
    
}
