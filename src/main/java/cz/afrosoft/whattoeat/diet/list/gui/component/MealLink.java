package cz.afrosoft.whattoeat.diet.list.gui.component;

import cz.afrosoft.whattoeat.core.gui.component.RecipeLink;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;

/**
 * @author tomas.rejent
 */
public class MealLink extends RecipeLink {

    private final Meal meal;

    public MealLink(final Meal meal) {
        super(meal.getRecipe());
        this.meal = meal;
    }

    @Override
    protected String getLinkText() {
        return super.getLinkText() + " (" + meal.getServings() + ")";
    }
}
