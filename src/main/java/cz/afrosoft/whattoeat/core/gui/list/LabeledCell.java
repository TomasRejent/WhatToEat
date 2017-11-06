package cz.afrosoft.whattoeat.core.gui.list;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import javafx.scene.control.ListCell;
import org.apache.commons.lang3.StringUtils;

/**
 * List cell for displaying items implementing {@link Labeled}. Translated message is displayed.
 *
 * @author Tomas Rejent
 */
public class LabeledCell<T extends Labeled> extends ListCell<T> {

    @Override
    protected void updateItem(final T item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(StringUtils.EMPTY);
        } else {
            setText(I18n.getText(item.getLabelKey()));
        }
    }
}
