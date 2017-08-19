package cz.afrosoft.whattoeat.core.gui.list;

import cz.afrosoft.whattoeat.core.util.VarargsUtil;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Util class for creating cell factories for {@link ListView}.
 *
 * @author Tomas Rejent
 */
public final class ListCellFactory {

    /**
     * Creates new cell factory for list based on specified map function.
     *
     * @param mapFunction (NotNull) Function to map list items to String.
     * @param <T>         Type of item in list.
     * @return (NotNull) New cell factory.
     */
    public static <T> Callback<ListView<T>, ListCell<T>> newCellFactory(final Function<T, String> mapFunction) {
        return listView -> new CustomStringCell<>(mapFunction);
    }

    @SafeVarargs
    public static <T> Callback<ListView<T>, ListCell<T>> newRemovableCellFactory(final Function<T, String> mapFunction, final Consumer<T>... removeListeners) {
        return listView -> new RemovableStringCell<>(mapFunction, VarargsUtil.toList(removeListeners));
    }

    private ListCellFactory() {
        throw new IllegalStateException("This class cannot be instanced.");
    }
}
