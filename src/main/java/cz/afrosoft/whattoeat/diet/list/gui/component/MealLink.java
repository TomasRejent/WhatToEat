package cz.afrosoft.whattoeat.diet.list.gui.component;

import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.core.gui.component.RecipeLink;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomas.rejent
 */
@FXMLComponent(fxmlPath = "/component/RecipeLink.fxml")
public class MealLink extends RecipeLink {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealLink.class);

    private final Meal meal;

    public MealLink(final Meal meal) {
        super(meal.getRecipe());
        this.meal = meal;
    }

    @Override
    protected String getLinkText() {
        return super.getLinkText() + " (" + meal.getServings() + ")";
    }

    @Override
    protected void handleOnClick(final MouseEvent mouseEvent) {
        LOGGER.debug("Meal link clicked. Meal {}", meal);
        RecipeViewDialog dialog = applicationContext.getBean(RecipeViewDialog.class);
        Recipe loadedRecipe = recipeService.getRecipeById(meal.getRecipe().getId());
        dialog.showRecipe(loadedRecipe, meal.getServings());
    }
}
