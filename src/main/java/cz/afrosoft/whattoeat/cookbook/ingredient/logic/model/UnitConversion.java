package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Represents definition of conversion between various units of {@link Ingredient}.
 * Not all conversion values have to be defined.
 *
 * @author Tomas Rejent
 */
public interface UnitConversion extends IdEntity {

    /**
     * @return (Nullable) Gets number of grams per average piece of ingredient.
     * For example average onion has 90g.
     */
    Float getGramsPerPiece();

    /**
     * @return (Nullable) Gets number of milliliters per gram. For example
     * milk has 1.001 milliliters per gram.
     */
    Float getMilliliterPerGram();

    /**
     * @return (Nullable) Gets grams per pinch. This conversion is typically used for
     * spices. For example one pinch of rosemary weights 0.1g.
     */
    Float getGramsPerPinch();

    /**
     * @return (Nullable) Gets grams per cofee spoon. This conversion is typically used for
     * spices. For example one cofee spoon of salt weights 1.5g.
     * <p>
     * Meaning of spoon is that content is leveled so it is not above edges of spoon.
     * This allows to use same conversion field also for liquids.
     */
    Float getGramsPerCoffeeSpoon();

    /**
     * @return (Nullable) Gets grams per spoon. This conversion is typically used for
     * spices. For example one spoon of marjoram has 1.2g.
     * <p>
     * Meaning of spoon is that content is leveled so it is not above edges of spoon.
     * This allows to use same conversion field also for liquids.
     */
    Float getGramsPerSpoon();

}
