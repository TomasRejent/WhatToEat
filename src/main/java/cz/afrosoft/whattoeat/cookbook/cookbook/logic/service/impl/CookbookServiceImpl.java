package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.repository.CookbookRepository;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookUpdateObject;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

/**
 * Implementation of {@link CookbookService} which uses {@link CookbookEntity} as implementation of
 * {@link Cookbook} and provides its own implementation of {@link CookbookUpdateObject}.
 *
 * @author Tomas Rejent
 */
@Service
public class CookbookServiceImpl implements CookbookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookServiceImpl.class);

    @Autowired
    private CookbookRepository repository;

    @Override
    public Set<Cookbook> getAllCookbooks() {
        LOGGER.debug("Getting all cookbooks.");
        return ConverterUtil.convertToSortedSet(repository.findAllWithAuthors(), this::entityToCookbook);
    }

    @Override
    @Transactional
    public void delete(final Cookbook cookbook) {
        LOGGER.debug("Deleting cookbook: {}", cookbook);
        Validate.notNull(cookbook, "Cannot delete null cookbook.");
        repository.delete(cookbook.getId());
    }

    @Override
    public CookbookUpdateObject getCreateObject() {
        LOGGER.debug("Creating update object for new cookbook.");
        return new CookbookImpl.Builder();
    }

    @Override
    public CookbookUpdateObject getUpdateObject(final Cookbook cookbook) {
        LOGGER.debug("Getting update object for cookbook: {}", cookbook);
        Validate.notNull(cookbook, "Cannot get update object for null cookbook.");

        return new CookbookImpl.Builder()
                .setId(cookbook.getId())
                .setName(cookbook.getName())
                .setDescription(cookbook.getDescription())
                .setAuthors(cookbook.getAuthors())
                .setRecipes(cookbook.getRecipes());
    }

    @Override
    @Transactional
    public Cookbook createOrUpdate(final CookbookUpdateObject cookbookChanges) {
        LOGGER.debug("Updating cookbook: {}", cookbookChanges);
        Validate.notNull(cookbookChanges, "Cannot createOrUpdate cookbook with null changes.");

        CookbookEntity entity = new CookbookEntity();
        entity.setId(cookbookChanges.getId())
                .setName(cookbookChanges.getName())
                .setDescription(cookbookChanges.getDescription())
                .setAuthors(ConverterUtil.convertToSet(cookbookChanges.getAuthors(), this::authorToEntity));
        return entityToCookbook(repository.save(entity));
    }

    /**
     * Converts {@link CookbookEntity} to {@link Cookbook} using {@link CookbookImpl}.
     *
     * @param entity (NotNull) Entity to convert.
     * @return (NotNull) New {@link Cookbook} with data from entity.
     */
    private Cookbook entityToCookbook(final CookbookEntity entity) {
        Validate.notNull(entity);
        return new CookbookImpl.Builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setAuthors(ConverterUtil.convertToSortedSet(entity.getAuthors(), this::entityToAuthor))
                .setRecipes(Collections.emptySet())
                .build();
    }

    /**
     * Converts {@link AuthorEntity} to {@link Author} using {@link AuthorWeakImpl}.
     *
     * @param entity (NotNull) Entity to convert.
     * @return (NotNull) New {@link Author} with subset of data from entity. Only id and name are filled.
     */
    private Author entityToAuthor(final AuthorEntity entity) {
        Validate.notNull(entity);
        return new AuthorWeakImpl(entity.getId(), entity.getName());
    }

    /**
     * Converts author to entity. This is only to set relation between cookbook and author. For this purpose id is filled.
     * Also name is filled because if it is not then cookbook after save has to be reloaded else it does not contain name and that
     * causes failure on front end. So name is also filled here so reloading is not necessary.
     *
     * @param author (NotNull) Author to convert to entity.
     * @return (NotNull) Partially filled author entity(id and name).
     */
    private AuthorEntity authorToEntity(final Author author) {
        Validate.notNull(author);
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.getId());
        entity.setName(author.getName());
        return entity;
    }
}
