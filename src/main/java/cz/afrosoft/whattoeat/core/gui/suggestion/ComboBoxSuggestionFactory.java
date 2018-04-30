package cz.afrosoft.whattoeat.core.gui.suggestion;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.list.ListCellFactory;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

/**
 * Factory for initializing suggestion for combo boxes. This way user can quickly search through many items
 * if he knows at least part of what he is looking for. If he does not know then he can view items in combo box
 * and pick one.
 *
 * @author Tomas Rejent
 */
@Service
public final class ComboBoxSuggestionFactory {

    @Autowired
    private ListCellFactory listCellFactory;

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
    public <T> void initSuggestion(final ComboBox<T> comboBox, final Function<T, String> mapFunction) {
        StringConverter<T> stringConverter = ComboBoxUtils.createStringConverter(comboBox, mapFunction);
        ComboBoxSuggestionProvider<T> suggestionProvider = new ComboBoxSuggestionProvider<>(comboBox, mapFunction);
        comboBox.setEditable(true);
        comboBox.setConverter(stringConverter);
        comboBox.setCellFactory(listCellFactory.newCellFactory(mapFunction));
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
}
