package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;

/**
 * Enables to specify and transfer info about createOrUpdate of unit conversion.
 * This enables {@link UnitConversion} to exist without setter methods, so its implementation
 * can be immutable. Provides fluent setters for all editable parameters.
 *
 * @author Tomas Rejent
 */
public interface UnitConversionUpdateObject extends UnitConversion {

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

}
