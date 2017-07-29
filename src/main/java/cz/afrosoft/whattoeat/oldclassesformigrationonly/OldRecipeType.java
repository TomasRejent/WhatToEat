/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.core.gui.Labeled;

/**
 * @author Alexandra
 */
public enum OldRecipeType implements Labeled {
    BREAKFAST("cz.afrosoft.whattoeat.recipeType.breakfast"),
    SNACK("cz.afrosoft.whattoeat.recipeType.snack"),
    LUNCH("cz.afrosoft.whattoeat.recipeType.lunch"),
    SOUP("cz.afrosoft.whattoeat.recipeType.soup"),
    DINNER("cz.afrosoft.whattoeat.recipeType.dinner"),
    SIDE_DISH("cz.afrosoft.whattoeat.recipeType.sideDish"),
    DESSERT("cz.afrosoft.whattoeat.recipeType.dessert");

    private final String labelKey;

    private OldRecipeType(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

}