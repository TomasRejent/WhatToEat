/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.logic.model.conversion;

import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.logic.model.PieceConversionInfo;

/**
 *
 * @author Tomas Rejent
 */
public final class GarlicConversionInfo implements PieceConversionInfo {

    private static final String[] EXCLUDED_FIELDS = new String[]{"gramsOfAveragePiece"};
    private static final String INGREDIENT_NAME = I18n.getText("cz.afrosoft.whattoeat.ingredient.piece.conversion.garlic.name");
    private static final int GRAMS_OF_AVERAGE_GARLIC_BULB = 70;
    private static final int GRAMS_OF_AVERAGE_GARLIC_CLOVE = 10;

    private static final GarlicConversionInfo INSTANCE = new GarlicConversionInfo();

    public static GarlicConversionInfo getInstance(){
        return INSTANCE;
    }

    private  GarlicConversionInfo() {
    }

    @Override
    public String getIngredientName() {
        return INGREDIENT_NAME;
    }

    @Override
    public int getGramsOfAveragePiece() {
        return GRAMS_OF_AVERAGE_GARLIC_CLOVE;
    }

    @Override
    public String getText(int cloves) {
        final StringBuilder sb = new StringBuilder();
        int bulbs = getNumberOfBulbs(cloves);
        String cloveLabel = I18n.getText("cz.afrosoft.whattoeat.ingredient.piece.conversion.garlic.clove");
        String bulbLabel = I18n.getText("cz.afrosoft.whattoeat.ingredient.piece.conversion.garlic.bulb");
        sb.append(cloves).append(cloveLabel).append(":").append(bulbs).append(bulbLabel);
        return sb.toString();
    }

    private int getNumberOfBulbs(int cloves){
        int gramsOfGarlic = cloves*GRAMS_OF_AVERAGE_GARLIC_CLOVE;
        return (int) Math.ceil(((float)gramsOfGarlic) / GRAMS_OF_AVERAGE_GARLIC_BULB);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GarlicConversionInfo singleton[");
        stringBuilder.append("ingredientName=").append(INGREDIENT_NAME);
        stringBuilder.append("averageBulbGrams=").append(GRAMS_OF_AVERAGE_GARLIC_BULB);
        stringBuilder.append("averageCloveGrams=").append(GRAMS_OF_AVERAGE_GARLIC_CLOVE);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        if(obj instanceof PieceConversionInfo){
            PieceConversionInfo conversionInfo = (PieceConversionInfo) obj;
            return INGREDIENT_NAME.equals(conversionInfo.getIngredientName());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return INGREDIENT_NAME.hashCode();
    }

}
