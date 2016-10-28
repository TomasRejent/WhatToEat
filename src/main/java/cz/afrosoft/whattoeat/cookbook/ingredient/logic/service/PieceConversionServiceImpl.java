/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import com.google.common.collect.ImmutableSet;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.BasicConversionInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.PieceConversionInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.conversioninfo.GarlicConversionInfo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Tomas Rejent
 */
public class PieceConversionServiceImpl implements PieceConversionService{

    private Map<String, PieceConversionInfo> pieceConversionMap;

    public PieceConversionServiceImpl(final BasicConversionInfoDao basicConversionInfoDao) {
        Collection<? extends PieceConversionInfo> pieceConversionInfoCollection = basicConversionInfoDao.readAll();
        Collection<PieceConversionInfo> specialConversions = getSpecialConversions();

        this.pieceConversionMap = new HashMap<>(pieceConversionInfoCollection.size() + specialConversions.size());
        for(PieceConversionInfo pieceConversionInfo : pieceConversionInfoCollection){
            this.pieceConversionMap.put(pieceConversionInfo.getIngredientName(), pieceConversionInfo);
        }

        for(PieceConversionInfo pieceConversionInfo : specialConversions){
            this.pieceConversionMap.put(pieceConversionInfo.getIngredientName(), pieceConversionInfo);
        }
    }

    @Override
    public boolean hasPieceConversion(String ingredientName) {
        return pieceConversionMap.containsKey(ingredientName);
    }

    @Override
    public int getPieces(String ingredientName, int grams) {
        final PieceConversionInfo pieceConversionInfo = pieceConversionMap.get(ingredientName);
        return getNumberOfPieces(grams, pieceConversionInfo.getGramsOfAveragePiece());
    }

    @Override
    public String getPieceText(String ingredientName, int grams) {
        final PieceConversionInfo pieceConversionInfo = pieceConversionMap.get(ingredientName);
        final int pieces = getNumberOfPieces(grams, pieceConversionInfo.getGramsOfAveragePiece());
        return pieceConversionInfo.getText(pieces);
    }

    @Override
    public PieceConversionInfo getPieceConversionInfo(final String ingredientName) {
        Validate.notNull(ingredientName);
        return pieceConversionMap.get(ingredientName);
    }



    private int getNumberOfPieces(int grams, int gramsOfAveragePiece){
        float fractalPieces = ((float)grams) / gramsOfAveragePiece;
        return (int) Math.ceil(fractalPieces);
    }

    private Collection<PieceConversionInfo> getSpecialConversions(){
        return ImmutableSet.of(
            GarlicConversionInfo.getInstance()
        );
    }

}
