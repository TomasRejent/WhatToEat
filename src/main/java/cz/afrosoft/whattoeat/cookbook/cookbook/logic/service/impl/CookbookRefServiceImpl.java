package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.repository.CookbookRepository;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookRefService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link CookbookRefService} which uses {@link CookbookRefImpl} as {@link cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef}
 * implementation.
 */
@Service
class CookbookRefServiceImpl implements CookbookRefService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookRefServiceImpl.class);

    @Autowired
    private CookbookRepository repository;

    @Override
    public CookbookRef fromEntity(final CookbookEntity entity) {
        LOGGER.trace("Converting cookbook entity {} to CookbookRef.", entity);
        Validate.notNull(entity);
        Validate.notNull(entity.getId(), "Only persisted entities can be used as reference.");

        return new CookbookRefImpl(entity.getId(), entity.getName());
    }

    @Override
    public CookbookEntity toEntity(final CookbookRef reference) {
        LOGGER.trace("Converting reference {} to entity.", reference);
        Validate.notNull(reference);
        Validate.notNull(reference.getId(), "Reference must always contains id.");

        return repository.getOne(reference.getId());
    }
}
