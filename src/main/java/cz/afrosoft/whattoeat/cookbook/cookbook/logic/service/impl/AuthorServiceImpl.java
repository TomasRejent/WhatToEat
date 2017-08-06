package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.repository.AuthorRepository;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorUpdateObject;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Implementation of {@link AuthorService} which uses {@link AuthorImpl} as implementation of {@link Author}
 * and {@link AuthorImpl.Builder} as implementation of {@link AuthorUpdateObject}.
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
        return ConverterUtil.convertToSet(repository.findAllWithCookbooks(), this::entityToAuthor);
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
        return new AuthorImpl.Builder();
    }

    @Override
    public AuthorUpdateObject getUpdateObject(final Author author) {
        LOGGER.debug("Getting update object for author: {}", author);
        Validate.notNull(author, "Cannot get update object for null author.");

        return new AuthorImpl.Builder()
                .setId(author.getId())
                .setName(author.getName())
                .setEmail(author.getEmail())
                .setDescription(author.getDescription())
                .setCookbooks(author.getCookbooks());
    }

    @Override
    @Transactional
    public Author createOrUpdate(final AuthorUpdateObject authorChanges) {
        LOGGER.debug("Updating author: {}", authorChanges);
        Validate.notNull(authorChanges, "Cannot createOrUpdate author with null changes.");

        AuthorEntity entity = new AuthorEntity();
        entity.setId(authorChanges.getId())
                .setName(authorChanges.getName())
                .setEmail(authorChanges.getEmail())
                .setDescription(authorChanges.getDescription())
                .setCookbooks(ConverterUtil.convertToSet(authorChanges.getCookbooks(), this::cookbookToEntity));
        return entityToAuthor(repository.save(entity));
    }

    private Author entityToAuthor(final AuthorEntity entity) {
        Validate.notNull(entity);
        return new AuthorImpl.Builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setDescription(entity.getDescription())
                .setCookbooks(ConverterUtil.convertToSet(entity.getCookbooks(), this::entityToCookbook))
                .build();
    }

    private Cookbook entityToCookbook(final CookbookEntity entity) {
        Validate.notNull(entity);
        return new CookbookWeakImpl(entity.getId(), entity.getName());
    }

    private CookbookEntity cookbookToEntity(final Cookbook cookbook) {
        Validate.notNull(cookbook);
        CookbookEntity entity = new CookbookEntity();
        return entity.setId(cookbook.getId());
    }
}
