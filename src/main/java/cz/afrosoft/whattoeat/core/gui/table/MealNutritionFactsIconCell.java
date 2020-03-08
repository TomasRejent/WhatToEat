package cz.afrosoft.whattoeat.core.gui.table;

import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

/**
 * @author Tomas Rejent
 */
public class MealNutritionFactsIconCell<S> extends TableCell<S, MealNutritionFacts> {

    public MealNutritionFactsIconCell() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(final MealNutritionFacts item, final boolean empty) {
        super.updateItem(item, empty);
        if(!empty && item != null){
            Glyph icon = new Glyph("FontAwesome", FontAwesome.Glyph.LIST_ALT);
            setGraphic(icon.color(item.isNutritionFactMissing() ? Color.RED : Color.GREEN));
        }else {
            setGraphic(null);
        }
    }

}
