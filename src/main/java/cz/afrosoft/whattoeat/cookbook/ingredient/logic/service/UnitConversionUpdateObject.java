package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;

import java.util.Optional;

/**
 * Update object for {@link UnitConversion} Serves for its creation or editing. This allows {@link UnitConversion} to be immutable.
 *
 * @author Tomas Rejent
 */
public interface UnitConversionUpdateObject {

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity or optional filled with id of entity which is edited.
     */
    Optional<Integer> getId();

    /**
     * @return (NotNull) Conversion rate for grams to pieces. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setGramsPerPiece(Float)}. Else is optional filled with conversion rate set by {@link #setGramsPerPiece(Float)}. If conversion rate was not set and this object is
     * for update of existing recipe then it contains original conversion rate of recipe.
     */
    Optional<Float> getGramsPerPiece();

    /**
     * @return (NotNull) Conversion rate for milliliters to grams. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setMilliliterPerGram(Float)}. Else is optional filled with conversion rate set by {@link #setMilliliterPerGram(Float)}. If conversion rate was not set and this
     * object is for update of existing recipe then it contains original conversion rate of recipe.
     */
    Optional<Float> getMilliliterPerGram();

    /**
     * @return (NotNull) Conversion rate for grams to pinch. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setGramsPerPinch(Float)}. Else is optional filled with conversion rate set by {@link #setGramsPerPinch(Float)}. If conversion rate was not set and this object is
     * for update of existing recipe then it contains original conversion rate of recipe.
     */
    Optional<Float> getGramsPerPinch();

    /**
     * @return (NotNull) Conversion rate for grams to coffee spoon. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setGramsPerCoffeeSpoon(Float)}. Else is optional filled with conversion rate set by {@link #setGramsPerCoffeeSpoon(Float)}. If conversion rate was not set and
     * this object is for update of existing recipe then it contains original conversion rate of recipe.
     */
    Optional<Float> getGramsPerCoffeeSpoon();

    /**
     * @return (NotNull) Conversion rate for grams to spoon. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setGramsPerSpoon(Float)}. Else is optional filled with conversion rate set by {@link #setGramsPerSpoon(Float)}. If conversion rate was not set and this object is
     * for update of existing recipe then it contains original conversion rate of recipe.
     */
    Optional<Float> getGramsPerSpoon();

    /**
     * Changes conversion rate between grams and pieces. If called multiple times only value from last call is used.
     *
     * @param gramsPerPiece (Nullable) Conversion rate between grams and pieces or null to clear it.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    UnitConversionUpdateObject setGramsPerPiece(Float gramsPerPiece);

    /**
     * Changes conversion rate between milliliters and grams. If called multiple times only value from last call is used.
     *
     * @param milliliterPerGram (Nullable) Conversion rate between  milliliters and grams or null to clear it.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    UnitConversionUpdateObject setMilliliterPerGram(Float milliliterPerGram);

    /**
     * Changes conversion rate between grams and pinch. If called multiple times only value from last call is used.
     *
     * @param gramsPerPinch (Nullable) Conversion rate between grams and pinch or null to clear it.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    UnitConversionUpdateObject setGramsPerPinch(Float gramsPerPinch);

    /**
     * Changes conversion rate between grams and coffee spoon. If called multiple times only value from last call is used.
     *
     * @param gramsPerCoffeeSpoon (Nullable) Conversion rate between grams and coffee spoon or null to clear it.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    UnitConversionUpdateObject setGramsPerCoffeeSpoon(Float gramsPerCoffeeSpoon);

    /**
     * Changes conversion rate between grams and spoon. If called multiple times only value from last call is used.
     *
     * @param gramsPerSpoon (Nullable) Conversion rate between grams and spoon or null to clear it.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    UnitConversionUpdateObject setGramsPerSpoon(Float gramsPerSpoon);

    /**
     * Utility method for checking if this update object has any useful (positive) conversion value set.
     *
     * @return True if any conversion value is set to positive number. False otherwise.
     */
    boolean hasAnyUsefulValue();

}
