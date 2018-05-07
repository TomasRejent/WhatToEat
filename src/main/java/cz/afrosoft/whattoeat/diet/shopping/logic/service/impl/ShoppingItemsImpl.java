package cz.afrosoft.whattoeat.diet.shopping.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItem;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItems;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
class ShoppingItemsImpl implements ShoppingItems {

    private final List<ShoppingItem> items;

    private ShoppingItemsImpl(final List<ShoppingItem> items) {
        Validate.notNull(items);

        this.items = Collections.unmodifiableList(items);
    }

    @Override
    public List<ShoppingItem> getItemList() {
        return items;
    }

    static class Builder {

        private final Map<Ingredient, Float> shoppingItems = new HashMap<>();

        Builder addItem(final Ingredient item, final Float quantity) {
            Validate.notNull(item);
            Validate.notNull(quantity);

            shoppingItems.merge(item, quantity, Float::sum);

            return this;
        }

        ShoppingItems build() {
            return new ShoppingItemsImpl(shoppingItems.entrySet().stream()
                    .map(itemEntry -> new ShoppingItemImpl(itemEntry.getKey(), itemEntry.getValue()))
                    .collect(Collectors.toList()));
        }

    }
}
