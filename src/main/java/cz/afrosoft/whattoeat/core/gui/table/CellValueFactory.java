package cz.afrosoft.whattoeat.core.gui.table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Constructs cell value factories for java fx table columns {@link TableColumn#setCellValueFactory(Callback)}.
 * This provides one place for getting cell value factories for whole application.
 *
 * @author Tomas Rejent
 */
public class CellValueFactory {

    /**
     * Construct cell value factory for String values. For empty values factory returns empty String.
     *
     * @param mapFunction (NotNull) Function which maps table row item to its cell String representation.
     * @param <S>         Type of item in table.
     * @return (NotNull) Cell value factory which constructs cell value from {@link ReadOnlyObjectWrapper} with value
     * provided by mapping function.
     */
    public static <S> Callback<TableColumn.CellDataFeatures<S, String>, ObservableValue<String>> newStringReadOnlyWrapper(final Function<S, String> mapFunction) {
        Validate.notNull(mapFunction, "Map function cannot be null.");
        return newReadOnlyWrapper(mapFunction, StringUtils.EMPTY);
    }

    /**
     * Construct cell value factory for custom type. Use null for empty value. See {@link #newReadOnlyWrapper(Function, Object)}.
     *
     * @param mapFunction (NotNull) Function which maps table row item to its cell representation.
     * @param <S>         Type of item in table.
     * @param <T>         Type of cell value.
     * @return (NotNull) Cell value factory which constructs cell value from {@link ReadOnlyObjectWrapper} with value
     * provided by mapping function. In case cell or row value is null, provided <code>emptyValue</code> is used instead of result of mapping function.
     */
    public static <S, T> Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> newReadOnlyWrapper(final Function<S, T> mapFunction) {
        Validate.notNull(mapFunction, "Map function cannot be null.");
        return newReadOnlyWrapper(mapFunction, null);
    }

    /**
     * Construct cell value factory for custom type.
     *
     * @param mapFunction (NotNull) Function which maps table row item to its cell representation.
     * @param emptyValue  (Nullable) Value which is used for construction of {@link ReadOnlyObjectWrapper} if cell or row value is null.
     * @param <S>         Type of item in table.
     * @param <T>         Type of cell value.
     * @return (NotNull) Cell value factory which constructs cell value from {@link ReadOnlyObjectWrapper} with value
     * provided by mapping function. In case cell or row value is null, provided <code>emptyValue</code> is used instead of result of mapping function.
     */
    public static <S, T> Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> newReadOnlyWrapper(final Function<S, T> mapFunction, final T emptyValue) {
        Validate.notNull(mapFunction, "Map function cannot be null.");

        return cell -> {
            T initialValue;
            if (cell == null || cell.getValue() == null) {
                initialValue = emptyValue;
            } else {
                initialValue = mapFunction.apply(cell.getValue());
            }
            return new ReadOnlyObjectWrapper<>(initialValue);
        };
    }

    private CellValueFactory() {
        throw new IllegalStateException("This class is not meant to be instanced.");
    }

}
