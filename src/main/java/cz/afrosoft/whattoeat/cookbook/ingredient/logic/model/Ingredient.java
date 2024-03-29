package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;
import cz.afrosoft.whattoeat.core.logic.model.KeywordableEntity;
import cz.afrosoft.whattoeat.core.logic.model.NamedEntity;

/**
 * Represents definition of ingredient. Contains all ingredient properties
 * which are not dependent on recipe.
 *
 * @author Tomas Rejent
 */
public interface Ingredient extends IngredientRef, IdEntity, NamedEntity, KeywordableEntity, Comparable<Ingredient> {

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
     * @return (NotNull) Gets optional with unit conversion defined for this ingredient. May be empty optional
     * if ingredient does not have any conversion defined.
     */
    Optional<UnitConversion> getUnitConversion();

    /**
     * @return (NotNull) Gets optional with nutrition facts defined for this ingredient. May be empty optional
     * if ingredient does not have any nutrition facts defined.
     */
    Optional<NutritionFacts> getNutritionFacts();

    Set<Shop> getShops();

    boolean isGeneral();

    boolean isEdible();

    boolean isPurchasable();

    String getManufacturer();

    Optional<RecipeRef> getRecipe();

    Optional<IngredientRef> getParent();

    Set<IngredientRef> getChildren();
}
