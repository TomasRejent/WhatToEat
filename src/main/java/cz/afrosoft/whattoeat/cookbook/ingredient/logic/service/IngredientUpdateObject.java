package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

/**
 * Update object for {@link Ingredient} Serves for its creation or editing. This allows {@link Ingredient} to be immutable.
 *
 * @author Tomas Rejent
 */
public interface IngredientUpdateObject {

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity or optional filled with id of entity which is edited.
     */
    Optional<Integer> getId();

    /**
     * @return (NotNull) Name of ingredient. Is empty optional if update object is for creating new entity and value was not yet set by {@link #setName(String)}. Else is
     * optional filled with name set by {@link #setName(String)}. If name was not set and this object is for update of existing ingredient then it contains original name
     * of ingredient.
     */
    Optional<String> getName();

    /**
     * @return (NotNull) Unit for measuring ingredient. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setIngredientUnit(IngredientUnit)}. Else is optional filled with unit set by {@link #setIngredientUnit(IngredientUnit)}. If unit was not set and this object is
     * for update of existing ingredient then it contains original unit of ingredient.
     */
    Optional<IngredientUnit> getIngredientUnit();

    /**
     * @return (NotNull) Price of one unit of ingredient. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setPrice(float)}. Else is optional filled with unit set by {@link #setPrice(float)}. If price was not set and this object is for update of existing ingredient
     * then it contains original price of ingredient.
     */
    Optional<Float> getPrice();

    /**
     * @return (NotNull) Unit conversion info for this ingredient. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setUnitConversion(UnitConversionUpdateObject)} or {@link #setUnitConversion(Float, Float, Float, Float, Float)}. Else is optional filled with one of those method.
     * If conversion info was not set and this object is for update of existing ingredient then it contains original unit conversion of ingredient.
     */
    Optional<UnitConversionUpdateObject> getUnitConversion();

    /**
     * @return (NotNull) Keywords of ingredient. Is empty set if update object is for creating new entity and value was not yet set by {@link #setKeywords(Set)}. Else is
     * set filled with keywords set by {@link #setKeywords(Set)}. If keywords were not set and this object is for update of existing ingredient then it contains original
     * keywords of ingredient.
     */
    Set<Keyword> getKeywords();

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
    IngredientUpdateObject setUnitConversion(UnitConversionUpdateObject unitConversion);

    /**
     * Changes unit conversion info about ingredient. This is optional for ingredient. Conversion is specified by conversion rates.
     *
     * @param gramsPerPiece       (Nullable)
     * @param milliliterPerGram   (Nullable)
     * @param gramsPerPinch       (Nullable)
     * @param gramsPerCoffeeSpoon (Nullable)
     * @param gramsPerSpoon       (Nullable)
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setUnitConversion(Float gramsPerPiece, Float milliliterPerGram, Float gramsPerPinch, Float gramsPerCoffeeSpoon, Float gramsPerSpoon);

    /**
     * Changes nutrition facts about ingredient.
     * @param nutritionFacts (Nullable)
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setNutritionFacts(NutritionFactsUpdateObject nutritionFacts);

    NutritionFactsUpdateObject getNutritionFacts();

    /**
     * Changes keywords of ingredient. If called multiple times only value from last call is used.
     *
     * @param keywords (NotNull) Keywords to associate with ingredient.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    IngredientUpdateObject setKeywords(Set<Keyword> keywords);
}
