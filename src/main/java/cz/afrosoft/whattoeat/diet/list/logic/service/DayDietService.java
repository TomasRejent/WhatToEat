package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;

/**
 * @author Tomas Rejent
 */
public interface DayDietService {

    DayDiet loadDayDiet(DayDietRef reference);

}
