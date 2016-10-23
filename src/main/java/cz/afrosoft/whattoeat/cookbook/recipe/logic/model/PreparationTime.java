/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.gui.Labeled;

/**
 *
 * @author Alexandra
 */
public enum PreparationTime implements Labeled {
    /**
     * Up to 3 minutes. Like pick something from shelve.
     */
    INSTANT("cz.afrosoft.whattoeat.preparationTime.instant"),
    /**
     * Up to 10 minutes.
     */
    QUICK("cz.afrosoft.whattoeat.preparationTime.quick"),
    /**
     * Up to 30 minutes.
     */
    SHORT("cz.afrosoft.whattoeat.preparationTime.short"),
    /**
     * Up to 1 hour.
     */
    MEDIUM("cz.afrosoft.whattoeat.preparationTime.medium"),
    /**
     * More than hour.
     */
    LONG("cz.afrosoft.whattoeat.preparationTime.long");

    private final String labelKey;

    private PreparationTime(String labelKey){
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

}
