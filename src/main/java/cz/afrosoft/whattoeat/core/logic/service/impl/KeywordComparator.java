package cz.afrosoft.whattoeat.core.logic.service.impl;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link Keyword}.
 *
 * @author Tomas Rejent
 */
enum KeywordComparator implements Comparator<Keyword> {
    INSTANCE;

    @Override
    public int compare(final Keyword keyword, final Keyword otherKeyword) {
        if (keyword == otherKeyword) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(keyword).map(Keyword::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherKeyword).map(Keyword::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
