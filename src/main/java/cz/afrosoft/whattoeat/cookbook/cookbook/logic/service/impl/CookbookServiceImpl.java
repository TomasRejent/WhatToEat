package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.repository.CookbookRepository;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorRefService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookRefService;
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
 * Implementation of {@link CookbookService} which uses {@link CookbookImpl} as implementation of
 * {@link Cookbook} and provides its own implementation of {@link CookbookUpdateObject}.
 *
 * @author Tomas Rejent
 */
@Service
public class CookbookServiceImpl implements CookbookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookServiceImpl.class);

    @Autowired
    private CookbookRepository repository;

    @Autowired
    private CookbookRefService cookbookRefService;

    @Autowired
    private AuthorRefService authorRefService;

    @Override
    public Set<Cookbook> getAllCookbooks() {
        LOGGER.debug("Getting all cookbooks.");
        return ConverterUtil.convertToSortedSet(repository.findAllWithAuthors(), this::entityToCookbook);
    }

    @Override
    public Set<CookbookRef> getAllCookbookRefs() {
        LOGGER.debug("Getting all cookbooks refs.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), cookbookRefService::fromEntity);
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
                .setAuthors(ConverterUtil.convertToSet(cookbookChanges.getAuthors(), authorRefService::toEntity));
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
                .setAuthors(ConverterUtil.convertToSortedSet(entity.getAuthors(), authorRefService::fromEntity))
                .setRecipes(Collections.emptySet())
                .build();
    }
}
