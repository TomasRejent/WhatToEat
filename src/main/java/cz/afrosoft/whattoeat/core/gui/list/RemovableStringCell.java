package cz.afrosoft.whattoeat.core.gui.list;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Cell which renders remove button aligned to right side of cell. This button allows to remove item
 * from list. Custom listeners
 *
 * @author Tomas Rejent
 */
final class RemovableStringCell<T> extends CustomStringCell<T> {

    private final HBox layout;
    private final Label label;
    private final Button removeButton;

    private final Collection<Consumer<T>> removeListeners;

    /**
     * Creates new removable cell.
     *
     * @param mapFunction     (NotNull) Function to map list item to its displayed text representation.
     * @param removeListeners (NotNull) Listeners for action of remove button. May be empty.
     */
    RemovableStringCell(final Function<T, String> mapFunction, final Collection<Consumer<T>> removeListeners) {
        super(mapFunction);
        Validate.notNull(removeListeners);

        this.label = new Label();
        this.removeButton = new Button(StringUtils.EMPTY, new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE));
        this.layout = new HBox(label, removeButton);
        this.removeListeners = removeListeners;

        layout.setAlignment(Pos.CENTER_LEFT);
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        setupRemoveEvent();
    }

    @Override
    protected void updateItem(final T item, final boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            label.setText(getText());
            setGraphic(layout);
        }
    }

    /**
     * Sets up event for remove button. Its action removes item from list and calls
     * all remove listeners.
     */
    private void setupRemoveEvent() {
        removeButton.setOnAction(
                event -> {
                    T removedItem = getItem();
                    getListView().getItems().remove(removedItem);
                    removeListeners.forEach(listener -> listener.accept(removedItem));
                }
        );
    }
}
