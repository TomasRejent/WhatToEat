package cz.afrosoft.whattoeat.core.gui.table;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import javafx.scene.control.TableCell;
import org.apache.commons.lang3.StringUtils;

/**
 * Table cell for displaying items implementing {@link Labeled}. Translated message is displayed.
 *
 * @author Tomas Rejent
 */
public class LabeledCell<S, T extends Labeled> extends TableCell<S, T> {

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
