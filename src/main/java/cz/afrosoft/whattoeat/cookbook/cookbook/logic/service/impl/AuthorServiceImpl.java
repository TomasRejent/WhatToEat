package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.repository.AuthorRepository;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorUpdateObject;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link AuthorService} which uses {@link AuthorEntity} as both implementation of
 * {@link Author} and {@link AuthorUpdateObject}.
 *
 * @author Tomas Rejent
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    private AuthorRepository repository;

    @Override
    public Set<Author> getAllAuthors() {
        LOGGER.debug("Getting all authors.");
        return new HashSet<>(repository.findAllWithCookbooks());
    }

    @Override
    @Transactional
    public void delete(final Author author) {
        LOGGER.debug("Deleting author: {}", author);
        Validate.notNull(author, "Cannot delete null author.");
        repository.delete(author.getId());
    }

    @Override
    public AuthorUpdateObject getCreateObject() {
        LOGGER.debug("Creating update object for new author.");
        return new AuthorEntity();
    }

    @Override
    public AuthorUpdateObject getUpdateObject(final Author author) {
        LOGGER.debug("Getting update object for author: {}", author);
        Validate.notNull(author, "Cannot get update object for null author.");

        if (author instanceof AuthorEntity) {
            return (AuthorEntity) author;
        } else {
            return repository.getOne(author.getId());
        }
    }

    @Override
    @Transactional
    public Author update(final AuthorUpdateObject authorChanges) {
        LOGGER.debug("Updating author: {}", authorChanges);
        Validate.notNull(authorChanges, "Cannot update author with null changes.");

        if (authorChanges instanceof AuthorEntity) {
            AuthorEntity updatedAuthor = repository.save((AuthorEntity) authorChanges);
            AuthorEntity oneWithCookbooks = repository.findOneWithCookbooks(updatedAuthor.getId());
            if (oneWithCookbooks == null) {
                throw new IllegalStateException(String.format("Author with id %s was not found.", updatedAuthor.getId()));
            }
            return oneWithCookbooks;
        } else {
            throw new IllegalStateException("This implementation of AuthorService supports only AuthorEntity as AuthorUpdateObject.");
        }
    }
}
