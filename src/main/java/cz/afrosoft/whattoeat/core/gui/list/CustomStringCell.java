package cz.afrosoft.whattoeat.core.gui.list;

import javafx.scene.control.ListCell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.function.Function;

/**
 * List cell which displays list item as String.
 * This String is obtained from specified map function.
 *
 * @param <T> Type of items in list.
 * @author Tomas Rejent
 */
class CustomStringCell<T> extends ListCell<T> {

    private final Function<T, String> mapFunction;

    /**
     * @param mapFunction (NotNull) Function to map list item to String.
     */
    CustomStringCell(final Function<T, String> mapFunction) {
        Validate.notNull(mapFunction);
        this.mapFunction = mapFunction;
    }

    @Override
    protected void updateItem(final T item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(StringUtils.EMPTY);
        } else {
            setText(mapFunction.apply(item));
        }
    }
}
