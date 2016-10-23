/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.data.exception.NotFoundException;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
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

}
