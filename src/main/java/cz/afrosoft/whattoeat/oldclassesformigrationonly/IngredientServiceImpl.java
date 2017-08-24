/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.IngredientCouple;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.OldRecipeIngredient;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

/**
 * Implementation of {@link IngredientService}.
 * @author Tomas Rejent
 */
public class IngredientServiceImpl implements IngredientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientServiceImpl.class);

    private final IngredientInfoDao ingredientInfoDao;
    private final PieceConversionService pieceConversionService;

    public IngredientServiceImpl(final IngredientInfoDao ingredientInfoDao, final PieceConversionService pieceConversionService) {
        LOGGER.debug("Creating RecipeIngredient Info service.");
        Validate.notNull(ingredientInfoDao);
        Validate.notNull(pieceConversionService);
        this.ingredientInfoDao = ingredientInfoDao;
        this.pieceConversionService = pieceConversionService;
    }

    @Override
    public List<OldIngredient> getAllIngredients() {
        return ingredientInfoDao.readAll();
    }

    @Override
    public OldIngredient getIngredientByKey(final String key) {
        return ingredientInfoDao.read(key);
    }

    @Override
    public OldIngredient getIngredientInfoByName(final String name) {
        LOGGER.debug("Getting Ingredient with name: {}.", name);
        Validate.notNull(name, "Ingredient name cannot be null.");
        List<OldIngredient> ingredients = ingredientInfoDao.readAll();
        for (OldIngredient ingredient : ingredients) {
            if(Objects.equals(ingredient.getName(), name)){
                return ingredient;
            }
        }
        throw new IllegalStateException("Ingredient with name " + name + " was not found.");
    }

    @Override
    public Set<String> getIngredientNames() {
        LOGGER.debug("Getting names of all defined ingredients.");
        final List<OldIngredient> ingredients = ingredientInfoDao.readAll();
        final Set<String> ingredientNames = new HashSet<>(ingredients.size());
        for (OldIngredient ingredient : ingredients) {
            ingredientNames.add(ingredient.getName());
        }
        return Collections.unmodifiableSet(ingredientNames);
    }

    @Override
    public List<IngredientRow> getIngredientRows() {
        LOGGER.debug("Getting ingredient rows.");
        final List<OldIngredient> ingredients = ingredientInfoDao.readAll();
        return Collections.unmodifiableList(
                ingredients.stream()
                        .map(ingredient -> new IngredientRow(ingredient, pieceConversionService.getPieceConversionInfo(ingredient.getName())))
                        .sorted()
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Set<String> getAllIngredientKeywords() {
        return ingredientInfoDao.getIngredientKeywords();
    }

    @Override
    public void saveOrUpdate(final IngredientRow ingredientRow) {
        LOGGER.debug("Saving ingredient row: {}.", ingredientRow);
        Validate.notNull(ingredientRow);
        final OldIngredient ingredientInfo = ingredientRow.getIngredientInfo();
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

    @Override
    public List<IngredientCouple> convertToCouple(Collection<OldRecipeIngredient> ingredients) {
        checkNotNull(ingredients, "Cannot convert null list to IngredientCouples.");

        return ingredients.stream().map(
                (recipeIngredient) -> new IngredientCouple(recipeIngredient, getIngredientByKey(recipeIngredient.getKey()))
        ).sorted().collect(Collectors.toList());
    }
}
