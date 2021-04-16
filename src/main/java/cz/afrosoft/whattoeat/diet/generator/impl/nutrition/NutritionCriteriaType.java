package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.core.gui.Labeled;
import cz.afrosoft.whattoeat.diet.generator.model.NutritionFactType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tomas Rejent
 */
public enum NutritionCriteriaType implements Labeled {
    ENERGY(7859, 5, 7, 7, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.energy"),
    FAT(56.5f, 1, 1, 8, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.fat"),
    SATURATED_FAT(20.7f, 6, 15, 15, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.saturatedFat"),
    CARBOHYDRATE(185, 4, 10, 10, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.carbohydrate"),
    SUGAR(32.9f, 8, 20, 35, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.sugar"),
    PROTEIN(148, 2, 8, 8, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.protein"),
    SALT(5, 3, 5, 5, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.salt"),
    FIBER(27.5f, 7, 15, 25, "cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.fiber");

    private final float defaultAmount;
    private final float defaultPriority;
    private final float defaultBelowTolerance;
    private final float defaultAboveTolerance;
    private final String labelKey;

    NutritionCriteriaType(final float defaultAmount, final float defaultPriority, final float defaultBelowTolerance, final float defaultAboveTolerance, final String labelKey) {
        this.defaultAmount = defaultAmount;
        this.defaultPriority = defaultPriority;
        this.defaultBelowTolerance = defaultBelowTolerance;
        this.defaultAboveTolerance = defaultAboveTolerance;
        this.labelKey = labelKey;
    }

    public float getDefaultAmount() {
        return defaultAmount;
    }

    public float getDefaultPriority() {
        return defaultPriority;
    }

    public float getDefaultBelowTolerance() {
        return defaultBelowTolerance;
    }

    public float getDefaultAboveTolerance() {
        return defaultAboveTolerance;
    }


    @Override
    public String getLabelKey() {
        return labelKey;
    }

    public static NutritionCriteria getNutritionCriteriaByType(NutritionFactType type){
        switch (type){
            case ENERGY:
                return ENERGY.toDefaultNutritionCriteria();
            case FAT:
                return FAT.toDefaultNutritionCriteria();
            case SATURATED_FAT:
                return SATURATED_FAT.toDefaultNutritionCriteria();
            case CARBOHYDRATE:
                return CARBOHYDRATE.toDefaultNutritionCriteria();
            case SUGAR:
                return SUGAR.toDefaultNutritionCriteria();
            case PROTEIN:
                return PROTEIN.toDefaultNutritionCriteria();
            case SALT:
                return SALT.toDefaultNutritionCriteria();
            case FIBRE:
                return FIBER.toDefaultNutritionCriteria();
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    private NutritionCriteria toDefaultNutritionCriteria(){
        return new NutritionCriteria(this, this.defaultAmount, this.defaultAboveTolerance, this.defaultBelowTolerance, Math.round(this.defaultPriority));
    }
}
