/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.IngredientCouple;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.core.data.exception.NotFoundException;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRow;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.PieceConversionInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Service which handles work with {@link Ingredient} and related entities on business layer.
 * @author Tomas Rejent
 */
public interface IngredientService {

    /**
     * Gets all ingredients.
     * @return (NotNull)
     */
    List<Ingredient> getAllIngredients();

    Ingredient getIngredientByKey(String key);

    /**
     * Gets {@link Ingredient} by its name.
     * @param name (NotNull) Name of Ingredient.
     * @return (NotNull) Ingredient with specified name.
     * @throws NotFoundException If Ingredient with given name does not exist.
     */
    Ingredient getIngredientInfoByName(String name);

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
     * Save or update {@link Ingredient} and {@link PieceConversionInfo} specified in {@link IngredientRow}.
     * @param ingredientRow (NotNull) RecipeIngredient row to save.
     */
    void saveOrUpdate(IngredientRow ingredientRow);

    /**
     * Deletes {@link Ingredient} and {@link PieceConversionInfo} specified in {@link IngredientRow}.
     * @param ingredientRow (NotNull) RecipeIngredient row to delete.
     */
    void delete(IngredientRow ingredientRow);

    /**
     * Converts collection of {@link RecipeIngredient} to {@link IngredientCouple}.
     * @param ingredients (Required) Recipe ingredients to which Ingredient details will be added.
     * @return (NotNull) List of ingredient couples.
     * @throws IllegalArgumentException When argument is null.
     */
    List<IngredientCouple> convertToCouple(Collection<RecipeIngredient> ingredients);

}
