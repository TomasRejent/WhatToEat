package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;

import java.util.Collection;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface DietService {

    Set<Diet> getAllDiets();

    DietCreateObject getCreateObject();

    Diet create(DietCreateObject dietChanges);

    void delete(Diet diet);

    Collection<Meal> getDietMeals(Diet diet);

}
