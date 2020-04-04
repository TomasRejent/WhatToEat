package cz.afrosoft.whattoeat.core.gui.table;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.Validate;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.util.function.BiFunction;

/**
 * Cell for displaying colorized icon based on supplied producer functions.
 * @author Tomas Rejent
 */
public class ColorIconCell<S, V> extends TableCell<S, V> {

    private BiFunction<S, V, Color> getColor;
    private BiFunction<S, V, FontAwesome.Glyph> getIcon;

    public ColorIconCell(BiFunction<S, V, FontAwesome.Glyph> getIcon, BiFunction<S, V, Color> getColor) {
        Validate.notNull(getIcon);
        Validate.notNull(getColor);

        this.getIcon = getIcon;
        this.getColor = getColor;
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(final V item, final boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setGraphic(null);
        }else{
            S rowValue = this.getTableView().getItems().get(this.getIndex());
            FontAwesome.Glyph iconGlyph = getIcon.apply(rowValue, item);
            if(iconGlyph == null){
                setGraphic(null);
            } else {
                Color color = getColor.apply(rowValue, item);
                Glyph icon = new Glyph("FontAwesome", iconGlyph).color(color);
                setGraphic(icon);
            }
        }
    }
}
