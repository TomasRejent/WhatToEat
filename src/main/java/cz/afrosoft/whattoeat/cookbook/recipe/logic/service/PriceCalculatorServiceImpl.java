/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.OldIngredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.OldRecipeIngredient;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.RecipeOld;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

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
    public float calculatePrice(final RecipeOld recipe, final int servings) {
        checkParameters(recipe, servings);
        
        if (servings == 0){
            return 0;
        }

        float totalPrice = 0;
        for (OldRecipeIngredient ingredient : recipe.getIngredients()) {
            final OldIngredient ingredientInfo = ingredientInfoDao.read(ingredient.getIngredientKey());
            final float quantity = ingredientQuantityService.getQuantity(ingredient, ingredientInfo, servings);

            totalPrice += quantity * ingredientInfo.getPrice();
        }

        return totalPrice;
    }

    private void checkParameters(final RecipeOld recipe, final int servings) {
        if (recipe == null){
            throw new IllegalArgumentException("Recipe can not be null");
        }
        if (servings < 0){
            throw new IllegalArgumentException("Servings must be 0 or positive number");
        }
        
    }
    
}
