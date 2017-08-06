package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;

/**
 * Implementation of {@link Cookbook} used by {@link AuthorImpl} to represent related cookbooks.
 * This implementation provides only id and name of cookbook. Other values are always empty.
 *
 * @author Tomas Rejent
 */
final class CookbookWeakImpl implements Cookbook {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookWeakImpl.class);

    private final Integer id;
    private final String name;

    CookbookWeakImpl(final Integer id, final String name) {
        Validate.notNull(id);
        Validate.notBlank(name);

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
    public String getDescription() {
        LOGGER.warn("Trying to access cookbook data[description] which are not preloaded. Empty String is returned.");
        return StringUtils.EMPTY;
    }

    @Override
    public Set<Recipe> getRecipes() {
        LOGGER.warn("Trying to access cookbook related entities[recipes] which are not preloaded. Empty Set is returned.");
        return Collections.emptySet();
    }

    @Override
    public Set<Author> getAuthors() {
        LOGGER.warn("Trying to access cookbook related entities[authors] which are not preloaded. Empty Set is returned.");
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
