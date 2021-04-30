package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;
import cz.afrosoft.whattoeat.core.logic.model.NamedEntity;

/**
 * Represents reference to {@link Ingredient} which can be used in related entities.
 * Reference provides only limited subset of attributes and does not contain any other
 * related entities. It always contains id.
 *
 * @author Tomas Rejent
 */
public interface IngredientRef extends IdEntity, NamedEntity {

    /**
     * @return Gets name of manufacturer.
     */
    String getManufacturer();

    /**
     * @return Gets full name which is combination of name and manufacturer.
     */
    String getFullName();

}
