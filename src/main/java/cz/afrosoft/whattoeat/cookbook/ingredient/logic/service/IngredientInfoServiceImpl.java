/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRow;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.PieceConversionInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link IngredientInfoService}.
 * @author Tomas Rejent
 */
public class IngredientInfoServiceImpl implements IngredientInfoService{

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientInfoServiceImpl.class);

    private final IngredientInfoDao ingredientInfoDao;
    private final PieceConversionService pieceConversionService;

    public IngredientInfoServiceImpl(final IngredientInfoDao ingredientInfoDao, final PieceConversionService pieceConversionService) {
        LOGGER.debug("Creating Ingredient Info service.");
        Validate.notNull(ingredientInfoDao);
        Validate.notNull(pieceConversionService);
        this.ingredientInfoDao = ingredientInfoDao;
        this.pieceConversionService = pieceConversionService;
    }

    @Override
    public IngredientInfo getIngredientInfoByName(final String name) {
        LOGGER.debug("Getting IngredientInfo with name: {}.", name);
        Validate.notNull(name, "IngredientInfo name cannot be null.");
        return ingredientInfoDao.read(name);
    }

    @Override
    public Set<String> getIngredientNames() {
        LOGGER.debug("Getting names of all defined ingredients.");
        final List<IngredientInfo> ingredients = ingredientInfoDao.readAll();
        final Set<String> ingredientNames = new HashSet<>(ingredients.size());
        for(IngredientInfo ingredient : ingredients){
            ingredientNames.add(ingredient.getName());
        }
        return Collections.unmodifiableSet(ingredientNames);
    }

    @Override
    public List<IngredientRow> getIngredientRows() {
        LOGGER.debug("Getting ingredient rows.");
        final List<IngredientInfo> ingredients = ingredientInfoDao.readAll();
        final List<IngredientRow> rows = new ArrayList<>(ingredients.size());
        ingredients.stream().forEach(ingredient -> {
            rows.add(new IngredientRow(ingredient, pieceConversionService.getPieceConversionInfo(ingredient.getName())));
        });
        return Collections.unmodifiableList(rows);
    }

    @Override
    public Set<String> getAllIngredientKeywords() {
        return ingredientInfoDao.getIngredientKeywords();
    }

    @Override
    public void saveOrUpdate(final IngredientRow ingredientRow) {
        LOGGER.debug("Saving ingredient row: {}.", ingredientRow);
        Validate.notNull(ingredientRow);
        final IngredientInfo ingredientInfo = ingredientRow.getIngredientInfo();
        if(ingredientInfoDao.exists(ingredientInfo.getKey())){
            ingredientInfoDao.update(ingredientInfo);
        }else{
            ingredientInfoDao.create(ingredientInfo);
        }

        final PieceConversionInfo pieceConversionInfo = ingredientRow.getPieceConversionInfo();
        if(pieceConversionInfo != null){
            pieceConversionService.saveOrUpdate(pieceConversionInfo);
        }
    }

    @Override
    public void delete(final IngredientRow ingredientRow) {
        LOGGER.debug("Deleting ingretient row: {}.", ingredientRow);
        Validate.notNull(ingredientRow);
        ingredientInfoDao.delete(ingredientRow.getIngredientInfo());
        final PieceConversionInfo pieceConversionInfo = ingredientRow.getPieceConversionInfo();
        if(pieceConversionInfo != null){
            pieceConversionService.delete(pieceConversionInfo);
        }
    }
}
