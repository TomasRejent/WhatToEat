package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorParams;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
public class NutritionGeneratorParams extends BasicGeneratorParams {

    private final NutritionCriteria energyCriteria;
    private final NutritionCriteria fatCriteria;
    private final NutritionCriteria saturatedFatCriteria;
    private final NutritionCriteria carbohydrateCriteria;
    private final NutritionCriteria sugarCriteria;
    private final NutritionCriteria proteinCriteria;
    private final NutritionCriteria saltCriteria;
    private final NutritionCriteria fiberCriteria;

    private final RecipeFilter breakfastFilter;
    private final RecipeFilter snackFilter;
    private final RecipeFilter lunchFilter;
    private final RecipeFilter afternoonSnackFilter;
    private final RecipeFilter dinnerFilter;

    public NutritionGeneratorParams(final LocalDate from, final LocalDate to, final User user, final RecipeFilter filter, final Set<MealTime> dishes, final NutritionCriteria energyCriteria, final NutritionCriteria fatCriteria, final NutritionCriteria saturatedFatCriteria, final NutritionCriteria carbohydrateCriteria, final NutritionCriteria sugarCriteria, final NutritionCriteria proteinCriteria, final NutritionCriteria saltCriteria, final NutritionCriteria fiberCriteria, final RecipeFilter breakfastFilter, final RecipeFilter snackFilter, final RecipeFilter lunchFilter, final RecipeFilter afternoonSnackFilter, final RecipeFilter dinnerFilter) {
        super(from, to, filter, dishes, user);
        this.energyCriteria = energyCriteria;
        this.fatCriteria = fatCriteria;
        this.saturatedFatCriteria = saturatedFatCriteria;
        this.carbohydrateCriteria = carbohydrateCriteria;
        this.sugarCriteria = sugarCriteria;
        this.proteinCriteria = proteinCriteria;
        this.saltCriteria = saltCriteria;
        this.fiberCriteria = fiberCriteria;
        this.breakfastFilter = breakfastFilter;
        this.snackFilter = snackFilter;
        this.lunchFilter = lunchFilter;
        this.afternoonSnackFilter = afternoonSnackFilter;
        this.dinnerFilter = dinnerFilter;
    }

    public NutritionCriteria getEnergyCriteria() {
        return energyCriteria;
    }

    public NutritionCriteria getFatCriteria() {
        return fatCriteria;
    }

    public NutritionCriteria getSaturatedFatCriteria() {
        return saturatedFatCriteria;
    }

    public NutritionCriteria getCarbohydrateCriteria() {
        return carbohydrateCriteria;
    }

    public NutritionCriteria getSugarCriteria() {
        return sugarCriteria;
    }

    public NutritionCriteria getProteinCriteria() {
        return proteinCriteria;
    }

    public NutritionCriteria getSaltCriteria() {
        return saltCriteria;
    }

    public NutritionCriteria getFiberCriteria() {
        return fiberCriteria;
    }

    public RecipeFilter getBreakfastFilter() {
        return breakfastFilter;
    }

    public RecipeFilter getSnackFilter() {
        return snackFilter;
    }

    public RecipeFilter getLunchFilter() {
        return lunchFilter;
    }

    public RecipeFilter getAfternoonSnackFilter() {
        return afternoonSnackFilter;
    }

    public RecipeFilter getDinnerFilter() {
        return dinnerFilter;
    }
}
