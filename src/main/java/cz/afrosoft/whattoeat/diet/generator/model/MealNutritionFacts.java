package cz.afrosoft.whattoeat.diet.generator.model;

import org.apache.commons.lang3.Validate;

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public class MealNutritionFacts {

    private String mealName;
    private Float energy;
    private Float fat;
    private Float saturatedFat;
    private Float carbohydrate;
    private Float sugar;
    private Float protein;
    private Float salt;
    private Float fibre;
    private boolean nutritionFactMissing;

    public String getMealName() {
        return mealName;
    }

    public MealNutritionFacts setMealName(final String mealName) {
        this.mealName = mealName;
        return this;
    }

    public Float getEnergy() {
        return energy;
    }

    public MealNutritionFacts setEnergy(final Float energy) {
        this.energy = energy;
        return this;
    }

    public Float getFat() {
        return fat;
    }

    public MealNutritionFacts setFat(final Float fat) {
        this.fat = fat;
        return this;
    }

    public Float getSaturatedFat() {
        return saturatedFat;
    }

    public MealNutritionFacts setSaturatedFat(final Float saturatedFat) {
        this.saturatedFat = saturatedFat;
        return this;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public MealNutritionFacts setCarbohydrate(final Float carbohydrate) {
        this.carbohydrate = carbohydrate;
        return this;
    }

    public Float getSugar() {
        return sugar;
    }

    public MealNutritionFacts setSugar(final Float sugar) {
        this.sugar = sugar;
        return this;
    }

    public Float getProtein() {
        return protein;
    }

    public MealNutritionFacts setProtein(final Float protein) {
        this.protein = protein;
        return this;
    }

    public Float getSalt() {
        return salt;
    }

    public MealNutritionFacts setSalt(final Float salt) {
        this.salt = salt;
        return this;
    }

    public Float getFibre() {
        return fibre;
    }

    public MealNutritionFacts setFibre(final Float fibre) {
        this.fibre = fibre;
        return this;
    }

    public boolean isNutritionFactMissing() {
        return nutritionFactMissing;
    }

    public MealNutritionFacts setNutritionFactMissing(final boolean nutritionFactMissing) {
        this.nutritionFactMissing = nutritionFactMissing;
        return this;
    }

    public Float getByType(NutritionFactType type){
        Validate.notNull(type);

        switch (type){
            case ENERGY:
                return getEnergy();
            case FAT:
                return getFat();
            case SATURATED_FAT:
                return getSaturatedFat();
            case CARBOHYDRATE:
                return getCarbohydrate();
            case SUGAR:
                return getSugar();
            case PROTEIN:
                return getProtein();
            case SALT:
                return getSalt();
            case FIBRE:
                return getFibre();
            default:
                throw new IllegalArgumentException("Unsupported nutrition fact type: " + type);
        }
    }

    public MealNutritionFacts setByType(NutritionFactType type, Float value){
        switch (type){
            case ENERGY:
                setEnergy(value);
                break;
            case FAT:
                setFat(value);
                break;
            case SATURATED_FAT:
                setSaturatedFat(value);
                break;
            case CARBOHYDRATE:
                setCarbohydrate(value);
                break;
            case SUGAR:
                setSugar(value);
                break;
            case PROTEIN:
                setProtein(value);
                break;
            case SALT:
                setSalt(value);
                break;
            case FIBRE:
                setFibre(value);
                break;
        }
        return this;
    }

    public static MealNutritionFacts add(MealNutritionFacts facts1, MealNutritionFacts facts2){
        MealNutritionFacts result = new MealNutritionFacts();
        for(NutritionFactType type : NutritionFactType.values()){
            result.setByType(type, Optional.ofNullable(facts1.getByType(type)).orElse(0f) + Optional.ofNullable(facts2.getByType(type)).orElse(0f));
        }
        return result;
    }
}
