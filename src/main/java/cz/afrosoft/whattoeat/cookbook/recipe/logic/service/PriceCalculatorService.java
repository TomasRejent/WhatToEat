/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.oldclassesformigrationonly.RecipeOld;

/**
 *
 * @author Alexandra
 */
public interface PriceCalculatorService {

    float calculatePrice(RecipeOld recipe, int servings);
    
}
