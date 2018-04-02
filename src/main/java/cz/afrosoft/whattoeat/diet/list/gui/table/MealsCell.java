package cz.afrosoft.whattoeat.diet.list.gui.table;

import org.apache.commons.lang3.Validate;

import java.util.List;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;

/**
 * Table cell for displaying list of meals. It displays number of servings and recipe name for each meal.
 *
 * @author tomas.rejent
 */
public class MealsCell extends TableCell<DayDiet, List<Meal>> {

    private VBox layout = new VBox();

    public MealsCell() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(layout);
    }

    @Override
    protected void updateItem(final List<Meal> item, final boolean empty) {
        super.updateItem(item, empty);
        layout.getChildren().clear();

        if (item != null) {
            item.forEach(meal -> layout.getChildren().add(new Label(mealToString(meal))));
        }
    }

    private String mealToString(final Meal meal) {
        Validate.notNull(meal);

        return meal.getRecipe().getName() + " (" + meal.getServings() + ")";
    }
}
