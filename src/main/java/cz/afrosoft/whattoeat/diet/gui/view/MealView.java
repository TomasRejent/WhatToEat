package cz.afrosoft.whattoeat.diet.gui.view;

import cz.afrosoft.whattoeat.diet.logic.model.Meal;

/**
 * Created by Tomas Rejent on 13. 6. 2017.
 */
public class MealView {

    private Meal meal;
    private String recipeName;

    public MealView(Meal meal, String recipeName) {
        this.meal = meal;
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeKey(){
        return meal.getRecipeKey();
    }

    public int getServings(){
        return meal.getServings();
    }

    public void setRecipeKey(String recipeKey){
        meal.setRecipeKey(recipeKey);
    }

    public void setServings(int servings){
        meal.setServings(servings);
    }

    public Meal getMeal() {
        return meal;
    }

    @Override
    public String toString() {
        return recipeName;
    }
}
