package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;

import java.util.Set;

/**
 * Enables to specify and transfer info about createOrUpdate of cookbook.
 * This enables {@link Cookbook} to exist without setter methods, so its implementation
 * can be immutable. Provides fluent setters for all editable parameters.
 *
 * @author Tomas Rejent
 */
public interface CookbookUpdateObject extends Cookbook {

    /**
     * Changes name of cookbook. If called multiple times only value from last call is used.
     *
     * @param name (NotBlank) Name of cookbook to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    CookbookUpdateObject setName(String name);

    /**
     * Changes description of cookbook. If called multiple times only value from last call is used.
     *
     * @param description (NotNull)
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    CookbookUpdateObject setDescription(String description);

    /**
     * Changes authors of cookbook.
     *
     * @param authors (NotEmpty) Authors to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    CookbookUpdateObject setAuthors(Set<Author> authors);
}
