package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Represents reference to {@link Meal} which can be used in related entities.
 * Reference provides only limited subset of attributes and does not contain any other
 * related entities. It always contains id.
 *
 * @author Tomas Rejent
 */
public interface MealRef extends IdEntity {

}
