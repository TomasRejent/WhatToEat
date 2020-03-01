package cz.afrosoft.whattoeat.core.gui.table;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public class NutritionFactsIconCell<S> extends TableCell<S, Optional<NutritionFacts>> {

    public NutritionFactsIconCell() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(final Optional<NutritionFacts> item, final boolean empty) {
        super.updateItem(item, empty);
        if(!empty && item.isPresent()){
            Glyph icon = new Glyph("FontAwesome", FontAwesome.Glyph.LIST_ALT).color(Color.GREEN);
            setGraphic(icon);
        }else {
            setGraphic(null);
        }
    }

}
