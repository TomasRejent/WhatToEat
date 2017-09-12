package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link RecipeComparator}.
 *
 * @author Tomas Rejent
 */
enum RecipeComparator implements Comparator<Recipe> {
    INSTANCE;


    @Override
    public int compare(final Recipe recipe, final Recipe otherRecipe) {
        if (recipe == otherRecipe) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(recipe).map(Recipe::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherRecipe).map(Recipe::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
