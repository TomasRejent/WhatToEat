package cz.afrosoft.whattoeat.diet.shopping.logic.service;

import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItems;

import java.util.Collection;

/**
 * @author Tomas Rejent
 */
public interface ShoppingListService {

    ShoppingItems createShoppingItems(Collection<Meal> meals);

    String formatToSimpleText(ShoppingItems shoppingItems);
}
