package cz.afrosoft.whattoeat.diet.list.gui.table;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.gui.component.RecipeLink;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;

/**
 * Table cell for displaying list of meals. It displays number of servings and recipe name for each meal.
 *
 * @author tomas.rejent
 */
public class RecipeLinkCell<I> extends TableCell<I, RecipeRef> {

    private final VBox layout = new VBox();
    private final ApplicationContext applicationContext;

    public RecipeLinkCell(final ApplicationContext applicationContext) {
        Validate.notNull(applicationContext);

        this.applicationContext = applicationContext;
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        //setGraphic(layout);
    }

    @Override
    protected void updateItem(final RecipeRef item, final boolean empty) {
        super.updateItem(item, empty);
        layout.getChildren().clear();

        if (!empty && item != null) {
            RecipeLink recipeLink = applicationContext.getBean(RecipeLink.class, item);
            setGraphic(recipeLink);
        }
    }
}
