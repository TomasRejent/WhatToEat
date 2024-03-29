package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;

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
     * @return (NotNull) Return all authors defined in application. Authors are loaded only as reference so they
     * contains only id and name. This is suitable to connecting other entities to authors.
     */
    Set<AuthorRef> getAllAuthorRefs();

    /**
     * Deletes specified author.
     *
     * @param author (NotNull) Author to be deleted.
     */
    void delete(Author author);

    /**
     * Gets update object for new Author. After data are filled it can be persisted using
     * {@link #createOrUpdate(AuthorUpdateObject)} method.
     *
     * @return (NotNull)
     */
    AuthorUpdateObject getCreateObject();

    /**
     * Gets update object for specified author. Update object is used to modify author.
     * Changes are not persisted until {@link #createOrUpdate(AuthorUpdateObject)} is called.
     *
     * @param author (NotNull) Author to modify.
     * @return (NotNull) Update object which enables you to specify changes to author.
     */
    AuthorUpdateObject getUpdateObject(Author author);

    /**
     * Applies changes specified by authorChanges to author for which authorChanges was constructed.
     * It can also be used to persist new authors.
     *
     * @param authorChanges (NotNull) Changes to persist.
     * @return (NotNull) Author with updated values.
     * @throws IllegalStateException When createOrUpdate failed and updated user cannot be reloaded.
     */
    Author createOrUpdate(AuthorUpdateObject authorChanges);
}
