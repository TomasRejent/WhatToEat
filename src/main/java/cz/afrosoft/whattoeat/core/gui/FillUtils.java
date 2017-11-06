package cz.afrosoft.whattoeat.core.gui;

import org.apache.commons.lang3.Validate;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;

import java.util.Collection;

/**
 * Utils for filling specific GUI components.
 * Created by Tomas Rejent on 21. 5. 2017.
 */
public class FillUtils {

    /**
     * Removes all previously checked items and check all items from specified collection in supplied CheckComboBox.
     * @param checkComboBox (Required) CheckComboBox to fill
     * @param items (Required) Items to check.
     * @param <T> Type of {@link CheckComboBox} items.
     */
    public static <T> void checkItems(CheckComboBox<T> checkComboBox, Collection<T> items){
        Validate.notNull(checkComboBox, "CheckComboBox to fill cannot be null.");
        Validate.notNull(items, "Item collection cannot be null.");

        IndexedCheckModel<T> checkModel = checkComboBox.getCheckModel();
        checkModel.clearChecks();
        for(T item : items){
            checkModel.check(item);
        }
    }

}
