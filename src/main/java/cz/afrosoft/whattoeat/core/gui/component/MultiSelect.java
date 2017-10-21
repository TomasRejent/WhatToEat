package cz.afrosoft.whattoeat.core.gui.component;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import impl.org.controlsfx.skin.CheckComboBoxSkin;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.CheckComboBox;

import java.util.Optional;


/**
 * Extension of {@link CheckComboBox} which allows to select items by clicking on label. {@link CheckComboBox} allow to select item
 * only by clicking on checkbox, which is not user friendly.
 * <p>
 * This functionality is achieved by adding on click listener to every list cell rendered by {@link CheckComboBox}.
 * Listeners are added in cell factory of combo box. Original cell factory is wrapped by one which adds listener.
 * <p>
 * Cell factory is replaced only when using default skin. If you set custom skin by {@link #setSkin(Skin)} or in css by
 * -fx-skin then you must implement this behavior yourselves. This is because lookup of underlying {@link ComboBox} is dependent
 * on skin implementation.
 *
 * Another added functionality is ability to navigate in items by keys and change item selection by enter key.
 *
 * @author Tomas Rejent
 */
public class MultiSelect<T> extends CheckComboBox<T> {

    private ComboBox<T> comboBox;

    public MultiSelect() {
        setupFocusTraversal();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        CheckComboBoxSkin<T> skin = new CheckComboBoxSkin<>(this);
        comboBox = getComboBox(skin);
        setUpComboBox();
        return skin;
    }

    private ComboBox<T> getComboBox(final CheckComboBoxSkin<T> skin) {
        Validate.notNull(skin);
        return (ComboBox<T>) skin.getChildren().stream().filter(node -> node instanceof ComboBox).findFirst().get();
    }

    private void setUpComboBox() {
        comboBox.setOnShown(showEvent -> {
            ((ComboBoxListViewSkin<T>) comboBox.getSkin()).getListView().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ESCAPE) {
                    comboBox.hide();
                    comboBox.requestFocus();
                    event.consume();
                }
            });
        });

        Callback<ListView<T>, ListCell<T>> cellFactory = comboBox.getCellFactory();
        comboBox.setCellFactory(param -> {
            ListCell<T> cell = cellFactory.call(param);
            cell.setOnMouseClicked(createClickHandler(cell));
            cell.setOnKeyPressed(createKeyHandler(cell));
            return cell;
        });

        comboBox.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DOWN) {
                openComboBox();
                event.consume();
            }
        });
    }

    private void setupFocusTraversal() {
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (BooleanUtils.isTrue(newValue)) {
                openComboBox();
            }
        });
    }

    private void openComboBox() {
        comboBox.show();
        comboBox.getSelectionModel().select(0);
    }

    /**
     * Creates handler for clicking on combo box cell. This handler select item in cell.
     * <p>
     * First, clicked item is unselected in underlying list view selection model. This is needed else cell is marked as
     * selected and this causes blue background of cell after click, which is not desired.
     * Then item is checked / unchecked by inverting item boolean checked property.
     *
     * @param cell (NotNull) Cell for which handler is created.
     * @return (NotNull) New handler for mouse click.
     */
    private EventHandler<MouseEvent> createClickHandler(final ListCell<T> cell) {
        Validate.notNull(cell);

        return event -> {
            int index = cell.getIndex();
            cell.getListView().getSelectionModel().clearSelection(index);
            negateCheckedProperty(index);
            event.consume();
        };
    }

    private EventHandler<KeyEvent> createKeyHandler(final ListCell<T> cell) {
        Validate.notNull(cell);

        cell.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (BooleanUtils.isTrue(newValue)) {
                Optional.ofNullable(cell.getGraphic()).ifPresent(Node::requestFocus);
            }
        });

        return event -> {
            if (event.getCode() == KeyCode.ENTER) {
                negateCheckedProperty(cell.getIndex());
                event.consume();
            }
        };
    }

    private void negateCheckedProperty(final int index) {
        BooleanProperty itemBooleanProperty = getItemBooleanProperty(index);
        itemBooleanProperty.setValue(!itemBooleanProperty.get());
    }
}
