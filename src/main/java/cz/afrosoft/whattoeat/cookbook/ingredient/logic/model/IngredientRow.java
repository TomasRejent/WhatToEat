/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.PieceConversionInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents one row from ingredient table.
 * @author Tomas Rejent
 */
public class IngredientRow implements Comparable<IngredientRow>{

    private static final String NO_CONVERSION = "-";
    private static final String KEYWORD_SEPARATOR = ",";

    private final OldIngredient ingredientInfo;
    private final PieceConversionInfo pieceConversionInfo;

    /**
     *
     * @param ingredientInfo (NotNull)
     * @param pieceConversionInfo (NullAble) Piece conversion info if exist for specified ingredient info.
     */
    public IngredientRow(final OldIngredient ingredientInfo, final PieceConversionInfo pieceConversionInfo) {
        Validate.notNull(ingredientInfo);
        this.ingredientInfo = ingredientInfo;
        this.pieceConversionInfo = pieceConversionInfo;
    }

    public String getName(){
        return ingredientInfo.getName();
    }

    public String getUnit(){
        return I18n.getText(ingredientInfo.getIngredientUnit().getLabelKey());
    }

    public String getPrice(){
        return String.valueOf(ingredientInfo.getPrice());
    }

    public String getKeywords(){
        return StringUtils.join(ingredientInfo.getKeywords(), KEYWORD_SEPARATOR);
    }

    /**
     * @return Gets text for conversion from grams to pieces.
     */
    public String getConversion(){
        if(pieceConversionInfo == null){
            return NO_CONVERSION;
        }else{
            return String.valueOf(pieceConversionInfo.getGramsOfAveragePiece());
        }
    }

    public OldIngredient getIngredientInfo() {
        return ingredientInfo;
    }

    public PieceConversionInfo getPieceConversionInfo() {
        return pieceConversionInfo;
    }

    @Override
    public int compareTo(final IngredientRow thatIngredientRow) {
        if (thatIngredientRow == null){
            return 1;
        }
        final OldIngredient thisIngredient = ingredientInfo;
        final OldIngredient thatIngredient = thatIngredientRow.getIngredientInfo();
        if(thisIngredient == thatIngredient){
            return 0;
        }else if(thisIngredient == null){
            return -1;
        }else if (thatIngredient == null){
            return 1;
        }else{
            return I18n.compareStringsIgnoreCase(thisIngredient.getName(), thatIngredient.getName());
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ingredientInfo.getKey()).build();
    }

    @Override
    public boolean equals(final Object obj) {
        if(obj == null || !(obj instanceof IngredientRow)){
            return false;
        }else{
            final IngredientRow otherRow = (IngredientRow) obj;
            return this.ingredientInfo.getKey().equals(otherRow.getIngredientInfo().getKey());
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
