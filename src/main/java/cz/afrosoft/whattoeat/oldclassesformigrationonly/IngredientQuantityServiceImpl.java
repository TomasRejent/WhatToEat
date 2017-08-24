/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.OldRecipeIngredient;
import cz.afrosoft.whattoeat.core.data.exception.NotFoundException;
import org.apache.commons.lang3.Validate;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

/**
 *
 * @author Tomas Rejent
 */
public class IngredientQuantityServiceImpl implements IngredientQuantityService{

    private final IngredientInfoDao ingredientInfoDao;

    public IngredientQuantityServiceImpl(IngredientInfoDao ingredientInfoDao) {
        Validate.notNull(ingredientInfoDao);
        this.ingredientInfoDao = ingredientInfoDao;
    }

    @Override
    public float getQuantity(final OldRecipeIngredient ingredient, final int numberOfServings) {
        checkNotNull(ingredient, "RecipeIngredient cannot be null.");
        checkServings(numberOfServings);

        final OldIngredient ingredientInfo = ingredientInfoDao.read(ingredient.getIngredientKey());
        checkNotNull(ingredientInfo, "Cannot find matching ingredient info for ingredient: " + ingredient, NotFoundException.class);

        return getQuantity(ingredient, ingredientInfo, numberOfServings);
    }

    @Override
    public float getQuantity(final OldRecipeIngredient ingredient, final OldIngredient ingredientInfo, final int numberOfServings) {
        checkNotNull(ingredient, "RecipeIngredient cannot be null.");
        checkNotNull(ingredientInfo, "Ingredient cannot be null for ingredient: " + ingredient);
        checkServings(numberOfServings);
        if(ingredient.getIngredientKey() == null || !ingredient.getIngredientKey().equals(ingredientInfo.getKey())){
            throw new IllegalArgumentException("RecipeIngredient name is not matching with ingredient info name.");
        }

        float quantityForServings = ingredient.getQuantity() * numberOfServings;

        if (IngredientUnit.PIECE == ingredientInfo.getIngredientUnit()) {
            return (float) Math.ceil(quantityForServings);
        } else {
            return quantityForServings;
        }
    }

    private void checkServings(int numberOfServings){
        if(numberOfServings < 0){
            throw new IllegalArgumentException("Number of serving cannot be less than zero.");
        }
    }

}
