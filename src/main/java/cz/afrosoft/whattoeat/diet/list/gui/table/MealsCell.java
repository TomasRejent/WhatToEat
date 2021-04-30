package cz.afrosoft.whattoeat.diet.list.gui.table;

import cz.afrosoft.whattoeat.core.gui.I18n;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;

import java.util.List;

import cz.afrosoft.whattoeat.diet.list.gui.component.MealLink;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;

/**
 * Table cell for displaying list of meals. It displays number of servings and recipe name for each meal.
 *
 * @author tomas.rejent
 */
public class MealsCell extends TableCell<DayDiet, List<Meal>> {

    private final VBox layout = new VBox();
    private final ApplicationContext applicationContext;

    public MealsCell(final ApplicationContext applicationContext) {
        Validate.notNull(applicationContext);

        this.applicationContext = applicationContext;
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(layout);
    }

    @Override
    protected void updateItem(final List<Meal> item, final boolean empty) {
        super.updateItem(item, empty);
        layout.getChildren().clear();

        if (item != null) {
            item.forEach(meal -> layout.getChildren().add(
                getMealComponent(meal)
            ));
        }
    }

    private Node getMealComponent(final Meal meal){
        if(meal.getRecipe() != null) {
            return applicationContext.getBean(MealLink.class, meal);
        } else {
            return new Label(
                "(" + meal.getAmount() + I18n.getText("cz.afrosoft.whattoeat.ingredientUnit.weight") + ") " + meal.getIngredient().getFullName()
            );
        }
    }
}
