/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.services;

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
    
    private DataHolderService dataHolderService;
    
    public PriceCalculatorServiceImpl (DataHolderService dataHolderService) {
        this.dataHolderService = dataHolderService;
    }
    
    @Override
    public float calculatePrice (Recipe recipe,int servings){
        checkParameters(recipe, servings);
        
        if (servings == 0){
            return 0;
        }
        Set<Ingredient> ingredients = recipe.getIngredients();
        float price=0;
        for(Ingredient ingredient :ingredients){
            IngredientInfo ingredientInfo = dataHolderService.getIngredientByName(ingredient.getName());
            price += ingredientInfo.getPrice();
        }
        return price*servings;
    }   
    
    private void checkParameters (Recipe recipe,int servings){
        if (recipe == null){
            throw new IllegalArgumentException("Recipe can not be null");
        }
        if (servings < 0){
            throw new IllegalArgumentException("Servings must be 0 or positive number");
        }
        
        
    }
    
}
