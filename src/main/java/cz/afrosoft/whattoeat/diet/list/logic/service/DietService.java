package cz.afrosoft.whattoeat.diet.list.logic.service;

import java.util.Set;

import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;

/**
 * @author Tomas Rejent
 */
public interface DietService {

    Set<Diet> getAllDiets();

    DietCreateObject getCreateObject();

    Diet create(DietCreateObject dietChanges);

    void delete(Diet diet);

}
