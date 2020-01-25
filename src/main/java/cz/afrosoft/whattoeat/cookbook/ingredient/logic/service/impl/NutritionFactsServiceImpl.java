package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.NutritionFactsEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsUpdateObject;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
@Service
public class NutritionFactsServiceImpl implements NutritionFactsService {

    @Override
    public NutritionFactsUpdateObject getCreateObject() {
        return new NutritionFactsImpl.Builder();
    }

    @Override
    public NutritionFactsUpdateObject getUpdateObject(final NutritionFacts nutritionFacts) {
        Validate.notNull(nutritionFacts);
        return new NutritionFactsImpl.Builder(nutritionFacts.getId())
                .setEnergy(nutritionFacts.getEnergy())
                .setFat(nutritionFacts.getFat())
                .setSaturatedFat(nutritionFacts.getSaturatedFat())
                .setCarbohydrate(nutritionFacts.getCarbohydrate())
                .setSugar(nutritionFacts.getSugar())
                .setProtein(nutritionFacts.getProtein())
                .setSalt(nutritionFacts.getSalt())
                .setFiber(nutritionFacts.getFiber());
    }

    @Override
    public NutritionFactsEntity toEntity(final NutritionFactsUpdateObject nutritionFacts) {
        if(nutritionFacts == null){
            return null;
        } else {
            NutritionFactsEntity entity = new NutritionFactsEntity();
            entity.setId(nutritionFacts.getId().orElse(null))
                    .setEnergy(nutritionFacts.getEnergy().orElse(null))
                    .setFat(nutritionFacts.getFat().orElse(null))
                    .setSaturatedFat(nutritionFacts.getSaturatedFat().orElse(null))
                    .setCarbohydrate(nutritionFacts.getCarbohydrate().orElse(null))
                    .setSugar(nutritionFacts.getSugar().orElse(null))
                    .setProtein(nutritionFacts.getProtein().orElse(null))
                    .setSalt(nutritionFacts.getSalt().orElse(null))
                    .setFiber(nutritionFacts.getFiber().orElse(null));
            return entity;
        }
    }

    @Override
    public Optional<NutritionFacts> toNutritionFacts(final NutritionFactsEntity entity) {
        if(entity == null){
            return Optional.empty();
        }else{
            return Optional.of(new NutritionFactsImpl.Builder(entity.getId())
                    .setEnergy(entity.getEnergy())
                    .setFat(entity.getFat())
                    .setSaturatedFat(entity.getSaturatedFat())
                    .setCarbohydrate(entity.getCarbohydrate())
                    .setSugar(entity.getSugar())
                    .setProtein(entity.getProtein())
                    .setSalt(entity.getSalt())
                    .setFiber(entity.getFiber())
                    .build());
        }
    }

    @Override
    public Float energyToHumanReadable(final Float baseEnergy) {
        if(baseEnergy == null){
            return null;
        }
        return baseEnergy/10;
    }

    @Override
    public Float energyToBase(final Float humanReadableEnergy) {
        if(humanReadableEnergy == null){
            return null;
        }
        return humanReadableEnergy*10;
    }

    @Override
    public Float nutritionToHumanReadable(final Float baseNutrition) {
        if(baseNutrition == null){
            return null;
        }
        return baseNutrition*100;
    }

    @Override
    public Float nutritionToBase(final Float humanReadableNutrition) {
        if(humanReadableNutrition == null){
            return null;
        }
        return humanReadableNutrition/100;
    }
}
