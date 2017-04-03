/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.gui.Labeled;

/**
 *
 * @author Alexandra
 */
public enum IngredientUnit implements Labeled{
    /**
     * Quantity expressed by number of units. For example three pieces of lemon.
     */
    PIECE("cz.afrosoft.whattoeat.ingredientUnit.piece"),
    /**
     * Quantity expressed by weight. Unit is gram. For example 500g of flour.
     */
    WEIGHT("cz.afrosoft.whattoeat.ingredientUnit.weight"),
    /**
     * Quantity expressed by volume. Unit is milliliter. For example 350ml of milk.
     */
    VOLUME("cz.afrosoft.whattoeat.ingredientUnit.volume");

    private String labelKey;

    private IngredientUnit(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
        
}
