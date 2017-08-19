package cz.afrosoft.whattoeat.core.gui.suggestion;

import cz.afrosoft.whattoeat.core.gui.list.ListCellFactory;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.Objects;
import java.util.function.Function;

/**
 * Util class for initializing suggestion for combo boxes. This way user can quickly search through many items
 * if he knows at least part of what he is looking for. If he does not know then he can view items in combo box
 * and pick one.
 *
 * @author Tomas Rejent
 */
public class ComboBoxSuggestion {

    /**
     * When width of suggestion popup is determined this value is subtracted from width of combo box because
     * else popup is bigger than combo box because of its border.
     */
    private static final int BORDER_OFFSET = 2;

    /**
     * Sets up suggestion for specified combo box. This sets combo box to editable mode, creates and sets string converter
     * based on map function, creates cell factory based on map function so suggestion text match text in list.
     * <p>
     * When suggestion is selected its value is filled automatically to combo box.
     *
     * @param comboBox    (NotNull)
     * @param mapFunction (NotNull)
     * @param <T>         Type of items in combo box.
     */
    public static <T> void initSuggestion(final ComboBox<T> comboBox, final Function<T, String> mapFunction) {
        StringConverter<T> stringConverter = createStringConverter(comboBox, mapFunction);
        ComboBoxSuggestionProvider<T> suggestionProvider = new ComboBoxSuggestionProvider<>(comboBox, mapFunction);
        comboBox.setEditable(true);
        comboBox.setConverter(stringConverter);
        comboBox.setCellFactory(ListCellFactory.newCellFactory(mapFunction));
        AutoCompletionBinding<T> suggestionBinding = TextFields.bindAutoCompletion(comboBox.getEditor(), suggestionProvider, stringConverter);
        //finishing auto complete sets value to combo box
        suggestionBinding.setOnAutoCompleted(
                event -> comboBox.getSelectionModel().select(event.getCompletion())
        );
        //width of auto complete popup matches width of combo box
        comboBox.widthProperty().addListener(
                (observable, oldValue, newValue) -> suggestionBinding.setPrefWidth(newValue.doubleValue() - BORDER_OFFSET)
        );
    }

    /**
     * Creates string converter for specified combo box which uses supplied map function to do conversion.
     *
     * @param comboBox    (NotNull) Combo box for which converter is created.
     * @param mapFunction (NotNull) Function which maps item from combo box to String.
     * @param <T>         Type of items in combo box.
     * @return (NotNull) New converter based on map function.
     */
    private static <T> StringConverter<T> createStringConverter(final ComboBox<T> comboBox, final Function<T, String> mapFunction) {
        Validate.notNull(comboBox);
        Validate.notNull(mapFunction);

        return new StringConverter<T>() {
            @Override
            public String toString(final T object) {
                if (object == null) {
                    return StringUtils.EMPTY;
                } else {
                    return mapFunction.apply(object);
                }
            }

            @Override
            public T fromString(final String string) {
                return comboBox.getItems().stream().filter(
                        object -> Objects.equals(string, toString(object))
                ).findFirst().orElse(null);
            }
        };
    }

    private ComboBoxSuggestion() {
        throw new IllegalStateException("This class cannot be instanced");
    }
}
