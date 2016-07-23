/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.services;

import cz.afrosoft.whattoeat.logic.exception.IngredientInfoNotFound;
import cz.afrosoft.whattoeat.logic.model.Ingredient;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.enums.IngredientUnit;

/**
 *
 * @author Tomas Rejent
 */
public interface IngredientQuantityService {

    /**
     * Gets quantity of ingredient and applies adjusting logic if necessary.
     * Current logic:
     * <ul>
     *  <li>If ingredient is of type {@link IngredientUnit#PIECE} returned value is always integer. This is useful when same quantity of ingredient is sufficient for more servings. Example one egg is sufficient
     * for both 1 and 2 servings, so recipe contains quantity 0.5 per serving and this method ceil it to 1 in both cases.</li>
     * </ul>
     *
     * Use {@link #getQuantity(cz.afrosoft.whattoeat.logic.model.Ingredient, cz.afrosoft.whattoeat.logic.model.IngredientInfo) } if you have reference to matching {@link IngredientInfo}.
     *
     * @param ingredient (Required) Ingredient from which quantity is obtained.
     * @param numberOfServings (Required) Number of servings for which ingredient quantity is computed. Must be greater or equal to zero.
     * @return (Not null) Quantity of ingredient.
     * @throws IngredientInfoNotFound This method attempts to find matching {@link IngredientInfo} for supplied {@link Ingredient}. If this fails this exception is thrown.
     */
    float getQuantity(Ingredient ingredient, int numberOfServings);

    /**
     * Gets quantity of ingredient and applies adjusting logic if necessary.
     * Current logic:
     * <ul>
     *  <li>If ingredient is of type {@link IngredientUnit#PIECE} returned value is always integer. This is useful when same quantity of ingredient is sufficient for more servings. Example one egg is sufficient
     * for both 1 and 2 servings, so recipe contains quantity 0.5 per serving and this method ceil it to 1 in both cases.</li>
     * </ul>
     *
     * Use {@link #getQuantity(cz.afrosoft.whattoeat.logic.model.Ingredient) } if you do not have reference to {@link IngredientInfo}.
     *
     * @param ingredient (Required) Ingredient from which quantity is obtained.
     * @param ingredientInfo (Required) Ingredient info matching to ingredient by name. It is used to determine ingredient type.
     * @param numberOfServings (Required) Number of servings for which ingredient quantity is computed. Must be greater or equal to zero.
     * @return (Not null) Quantity of ingredient.
     */
    float getQuantity(Ingredient ingredient, IngredientInfo ingredientInfo, int numberOfServings);

}
