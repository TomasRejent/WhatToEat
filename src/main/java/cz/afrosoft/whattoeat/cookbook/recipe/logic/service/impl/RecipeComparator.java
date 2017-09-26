package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link RecipeRef} and also {@link Recipe}.
 * Comparison is based on name.
 *
 * @author Tomas Rejent
 */
enum RecipeComparator implements Comparator<RecipeRef> {
    INSTANCE;


    @Override
    public int compare(final RecipeRef recipe, final RecipeRef otherRecipe) {
        if (recipe == otherRecipe) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(recipe).map(RecipeRef::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherRecipe).map(RecipeRef::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
