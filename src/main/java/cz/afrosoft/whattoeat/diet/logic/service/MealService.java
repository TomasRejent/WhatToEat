package cz.afrosoft.whattoeat.diet.logic.service;

import cz.afrosoft.whattoeat.diet.gui.view.MealView;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;

/**
 * Created by Tomas Rejent on 13. 6. 2017.
 */
public interface MealService {

    /**
     * Converts {@link Meal} to {@link MealView}.
     * @param meal (NotNull) Meal to convert.
     * @return (NotNull) MealView created from specified Meal. Recipe type is loaded and filled into it.
     */
    MealView getMealView(Meal meal);

}
