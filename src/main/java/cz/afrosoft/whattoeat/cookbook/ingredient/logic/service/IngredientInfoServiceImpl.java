/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRow;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.PieceConversionInfo;

import java.util.*;

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
        LOGGER.debug("Creating RecipeIngredient Info service.");
        Validate.notNull(ingredientInfoDao);
        Validate.notNull(pieceConversionService);
        this.ingredientInfoDao = ingredientInfoDao;
        this.pieceConversionService = pieceConversionService;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientInfoDao.readAll();
    }

    @Override
    public Ingredient getIngredientByKey(final String key) {
        return ingredientInfoDao.read(key);
    }

    @Override
    public Ingredient getIngredientInfoByName(final String name) {
        LOGGER.debug("Getting Ingredient with name: {}.", name);
        Validate.notNull(name, "Ingredient name cannot be null.");
        List<Ingredient> ingredients = ingredientInfoDao.readAll();
        for(Ingredient ingredient : ingredients){
            if(Objects.equals(ingredient.getName(), name)){
                return ingredient;
            }
        }
        throw new IllegalStateException("Ingredient with name " + name + " was not found.");
    }

    @Override
    public Set<String> getIngredientNames() {
        LOGGER.debug("Getting names of all defined ingredients.");
        final List<Ingredient> ingredients = ingredientInfoDao.readAll();
        final Set<String> ingredientNames = new HashSet<>(ingredients.size());
        for(Ingredient ingredient : ingredients){
            ingredientNames.add(ingredient.getName());
        }
        return Collections.unmodifiableSet(ingredientNames);
    }

    @Override
    public List<IngredientRow> getIngredientRows() {
        LOGGER.debug("Getting ingredient rows.");
        final List<Ingredient> ingredients = ingredientInfoDao.readAll();
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
        final Ingredient ingredientInfo = ingredientRow.getIngredientInfo();
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
