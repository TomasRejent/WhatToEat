package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link Ingredient}.
 *
 * @author Tomas Rejent
 */
enum IngredientComparator implements Comparator<Ingredient> {
    INSTANCE;

    @Override
    public int compare(final Ingredient ingredient, final Ingredient otherIngredient) {
        if (ingredient == otherIngredient) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(ingredient).map(Ingredient::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherIngredient).map(Ingredient::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
