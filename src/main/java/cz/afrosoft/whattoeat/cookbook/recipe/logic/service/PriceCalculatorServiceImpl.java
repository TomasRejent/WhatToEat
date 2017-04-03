/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityService;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import static cz.afrosoft.whattoeat.core.data.util.ParameterCheckUtils.checkNotNull;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;

/**
 *
 * @author Alexandra
 */
public class PriceCalculatorServiceImpl implements PriceCalculatorService {  
    
    private final IngredientInfoDao ingredientInfoDao;
    private final IngredientQuantityService ingredientQuantityService;
    
    public PriceCalculatorServiceImpl (final IngredientInfoDao ingredientInfoDao, final IngredientQuantityService ingredientQuantityService) {
        checkNotNull(ingredientInfoDao, "Data holder service cannot be null.");
        checkNotNull(ingredientQuantityService, "IngredientQuantity service cannot be null.");

        this.ingredientInfoDao = ingredientInfoDao;
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
            final IngredientInfo ingredientInfo = ingredientInfoDao.read(ingredient.getName());
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
