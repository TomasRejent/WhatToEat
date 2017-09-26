package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link CookbookRef}. Also works for {@link Cookbook}.
 * Use name for comparison.
 *
 * @author Tomas Rejent
 */
enum CookbookComparator implements Comparator<CookbookRef> {
    INSTANCE;

    @Override
    public int compare(final CookbookRef cookbook, final CookbookRef otherCookbook) {
        if (cookbook == otherCookbook) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(cookbook).map(CookbookRef::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherCookbook).map(CookbookRef::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
