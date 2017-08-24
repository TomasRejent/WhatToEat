/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import com.google.common.collect.ImmutableSet;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.conversioninfo.GarlicConversionInfo;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tomas Rejent
 */
public class PieceConversionServiceImpl implements PieceConversionService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PieceConversionServiceImpl.class);

    private Map<String, PieceConversionInfo> pieceConversionMap;
    private final BasicConversionInfoDao basicConversionInfoDao;

    public PieceConversionServiceImpl(final BasicConversionInfoDao basicConversionInfoDao) {
        Validate.notNull(basicConversionInfoDao);
        this.basicConversionInfoDao = basicConversionInfoDao;
        Collection<? extends PieceConversionInfo> pieceConversionInfoCollection = basicConversionInfoDao.readAll();
        Collection<PieceConversionInfo> specialConversions = getSpecialConversions();

        this.pieceConversionMap = new HashMap<>(pieceConversionInfoCollection.size() + specialConversions.size());
        for(PieceConversionInfo pieceConversionInfo : pieceConversionInfoCollection){
            this.pieceConversionMap.put(pieceConversionInfo.getIngredientKey(), pieceConversionInfo);
        }

        for(PieceConversionInfo pieceConversionInfo : specialConversions){
            this.pieceConversionMap.put(pieceConversionInfo.getIngredientKey(), pieceConversionInfo);
        }
    }

    @Override
    public List<BasicConversionInfo> getAll() {
        return basicConversionInfoDao.readAll();
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

    @Override
    public void saveOrUpdate(final PieceConversionInfo pieceConversionInfo) {
        LOGGER.debug("Saving piece conversion info: {}.", pieceConversionInfo);
        Validate.notNull(pieceConversionInfo, "Piece conversion info cannot be null.");
        if(pieceConversionInfo instanceof BasicConversionInfo){
            final BasicConversionInfo basicInfo = (BasicConversionInfo) pieceConversionInfo;
            if(basicConversionInfoDao.exists(basicInfo.getIngredientKey())){
                basicConversionInfoDao.update(basicInfo);
            }else{
                basicConversionInfoDao.create(basicInfo);
            }
        }else{
            LOGGER.warn("Type of piece conversion info is not recognized: {}.", pieceConversionInfo.getClass());
        }
    }

    @Override
    public void delete(final PieceConversionInfo pieceConversionInfo) {
        LOGGER.debug("Deleting piece conversion info: {}.", pieceConversionInfo);
        Validate.notNull(pieceConversionInfo);
        if(pieceConversionInfo instanceof BasicConversionInfo){
            basicConversionInfoDao.delete((BasicConversionInfo)pieceConversionInfo);
        }else{
            LOGGER.warn("Type of piece conversion info is not recognized: {}.", pieceConversionInfo.getClass());
        }
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
