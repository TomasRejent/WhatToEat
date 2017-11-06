package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.data.repository.AuthorRepository;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorRefService;

/**
 * Implementation of {@link AuthorRefService} which uses {@link AuthorRefImpl} as {@link cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef}
 * implementation.
 */
@Service
class AuthorRefServiceImpl implements AuthorRefService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRefServiceImpl.class);

    @Autowired
    private AuthorRepository repository;

    @Override
    public AuthorRef fromEntity(final AuthorEntity entity) {
        LOGGER.trace("Converting author entity {} to AuthorRef.", entity);
        Validate.notNull(entity);
        Validate.notNull(entity.getId(), "Only persisted entities can be used as reference.");

        return new AuthorRefImpl(entity.getId(), entity.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorEntity toEntity(final AuthorRef reference) {
        LOGGER.trace("Converting reference {} to entity.", reference);
        Validate.notNull(reference);
        Validate.notNull(reference.getId(), "Reference must always contains id.");

        return repository.getOne(reference.getId());
    }
}
