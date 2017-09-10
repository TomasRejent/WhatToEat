package cz.afrosoft.whattoeat.core.gui.combobox;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Objects;
import java.util.function.Function;

/**
 * Utils for {@link ComboBox}. Provides methods for easy initializing of combo boxes for {@link Labeled} items and
 * for creating general string converters.
 */
public final class ComboBoxUtils {

    /**
     * Initializes combo box so it displays message defined by key of {@link Labeled} items instead of default toString
     * result. Also clears any existing values and sets new values.
     *
     * @param comboBox (NotNull) Combo box to init.
     * @param values   (NotNull) Values to set to combo box.
     * @param <T>      Type of combo box item implementing {@link Labeled}.
     */
    public static <T extends Labeled> void initLabeledComboBox(final ComboBox<T> comboBox, T... values) {
        Validate.notNull(comboBox);
        Validate.notNull(values);

        comboBox.getItems().clear();
        comboBox.getItems().addAll(values);
        initLabeledComboBox(comboBox);
    }

    /**
     * Initializes combo box so it displays message defined by key of {@link Labeled} items instead of default toString
     * result.
     *
     * @param comboBox (NotNull) Combo box to init.
     * @param <T>      Type of combo box item implementing {@link Labeled}.
     */
    public static <T extends Labeled> void initLabeledComboBox(final ComboBox<T> comboBox) {
        Validate.notNull(comboBox);

        comboBox.setConverter(createStringConverter(comboBox, labeled -> I18n.getText(labeled.getLabelKey())));
    }

    /**
     * Creates string converter for specified combo box which uses supplied map function to do conversion.
     *
     * @param comboBox    (NotNull) Combo box for which converter is created.
     * @param mapFunction (NotNull) Function which maps item from combo box to String.
     * @param <T>         Type of items in combo box.
     * @return (NotNull) New converter based on map function.
     */
    public static <T> StringConverter<T> createStringConverter(final ComboBox<T> comboBox, final Function<T, String> mapFunction) {
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

    /**
     * Creates string converter for specified combo box which uses different function for mapping item to string and string to item. This is useful when combo box supports creating of new values.
     *
     * @param toStringFnc   (NotNull) Function which maps item from combo box to String.
     * @param fromStringFnc (NotNull) Function which maps string to item.
     * @param <T>           Type of items in combo box.
     * @return (NotNull) New converter based on map function.
     */
    public static <T> StringConverter<T> createAsymmetricStringConverter(final Function<T, String> toStringFnc, final Function<String, T> fromStringFnc) {
        Validate.notNull(toStringFnc);
        Validate.notNull(fromStringFnc);

        return new StringConverter<T>() {
            @Override
            public String toString(final T object) {
                if (object == null) {
                    return StringUtils.EMPTY;
                } else {
                    return toStringFnc.apply(object);
                }
            }

            @Override
            public T fromString(final String string) {
                if (StringUtils.isEmpty(string)) {
                    return null;
                } else {
                    return fromStringFnc.apply(string);
                }
            }
        };
    }

    private ComboBoxUtils() {
        throw new IllegalStateException("This class cannot be instanced.");
    }
}
