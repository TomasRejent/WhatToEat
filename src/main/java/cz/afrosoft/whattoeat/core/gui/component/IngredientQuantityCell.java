package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityService;
import javafx.scene.control.TableCell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Cell for displaying quantity of ingredient. Use {@link IngredientQuantityService} for intelligent formatting which use
 * {@link cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion} if possible to provide suitable unit and conversion.
 * <p>
 * This cell does not support editing.
 *
 * @author Tomas Rejent
 */
public class IngredientQuantityCell extends TableCell<IngredientQuantity, Float> {

    private final IngredientQuantityService quantityService;

    public IngredientQuantityCell(final IngredientQuantityService quantityService) {
        Validate.notNull(quantityService);
        this.quantityService = quantityService;
    }

    @Override
    protected void updateItem(final Float quantity, final boolean empty) {
        super.updateItem(quantity, empty);

        if (quantity == null || empty) {
            setText(StringUtils.EMPTY);
        } else {
            IngredientQuantity ingredientQuantity = getTableView().getItems().get(getIndex());
            setText(quantityService.getFormattedQuantity(ingredientQuantity.getIngredient(), quantity));
        }
    }
}
