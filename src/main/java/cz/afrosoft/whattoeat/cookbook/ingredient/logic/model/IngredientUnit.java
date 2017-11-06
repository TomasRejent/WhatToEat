package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.core.data.IdEnum;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import org.apache.commons.lang3.Validate;

/**
 * Determines how quantity of ingredient is measured.
 *
 * @author Tomas Rejent
 */
public enum IngredientUnit implements IdEnum, Labeled {
    /**
     * Quantity expressed by number of units. For example three pieces of eggs.
     */
    PIECE(1, "cz.afrosoft.whattoeat.ingredientUnit.piece"),
    /**
     * Quantity expressed by weight. Unit is gram. For example 500g of flour.
     */
    WEIGHT(2, "cz.afrosoft.whattoeat.ingredientUnit.weight"),
    /**
     * Quantity expressed by volume. Unit is milliliter. For example 350ml of milk.
     */
    VOLUME(3, "cz.afrosoft.whattoeat.ingredientUnit.volume");

    private final int id;
    private final String labelKey;

    IngredientUnit(final int id, final String labelKey) {
        Validate.notBlank(labelKey);
        this.id = id;
        this.labelKey = labelKey;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
