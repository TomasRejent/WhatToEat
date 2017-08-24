package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;

/**
 * Enables to specify and transfer info about createOrUpdate of author.
 * This enables {@link Author} to exist without setter methods, so its implementation
 * can be immutable. Provides fluent setters for all editable parameters.
 *
 * @author Tomas Rejent
 */
public interface AuthorUpdateObject extends Author {

    /**
     * Changes name of author. If called multiple times only value from last call is used.
     *
     * @param name (NotBlank) Name of author to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    AuthorUpdateObject setName(String name);

    /**
     * Changes email of author. If called multiple times only value from last call is used.
     *
     * @param email (NotNull) Email of author. Use empty String to clear email.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    AuthorUpdateObject setEmail(String email);

    /**
     * Changes description of author. If called multiple times only value from last call is used.
     *
     * @param description (NotNull) Description of author. Use empty String to clear description.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    AuthorUpdateObject setDescription(String description);

}
