/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.model.enums;

import cz.afrosoft.whattoeat.gui.Labeled;

/**
 *
 * @author Alexandra
 */
public enum PreparationTime implements Labeled {
    SHORT("cz.afrosoft.whattoeat.preparationTime.short"),
    MEDIUM("cz.afrosoft.whattoeat.preparationTime.medium"),
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
