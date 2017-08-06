package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;

/**
 * Implementation of {@link Author} used by {@link CookbookImpl} to represent related authors.
 * This implementation provides only id and name of author. Other values are always empty.
 *
 * @author Tomas Rejent
 */
final class AuthorWeakImpl implements Author {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorWeakImpl.class);

    private final Integer id;
    private final String name;

    public AuthorWeakImpl(final Integer id, final String name) {
        Validate.notNull(id);
        Validate.notNull(name);

        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        LOGGER.warn("Trying to access author data[email] which are not preloaded. Empty String is returned.");
        return StringUtils.EMPTY;
    }

    @Override
    public String getDescription() {
        LOGGER.warn("Trying to access author data[description] which are not preloaded. Empty String is returned.");
        return StringUtils.EMPTY;
    }

    @Override
    public Set<Cookbook> getCookbooks() {
        LOGGER.warn("Trying to access author related entities[cookbooks] which are not preloaded. Empty Set is returned.");
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
