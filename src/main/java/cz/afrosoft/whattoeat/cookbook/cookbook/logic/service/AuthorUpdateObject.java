package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import java.util.Optional;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;

/**
 * Update object for {@link Author} Serves for its creation or editing. This allows {@link Author} to be immutable.
 *
 * @author Tomas Rejent
 */
public interface AuthorUpdateObject {

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity or optional filled with id of entity which is edited.
     */
    Optional<Integer> getId();

    /**
     * @return (NotNull) Name of author. Is empty optional if update object is for creating new entity and value was not yet set by {@link #setName(String)}. Else is
     * optional filled with name set by {@link #setName(String)}. If name was not set and this object is for update of existing author then it contains original name of
     * author.
     */
    Optional<String> getName();

    /**
     * @return (NotNull) Email of author. Is empty optional if update object is for creating new entity and value was not yet set by {@link #setEmail(String)}. Else is
     * optional filled with email set by {@link #setEmail(String)}. If email was not set and this object is for update of existing author then it contains original email
     * of author.
     */
    Optional<String> getEmail();

    /**
     * @return (NotNull) Description of author. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setDescription(String)}. Else is optional filled with description set by {@link #setDescription(String)}. If description was not set and this object is for update
     * of existing author then it contains original description of author.
     */
    Optional<String> getDescription();

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
