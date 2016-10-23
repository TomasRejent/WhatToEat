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
public enum Taste implements Labeled {
    SWEET("cz.afrosoft.whattoeat.taste.sweet"),
    SALTY("cz.afrosoft.whattoeat.taste.salty"),
    NEUTRAL("cz.afrosoft.whattoeat.taste.neutral");

    private final String labelKey;

    private Taste(String labelKey){
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

}
