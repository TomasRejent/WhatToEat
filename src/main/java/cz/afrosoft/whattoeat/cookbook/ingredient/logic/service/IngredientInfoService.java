/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.data.exception.NotFoundException;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRow;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.PieceConversionInfo;
import java.util.List;
import java.util.Set;

/**
 * Service which handles work with {@link IngredientInfo} and related entities on business layer.
 * @author Tomas Rejent
 */
public interface IngredientInfoService {

    /**
     * Gets {@link IngredientInfo} by its name.
     * @param name (NotNull) Name of IngredientInfo.
     * @return (NotNull) IngredientInfo with specified name.
     * @throws NotFoundException If IngredientInfo with given name does not exist.
     */
    IngredientInfo getIngredientInfoByName(String name);

    /**
     * @return (NotNull)(ReadOnly) Set of names from all defined IngredientInfos.
     */
    Set<String> getIngredientNames();

    /**
     *
     * @return (NotNull)(ReadOnly) Gets list of ingredients with all data needed for view in ingredient table.
     */
    List<IngredientRow> getIngredientRows();

    /**
     * @return (NotNull)(ReadOnly) Gets set of all existing ingredient keywords.
     */
    Set<String> getAllIngredientKeywords();

    /**
     * Save or update {@link IngredientInfo} and {@link PieceConversionInfo} specified in {@link IngredientRow}.
     * @param ingredientRow (NotNull) Ingredient row to save.
     */
    void saveOrUpdate(IngredientRow ingredientRow);

    /**
     * Deletes {@link IngredientInfo} and {@link PieceConversionInfo} specified in {@link IngredientRow}.
     * @param ingredientRow (NotNull) Ingredient row to delete.
     */
    void delete(IngredientRow ingredientRow);

}
