package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

/**
 * Stores nutrition facts about ingredient or recipe.
 * @author Tomas Rejent
 */
public interface NutritionFacts extends IdEntity {

    /**
     * @return Gets amount of energy in joules per unit of ingredient or recipe.
     */
    Float getEnergy();

    /**
     * @return Gets amount of fat in grams per unit of ingredient or recipe. Amount of saturated fat is also part of this value.
     */
    Float getFat();

    /**
     * @return Gets amount of saturated fat in grams per unit of ingredient or recipe.
     */
    Float getSaturatedFat();

    /**
     * @return Gets amount of carbohydrate in grams per unit of ingredient or recipe. Amount of sugar is also part of this value.
     */
    Float getCarbohydrate();

    /**
     * @return Gets amount of sugar in grams per unit of ingredient or recipe.
     */
    Float getSugar();

    /**
     * @return Gets amount of protein in grams per unit of ingredient or recipe.
     */
    Float getProtein();

    /**
     * @return Gets amount of salt in grams per unit of ingredient or recipe.
     */
    Float getSalt();

    /**
     * @return Gets amount of fiber in grams per unit of ingredient or recipe.
     */
    Float getFiber();
}
