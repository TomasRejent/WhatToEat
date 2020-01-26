package cz.afrosoft.whattoeat.diet.generator.model;

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
}
