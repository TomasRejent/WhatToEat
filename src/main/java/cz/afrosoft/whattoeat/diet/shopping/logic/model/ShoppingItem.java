package cz.afrosoft.whattoeat.diet.shopping.logic.model;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface ShoppingItem {

    String getName();

    float getQuantity();

    IngredientUnit getUnit();

    Set<Keyword> getKeywords();
}
