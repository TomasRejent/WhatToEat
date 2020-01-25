package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import java.util.Optional;

/**
 * Update object for {@link cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts} Serves for its creation or editing.
 * This allows {@link cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts} to be immutable.
 *
 * @author Tomas Rejent
 */
public interface NutritionFactsUpdateObject{

    Optional<Integer> getId();

    Optional<Float> getEnergy();

    Optional<Float> getFat();

    Optional<Float> getSaturatedFat();

    Optional<Float> getCarbohydrate();

    Optional<Float> getSugar();

    Optional<Float> getProtein();

    Optional<Float> getSalt();

    Optional<Float> getFiber();

    NutritionFactsUpdateObject setEnergy(Float energy);

    NutritionFactsUpdateObject setFat(Float fat);

    NutritionFactsUpdateObject setSaturatedFat(Float saturatedFat);

    NutritionFactsUpdateObject setCarbohydrate(Float carbohydrate);

    NutritionFactsUpdateObject setSugar(Float sugar);

    NutritionFactsUpdateObject setProtein(Float protein);

    NutritionFactsUpdateObject setSalt(Float salt);

    NutritionFactsUpdateObject setFiber(Float fiber);

    /**
     * Utility method for checking if this update object has any useful nutrition facts value set.
     *
     * @return True if any nutrition facts value is set. False otherwise.
     */
    boolean hasAnyUsefulValue();
}
