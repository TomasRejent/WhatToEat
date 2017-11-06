package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;

/**
 * Update object for {@link Cookbook} Serves for its creation or editing. This allows {@link Cookbook} to be immutable.
 *
 * Recipes are added to cookbooks by editing recipe.
 *
 * @author Tomas Rejent
 */
public interface CookbookUpdateObject {

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity or optional filled with id of entity which is edited.
     */
    Optional<Integer> getId();

    /**
     * @return (NotNull) Name of cookbook. Is empty optional if update object is for creating new entity and value was not yet set by {@link #setName(String)}. Else is
     * optional filled with name set by {@link #setName(String)}. If name was not set and this object is for update of existing cookbook then it contains original name of
     * cookbook.
     */
    Optional<String> getName();

    /**
     * @return (NotNull) Description of cookbook. Is empty optional if update object is for creating new entity and value was not yet set by {@link
     * #setDescription(String)}. Else is optional filled with description set by {@link #setDescription(String)}. If description was not set and this object is for update
     * of existing cookbook then it contains original description of cookbook.
     */
    Optional<String> getDescription();

    /**
     * @return (NotNull) Authors of cookbook. Is empty set if update object is for creating new entity and value was not yet set by {@link #setAuthors(Set)}. Else is set
     * filled with authors set by {@link #setAuthors(Set)}. If authors were not set and this object is for update of existing cookbook then it contains original authors
     * of cookbook.
     */
    Set<AuthorRef> getAuthors();

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
    CookbookUpdateObject setAuthors(Set<AuthorRef> authors);
}
