/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.model.enums;

/**
 *
 * @author Alexandra
 */
public enum IngredientUnit {
    PIECE("pcs"), WEIGHT("Kg"), VOLUME("l");
    
    private String unitCode;

    private IngredientUnit(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return unitCode;
    }
        
}
