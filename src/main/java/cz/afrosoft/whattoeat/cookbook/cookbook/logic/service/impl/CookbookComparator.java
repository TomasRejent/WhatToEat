package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link Cookbook}.
 *
 * @author Tomas Rejent
 */
enum CookbookComparator implements Comparator<Cookbook> {
    INSTANCE;

    @Override
    public int compare(final Cookbook cookbook, final Cookbook otherCookbook) {
        if (cookbook == otherCookbook) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(cookbook).map(Cookbook::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherCookbook).map(Cookbook::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
