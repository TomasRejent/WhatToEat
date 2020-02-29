package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DietCopyParams;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface DietService {

    Diet getById(Integer id);

    Set<Diet> getAllDiets();

    DietCreateObject getCreateObject();

    Diet create(DietCreateObject dietChanges);

    Diet copy(Diet source, DietCopyParams params);

    void delete(Diet diet);

    Collection<Meal> getDietMeals(Diet diet);

    /**
     * Replaces existing day diets with specified ones.
     * @param diet Diet to update.
     * @param dayDiets List of new day diets to save.
     */
    void replaceDayDiets(Diet diet, List<DayDietEntity> dayDiets);

}
