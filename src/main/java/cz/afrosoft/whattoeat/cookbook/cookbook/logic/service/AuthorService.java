package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;

import java.util.Set;

/**
 * Service providing methods for operating on {@link cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author}.
 *
 * @author Tomas Rejent
 */
public interface AuthorService {

    /**
     * @return (NotNull) Return all authors defined in application.
     */
    Set<Author> getAllAuthors();

    /**
     * Deletes specified author.
     *
     * @param author (NotNull) Author to be deleted.
     */
    void delete(Author author);

    /**
     * Gets update object for new Author. After data are filled it can be persisted using
     * {@link #update(AuthorUpdateObject)} method.
     *
     * @return (NotNull)
     */
    AuthorUpdateObject getCreateObject();

    /**
     * Gets update object for specified author. Update object is used to modify author.
     * Changes are not persisted until {@link #update(AuthorUpdateObject)} is called.
     *
     * @param author (NotNull) Author to modify.
     * @return (NotNull) Update object which enables you to specify changes to author.
     */
    AuthorUpdateObject getUpdateObject(Author author);

    /**
     * Applies changes specified by authorChanges to author for which authorChanges was constructed.
     *
     * @param authorChanges (NotNull) Changes to persist.
     * @return (NotNull) Author with updated values.
     * @throws IllegalStateException When update failed and updated user cannot be reloaded.
     */
    Author update(AuthorUpdateObject authorChanges);

}
