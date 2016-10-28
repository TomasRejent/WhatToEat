/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.gui.I18n;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Represents one row from ingredient table.
 * @author Tomas Rejent
 */
public class IngredientRow {

    private static final String NO_CONVERSION = "-";
    private static final String KEYWORD_SEPARATOR = ",";

    private final IngredientInfo ingredientInfo;
    private final PieceConversionInfo pieceConversionInfo;

    /**
     *
     * @param ingredientInfo (NotNull)
     * @param pieceConversionInfo (NullAble) Piece conversion info if exist for specified ingredient info.
     */
    public IngredientRow(final IngredientInfo ingredientInfo, final PieceConversionInfo pieceConversionInfo) {
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

}
