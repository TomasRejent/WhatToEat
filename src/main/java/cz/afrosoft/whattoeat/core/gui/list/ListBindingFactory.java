package cz.afrosoft.whattoeat.core.gui.list;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Factory for binding list and combo box together. This means that items selected in combo box will be moved from
 * combo box to list. If item is removed from list it will be moved back to combo box. This is used for example
 * for picking authors of cookbook.
 *
 * @author Tomas Rejent
 */
@Service
public class ListBindingFactory {

    @Autowired
    private ListCellFactory listCellFactory;

    /**
     * Fill values for list bounded to combo box. All previously set values are cleared from list. Then selected items are set to list and difference between
     * allItems and selectedItems is set to combo box.
     *
     * @param list          (NotNull) List bounded to combo box.
     * @param comboBox      (NotNull) Combo box bounded to list.
     * @param allItems      (NotNull) All available items, can include selected ones.
     * @param selectedItems (NotNUll) Items which are selected.
     * @param <T>           Type of both list and combo box items.
     */
    public <T> void fillBoundedList(final ListView<T> list, final ComboBox<T> comboBox, final Collection<T> allItems, final Collection<T> selectedItems) {
        Validate.notNull(list);
        Validate.notNull(comboBox);
        Validate.notNull(allItems);
        Validate.notNull(selectedItems);

        comboBox.getItems().clear();
        list.getItems().clear();
        list.getItems().addAll(selectedItems);
        comboBox.getItems().addAll(allItems.stream().filter(item -> !selectedItems.contains(item)).collect(Collectors.toList()));
    }

    /**
     * Binds list and combo box together. Any item selected in combo box will be moved to list. List is set with removable
     * cells so items can be removed. Item can be removed by remove button or by pressing Delete key.
     * When removed it will get back to combo box.
     * <p>
     * Listener for Escape key is attached to enable selection clearing for list.
     * Items in combo box are preserved in its natural ordering.
     *
     * @param list        (NotNull) List which will display values selected by combo box.
     * @param comboBox    (NotNull) Combo box for selecting values for list.
     * @param mapFunction (NotNull) Function which maps list item to displayed text. This is needed to construct removable list cell factory.
     * @param <T>         Type of both list and combo box items.
     */
    public <T> void bindToComboBox(final ListView<T> list, final ComboBox<T> comboBox, final Function<T, String> mapFunction) {
        Validate.notNull(list);
        Validate.notNull(comboBox);
        Validate.notNull(mapFunction);

        Consumer<T> listRemoveListener = createListRemoveListener(list, comboBox);
        list.setCellFactory(listCellFactory.newRemovableCellFactory(mapFunction, listRemoveListener));
        list.setOnKeyPressed(
                (event) -> {
                    if (event.getCode() == KeyCode.DELETE && !list.getSelectionModel().isEmpty()) {
                        listRemoveListener.accept(list.getSelectionModel().getSelectedItem());
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        list.getSelectionModel().clearSelection();
                    }
                }
        );
        comboBox.valueProperty().addListener(
                createComboBoxChangeListener(list, comboBox)
        );
        comboBox.getItems().sort(null);
    }

    /**
     * Creates listener for combo box changes. It moves selected item from combo box to list.
     *
     * @param list     (NotNull)
     * @param comboBox (NotNull)
     * @param <T>      Type of both list and combo box items.
     * @return (NotNull)
     */
    private <T> ChangeListener<T> createComboBoxChangeListener(final ListView<T> list, final ComboBox<T> comboBox) {
        Validate.notNull(list);
        Validate.notNull(comboBox);

        return (observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(
                        () -> {
                            list.getItems().add(newValue);
                            comboBox.getSelectionModel().clearSelection();
                            comboBox.getItems().remove(newValue);
                        }
                );
            }
        };
    }

    /**
     * Creates listener for list. It moves items to combo box when they are removed in list.
     *
     * @param list     (NotNull)
     * @param comboBox (NotNull)
     * @param <T>      Type of both list and combo box items.
     * @return (NotNull)
     */
    private <T> Consumer<T> createListRemoveListener(final ListView<T> list, final ComboBox<T> comboBox) {
        return (removedItem) -> {
            comboBox.getItems().add(removedItem);
            comboBox.getItems().sort(null);
            list.getItems().remove(removedItem);
        };
    }
}
