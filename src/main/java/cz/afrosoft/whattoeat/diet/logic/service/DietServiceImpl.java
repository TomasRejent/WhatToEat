/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.logic.service;

import cz.afrosoft.whattoeat.diet.data.DietDao;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link DietService}.
 * @author Tomas Rejent
 */
public class DietServiceImpl implements DietService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DietServiceImpl.class);

    private final DietDao dietDao;

    public DietServiceImpl(final DietDao dietDao) {
        LOGGER.debug("Creating Diet service.");
        Validate.notNull(dietDao);
        this.dietDao = dietDao;
    }

    @Override
    public List<Diet> getAllDiets() {
        LOGGER.debug("Getting all diets.");
        return dietDao.readAll();
    }
}
