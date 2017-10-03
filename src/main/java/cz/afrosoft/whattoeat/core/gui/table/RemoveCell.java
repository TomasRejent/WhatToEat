package cz.afrosoft.whattoeat.core.gui.table;

import cz.afrosoft.whattoeat.core.gui.component.RemoveButton;
import javafx.scene.control.TableCell;

/**
 * Table cell with remove button. Makes table row removable by pressing this button.
 *
 * @author Tomas Rejent
 */
public class RemoveCell<T> extends TableCell<T, Void> {

    private final RemoveButton removeButton;

    public RemoveCell() {
        removeButton = new RemoveButton();
        setGraphic(removeButton);
    }

    @Override
    protected void updateItem(final Void item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(removeButton);
        }
    }
}
