package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Shop;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
enum ShopComparator implements Comparator<Shop> {
    INSTANCE;

    @Override
    public int compare(final Shop shop, final Shop otherShop) {
        if(shop == otherShop){
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(shop).map(Shop::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherShop).map(Shop::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
