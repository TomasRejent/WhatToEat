package cz.afrosoft.whattoeat.core.gui.table;

import javafx.scene.control.TableCell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Table cell for displaying float values.
 *
 * @author Tomas Rejent
 */
public class FloatCell<S> extends TableCell<S, Float> {

    private final int decimalPlaces;

    public FloatCell(final int decimalPlaces) {
        Validate.isTrue(decimalPlaces >= 0);
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    protected void updateItem(final Float item, final boolean empty) {
        if(empty){
            setText(StringUtils.EMPTY);
        } else if(item == null){
            setText("-");
        }else{
            if(decimalPlaces == 0){
                setText(String.valueOf(Math.round(item)));
            } else{
                setText(String.format("%." + decimalPlaces + "f", item));
            }
        }
    }
}
