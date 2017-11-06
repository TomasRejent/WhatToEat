package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link AuthorRef} and also for {@link Author}.
 * Comparison is done by name.
 *
 * @author Tomas Rejent
 */
enum AuthorComparator implements Comparator<AuthorRef> {
    INSTANCE;

    @Override
    public int compare(final AuthorRef author, final AuthorRef otherAuthor) {
        if (author == otherAuthor) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(author).map(AuthorRef::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherAuthor).map(AuthorRef::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
