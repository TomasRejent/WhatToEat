package cz.afrosoft.whattoeat.core.gui.suggestion;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ComboBox;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Suggestion provider for Combo box. Uses items from combo box to suggest them. Items are converted to text
 * by mapFunction. This provider listen to item changes in combo box and updates suggestion based on actual
 * items in combo box. This provider does not suggest item which is already selected in combo box.
 *
 * @param <T> Type of combo box item.
 * @author Tomas Rejent
 */
final class ComboBoxSuggestionProvider<T> extends SuggestionProvider<T> {

    private final Function<T, String> mapFunction;
    private final ComboBox<T> comboBox;

    /**
     * Creates new suggestion provider for specified combo box. This provider must be used only for
     * this combo box, not any other.
     *
     * @param comboBox    (NotNull) Combo box for which suggestions are provided.
     * @param mapFunction (NotNull) Function for mapping combo box items to String.
     */
    ComboBoxSuggestionProvider(final ComboBox<T> comboBox, final Function<T, String> mapFunction) {
        Validate.notNull(comboBox);
        Validate.notNull(mapFunction);

        this.comboBox = comboBox;
        this.mapFunction = mapFunction;
        this.addPossibleSuggestions(comboBox.getItems());
        setupItemChangeListener();
    }

    /**
     * Set ups change listener on combo box object which updates suggestions.
     */
    private void setupItemChangeListener() {
        comboBox.getItems().addListener((ListChangeListener<T>) c -> {
                    clearSuggestions();
                    addPossibleSuggestions(comboBox.getItems());
                }
        );
    }

    @Override
    protected Comparator<T> getComparator() {
        return (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(
                mapFunction.apply(o1), mapFunction.apply(o2)
        );
    }

    @Override
    protected boolean isMatch(final T suggestion, final AutoCompletionBinding.ISuggestionRequest request) {
        String filledText = request.getUserText();
        return !isItemAlreadySelected(suggestion) && containsTextIgnoreCase(suggestion, filledText);
    }

    /**
     * Check if suggestion contains specified text. Ignores case.
     *
     * @param suggestion (NotNull) Suggestion to check. It is converted to string by map function.
     * @param filledText (Nullable) Text filled by user.
     * @return True if text is part of suggested item. False otherwise.
     */
    private boolean containsTextIgnoreCase(final T suggestion, final String filledText) {
        Validate.notNull(suggestion);
        String userTextLower = Optional.ofNullable(filledText).orElse(StringUtils.EMPTY).toLowerCase();
        String suggestionStr = mapFunction.apply(suggestion).toLowerCase();
        return suggestionStr.contains(userTextLower);
    }

    /**
     * @param suggestion (Nullable) Item to check against selected item in combo box.
     * @return True if specified item is already selected in combo box. False otherwise.
     */
    private boolean isItemAlreadySelected(final T suggestion) {
        T selectedItem = comboBox.getSelectionModel().getSelectedItem();
        return Objects.equals(suggestion, selectedItem);
    }
}
