package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import java.util.Set;

/**
 * Enables to specify and transfer info about createOrUpdate of ingredient.
 * This enables {@link Ingredient} to exist without setter methods, so its implementation
 * can be immutable. Provides fluent setters for all editable parameters.
 *
 * @author Tomas Rejent
 */
public interface IngredientUpdateObject extends Ingredient {

    /**
     * Changes name of ingredient. If called multiple times only value from last call is used.
     *
     * @param name (NotBlank) Name of ingredient to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setName(String name);

    /**
     * Changes unit of ingredient. If called multiple times only value from last call is used.
     *
     * @param ingredientUnit (NotNull) Unit of ingredient to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setIngredientUnit(IngredientUnit ingredientUnit);

    /**
     * Changes price of ingredient. If called multiple times only value from last call is used.
     *
     * @param price Price of one Unit of ingredient. National currency is not used anywhere in application.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setPrice(float price);

    /**
     * Changes unit conversion info about ingredient. This is optional for ingredient.
     * If called multiple times only value from last call is used.
     *
     * @param unitConversion (Nullable) Unit conversion to set or null to clear existing one.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setUnitConversion(UnitConversion unitConversion);

    /**
     * Changes keywords of ingredient. If called multiple times only value from last call is used.
     *
     * @param keywords (NotNull) Keywords to associate with ingredient.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setKeywords(Set<Keyword> keywords);
}
