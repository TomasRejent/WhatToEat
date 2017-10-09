package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeRepository;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;

/**
 * Implementation of {@link RecipeRefService} which uses {@link RecipeRefImpl} as {@link RecipeRef}.
 * implementation.
 */
@Service
public class RecipeRefServiceImpl implements RecipeRefService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeRefServiceImpl.class);

    @Autowired
    private RecipeRepository repository;

    @Override
    public RecipeRef fromEntity(final RecipeEntity entity) {
        LOGGER.trace("Converting recipe entity {} to RecipeRef.", entity);
        Validate.notNull(entity);
        Validate.notNull(entity.getId(), "Only persisted entities can be used as reference.");

        return new RecipeRefImpl(entity.getId(), entity.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeEntity toEntity(final RecipeRef reference) {
        LOGGER.trace("Converting reference {} to entity.", reference);
        Validate.notNull(reference);
        Validate.notNull(reference.getId(), "Reference must always contains id.");

        return repository.getOne(reference.getId());
    }
}
