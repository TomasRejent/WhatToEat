package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.NutritionFactsEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public interface NutritionFactsService {

    NutritionFactsUpdateObject getCreateObject();

    NutritionFactsUpdateObject getUpdateObject(NutritionFacts nutritionFacts);

    NutritionFactsEntity toEntity(NutritionFactsUpdateObject nutritionFacts);

    Optional<NutritionFacts> toNutritionFacts(NutritionFactsEntity nutritionFacts);

    /**
     * Converts basic energy rate joule/g to human readable kJ/100g.
     * @param baseEnergy
     * @return
     */
    Float energyToHumanReadable(Float baseEnergy);

    /**
     * Converts human readable energy kJ/100g to base joule/g.
     * @param humanReadableEnergy
     * @return
     */
    Float energyToBase(Float humanReadableEnergy);

    /**
     * Converts from base nutrition /g to human readable /100g.
     * @param baseNutrition
     * @return
     */
    Float nutritionToHumanReadable(Float baseNutrition);

    /**
     * Converts from human readable /100g to base /g.
     * @param humanReadableNutrition
     * @return
     */
    Float nutritionToBase(Float humanReadableNutrition);
}
