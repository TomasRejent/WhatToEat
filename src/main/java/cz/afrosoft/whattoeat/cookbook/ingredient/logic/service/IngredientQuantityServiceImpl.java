/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import cz.afrosoft.whattoeat.core.data.exception.NotFoundException;
import static cz.afrosoft.whattoeat.core.data.util.ParameterCheckUtils.checkNotNull;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import org.apache.commons.lang3.Validate;

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
    public float getQuantity(final Ingredient ingredient, final int numberOfServings) {
        checkNotNull(ingredient, "Ingredient cannot be null.");
        checkServings(numberOfServings);

        final IngredientInfo ingredientInfo = ingredientInfoDao.read(ingredient.getName());
        checkNotNull(ingredientInfo, "Cannot find matching ingredient info for ingredient: " + ingredient, NotFoundException.class);

        return getQuantity(ingredient, ingredientInfo, numberOfServings);
    }

    @Override
    public float getQuantity(final Ingredient ingredient, final IngredientInfo ingredientInfo, final int numberOfServings) {
        checkNotNull(ingredient, "Ingredient cannot be null.");
        checkNotNull(ingredientInfo, "IngredientInfo cannot be null for ingredient: " + ingredient);
        checkServings(numberOfServings);
        if(ingredient.getName() == null || !ingredient.getName().equals(ingredientInfo.getName())){
            throw new IllegalArgumentException("Ingredient name is not matching with ingredient info name.");
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
