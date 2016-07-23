/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.services;

import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;

import cz.afrosoft.whattoeat.data.DataHolderService;
import cz.afrosoft.whattoeat.logic.model.Ingredient;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public class PriceCalculatorServiceImpl implements PriceCalculatorService {  
    
    private final DataHolderService dataHolderService;
    private final IngredientQuantityService ingredientQuantityService;
    
    public PriceCalculatorServiceImpl (final DataHolderService dataHolderService, final IngredientQuantityService ingredientQuantityService) {
        checkNotNull(dataHolderService, "Data holder service cannot be null.");
        checkNotNull(ingredientQuantityService, "IngredientQuantity service cannot be null.");

        this.dataHolderService = dataHolderService;
        this.ingredientQuantityService = ingredientQuantityService;
    }
    
    @Override
    public float calculatePrice (final Recipe recipe, final int servings){
        checkParameters(recipe, servings);
        
        if (servings == 0){
            return 0;
        }

        float totalPrice = 0;
        for(Ingredient ingredient : recipe.getIngredients()){
            final IngredientInfo ingredientInfo = dataHolderService.getIngredientByName(ingredient.getName());
            final float quantity = ingredientQuantityService.getQuantity(ingredient, ingredientInfo, servings);

            totalPrice += quantity * ingredientInfo.getPrice();
        }

        return totalPrice;
    }   
    
    private void checkParameters (final Recipe recipe, final int servings){
        if (recipe == null){
            throw new IllegalArgumentException("Recipe can not be null");
        }
        if (servings < 0){
            throw new IllegalArgumentException("Servings must be 0 or positive number");
        }
        
        
    }
    
}
