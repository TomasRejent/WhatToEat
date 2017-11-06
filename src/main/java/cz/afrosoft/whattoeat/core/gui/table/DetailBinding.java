package cz.afrosoft.whattoeat.core.gui.table;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.MasterDetailPane;

import java.util.function.Function;

/**
 * Provides methods for setting up common behavior for {@link MasterDetailPane} containing {@link TableView} and detail represented by {@link TextArea}.
 *
 * @author Tomas Rejent
 */
public class DetailBinding {

    /**
     * Sets up master detail pane so when row in table is selected then detail for it is displayed.
     * Table is set to allow only selection of single row. Multiple row and cell selection is disabled. Listener which clears table selection on Escape key is added.
     * Detail pane is initially hidden.
     *
     * @param detailPane  (NotNull) Master-Detail pane to set up.
     * @param table       (NotNull) Table contained as master node in detailPane.
     * @param detailArea  (NotNull) Text area to display details about item from table.
     * @param mapFunction (NotNull) Function which maps item from table to its text representing detail of item.
     * @param <T>         Type of items in table.
     */
    public static <T> void bindDetail(final MasterDetailPane detailPane, final TableView<T> table, final TextArea detailArea, final Function<T, String> mapFunction) {
        Validate.notNull(detailPane);
        Validate.notNull(table);
        Validate.notNull(detailArea);
        Validate.notNull(mapFunction);

        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.getSelectionModel().setCellSelectionEnabled(false);
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        detailPane.setShowDetailNode(false);
                        detailArea.clear();
                    } else {
                        detailArea.setText(mapFunction.apply(newValue));
                        detailPane.setShowDetailNode(true);
                    }
                }
        );
        table.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                table.getSelectionModel().clearSelection();
            }
        });
        detailPane.setShowDetailNode(false);
    }

    private DetailBinding() {
        throw new UnsupportedOperationException("This class cannot be instanced.");
    }
}
