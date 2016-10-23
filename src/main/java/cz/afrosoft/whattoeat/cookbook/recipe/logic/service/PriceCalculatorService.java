/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;

/**
 *
 * @author Alexandra
 */
public interface PriceCalculatorService {

    float calculatePrice(Recipe recipe, int servings);
    
}
