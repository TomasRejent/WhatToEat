/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.logic.service;

import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import java.util.List;

/**
 * Service which handles work with {@link Diet} and related entities on business layer.
 * @author TomasRejent
 */
public interface DietService {

    /**
     * @return (NotNull)(ReadOnly) List of all diets.
     */
    List<Diet> getAllDiets();

}
