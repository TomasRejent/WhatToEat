/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.logic.model;

import cz.afrosoft.whattoeat.data.util.ParameterCheckUtils;
import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.logic.model.enums.IngredientUnit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class BasicConversionInfo implements PieceConversionInfo{

    private static final String[] EXCLUDED_FIELDS = new String[]{"gramsOfAveragePiece"};

    private String ingredientName;
    private int gramsOfAveragePiece;

    @Override
    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public int getGramsOfAveragePiece() {
        return gramsOfAveragePiece;
    }

    @Override
    public String getText(int pieces) {
        final StringBuilder sb = new StringBuilder();
        final String translatedPieceLabel = I18n.getText(IngredientUnit.PIECE.getLabelKey());
        return sb.append(pieces).append(translatedPieceLabel).toString();
    }

    

    public void setIngredientName(String ingredientName) {
        ParameterCheckUtils.checkNotNull(ingredientName, "Ingredient name cannot be null.");
        this.ingredientName = ingredientName;
    }

    public void setGramsOfAveragePiece(int gramsOfAveragePiece) {
        if(gramsOfAveragePiece <= 0){
            throw new IllegalArgumentException("Grams of average piece must be greater than zero.");
        }

        this.gramsOfAveragePiece = gramsOfAveragePiece;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, EXCLUDED_FIELDS);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

}