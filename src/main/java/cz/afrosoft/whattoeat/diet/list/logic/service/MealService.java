package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;

/**
 * @author Tomas Rejent
 */
public interface MealService {

    Meal entityToMeal(MealEntity entity);

}
