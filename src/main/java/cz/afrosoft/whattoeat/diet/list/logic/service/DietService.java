package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;

import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface DietService {

    Set<Diet> getAllDiets();

    DietCreateObject getCreateObject();

    Diet create(DietCreateObject dietChanges);

}
