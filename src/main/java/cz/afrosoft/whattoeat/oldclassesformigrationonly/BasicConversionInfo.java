/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class BasicConversionInfo implements PieceConversionInfo, PersistentEntity<String>{

    private static final String[] EXCLUDED_FIELDS = new String[]{"gramsOfAveragePiece"};

    private String ingredientKey;
    private int gramsOfAveragePiece;

    public BasicConversionInfo() {
    }

    public BasicConversionInfo(final String ingredientKey, final int gramsOfAveragePiece) {
        Validate.notNull(ingredientKey);
        validateGramsOfAveragePiece(gramsOfAveragePiece);

        this.ingredientKey = ingredientKey;
        this.gramsOfAveragePiece = gramsOfAveragePiece;
    }

    @Override
    public String getKey() {
        return ingredientKey;
    }

    @Override
    public String getIngredientKey() {
        return ingredientKey;
    }

    public void setIngredientKey(String ingredientKey) {
        ParameterCheckUtils.checkNotNull(ingredientKey, "RecipeIngredient name cannot be null.");
        this.ingredientKey = ingredientKey;
    }

    @Override
    public int getGramsOfAveragePiece() {
        return gramsOfAveragePiece;
    }

    public void setGramsOfAveragePiece(int gramsOfAveragePiece) {
        validateGramsOfAveragePiece(gramsOfAveragePiece);
        this.gramsOfAveragePiece = gramsOfAveragePiece;
    }

    @Override
    public String getText(int pieces) {
        final StringBuilder sb = new StringBuilder();
        final String translatedPieceLabel = I18n.getText(IngredientUnit.PIECE.getLabelKey());
        return sb.append(pieces).append(translatedPieceLabel).toString();
    }

    private void validateGramsOfAveragePiece(final int gramsOfAveragePiece){
        if(gramsOfAveragePiece <= 0){
            throw new IllegalArgumentException("Grams of average piece must be greater than zero.");
        }
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
