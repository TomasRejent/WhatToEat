package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;
import cz.afrosoft.whattoeat.core.logic.model.KeywordableEntity;

/**
 * Represents definition of ingredient. Contains all ingredient properties
 * which are not dependent on recipe.
 *
 * @author Tomas Rejent
 */
public interface Ingredient extends IdEntity, KeywordableEntity {

    /**
     * @return (NotNull) Gets name of ingredient.
     */
    String getName();

    /**
     * @return (NotNull) Usually used unit for measuring this ingredient.
     * Sometimes it make sense that ingredient has more units, for example
     * milk can be measured by both {@link IngredientUnit#VOLUME} and
     * {@link IngredientUnit#WEIGHT}. These cases are solved by {@link UnitConversion}.
     */
    IngredientUnit getIngredientUnit();

    /**
     * @return Gets price for one unit of this ingredient. Currency is not considered.
     * It is assumed currency corresponds to language of recipes.
     */
    float getPrice();

    /**
     * @return (Nullable) Gets unit conversion defined for this ingredient. May be null
     * if ingredient does not have any conversion defined.
     */
    UnitConversion getUnitConversion();
}
