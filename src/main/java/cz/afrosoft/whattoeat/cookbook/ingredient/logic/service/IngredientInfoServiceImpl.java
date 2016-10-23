/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
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

    public IngredientInfoServiceImpl(final IngredientInfoDao ingredientInfoDao) {
        LOGGER.debug("Creating Ingredient Info service.");
        Validate.notNull(ingredientInfoDao);
        this.ingredientInfoDao = ingredientInfoDao;
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



}