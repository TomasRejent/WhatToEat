/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.data;

import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.BaseDao;

/**
 * Interface for {@link Diet} entity DAO to provide Diet specific data services.
 * @author Tomas Rejent
 */
public interface DietDao extends BaseDao<Diet, String>{

    void updateMeal(Meal meal);

}
