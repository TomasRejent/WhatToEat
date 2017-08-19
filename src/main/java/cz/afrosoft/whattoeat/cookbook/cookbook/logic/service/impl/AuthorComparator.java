package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Comparator implementation for {@link Author}.
 *
 * @author Tomas Rejent
 */
enum AuthorComparator implements Comparator<Author> {
    INSTANCE;

    @Override
    public int compare(final Author author, final Author otherAuthor) {
        if (author == otherAuthor) {
            return 0;
        } else {
            return I18n.compareStringsIgnoreCase(
                    Optional.ofNullable(author).map(Author::getName).orElse(StringUtils.EMPTY),
                    Optional.ofNullable(otherAuthor).map(Author::getName).orElse(StringUtils.EMPTY)
            );
        }
    }
}
