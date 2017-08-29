package cz.afrosoft.whattoeat.core.gui.table;

import javafx.scene.control.TableCell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.function.Function;

/**
 * Implementation of table cell which formats collection of items to String displayed in cell. Each item is mapped
 * to String by mapping function supplied in constructor. Item Strings are then joined by separator {@link #ITEM_SEPARATOR}.
 * If collection is empty then empty String is rendered.
 *
 * @param <S> Type of items displayed in table.
 * @param <T> Type of item in collection to be rendered.
 * @author Tomas Rejent
 */
public class CollectionCell<S, T> extends TableCell<S, Collection<T>> {

    /**
     * Separates String representations of rendered items.
     */
    private static final String ITEM_SEPARATOR = ", ";

    /**
     * Construct new collection cell.
     *
     * @param mapFunction (NotNull) Function which maps items from collection to their human readable String
     *                    representation. This is typically name of item.
     */
    public static <S, T> CollectionCell<S, T> newInstance(final Function<T, String> mapFunction) {
        return new CollectionCell<>(mapFunction);
    }

    private final Function<T, String> mapFunction;

    private CollectionCell(final Function<T, String> mapFunction) {
        Validate.notNull(mapFunction, "Map function cannot be null.");
        this.mapFunction = mapFunction;
    }

    @Override
    protected void updateItem(final Collection<T> item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null || item.isEmpty()) {
            setText(StringUtils.EMPTY);
        } else {
            setText(StringUtils.join(item.stream().map(mapFunction).toArray(), ITEM_SEPARATOR));
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("mapFunction", mapFunction)
                .build();
    }
}
