/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public interface IngredientInfoLoader {
    
    Set<IngredientInfo> loadIngredientsInfo(File ingredientsInfo) throws FileNotFoundException;
    
}
