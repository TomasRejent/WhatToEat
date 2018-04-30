package cz.afrosoft.whattoeat.core.gui.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;

import cz.afrosoft.whattoeat.core.gui.component.RemoveButton;
import cz.afrosoft.whattoeat.core.util.VarargsUtil;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Factory for creating cell factories for {@link ListView}.
 *
 * @author Tomas Rejent
 */
@Service
public final class ListCellFactory {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Creates new cell factory for list based on specified map function.
     *
     * @param mapFunction (NotNull) Function to map list items to String.
     * @param <T>         Type of item in list.
     * @return (NotNull) New cell factory.
     */
    public <T> Callback<ListView<T>, ListCell<T>> newCellFactory(final Function<T, String> mapFunction) {
        return listView -> new CustomStringCell<>(mapFunction);
    }

    public <T> Callback<ListView<T>, ListCell<T>> newRemovableCellFactory(final Function<T, String> mapFunction, final Consumer<T>... removeListeners) {
        return listView -> new RemovableStringCell<>(mapFunction, VarargsUtil.toList(removeListeners), applicationContext.getBean(RemoveButton.class));
    }

}
