package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.IngredientRepository;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientRefService;

/**
 * Implementation of {@link IngredientRefService} which uses {@link IngredientRefImpl} as {@link IngredientRef}.
 * implementation.
 *
 * @author Tomas Rejent
 */
@Service
public class IngredientRefServiceImpl implements IngredientRefService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientRefServiceImpl.class);

    @Autowired
    private IngredientRepository repository;

    @Override
    public IngredientRef fromEntity(final IngredientEntity entity) {
        LOGGER.trace("Converting ingredient entity {} to IngredientRef.", entity);
        Validate.notNull(entity);
        Validate.notNull(entity.getId(), "Only persisted entities can be used as reference.");

        return new IngredientRefImpl(entity.getId(), entity.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientEntity toEntity(final IngredientRef reference) {
        LOGGER.trace("Converting reference {} to entity.", reference);
        Validate.notNull(reference);
        Validate.notNull(reference.getId(), "Reference must always contains id.");

        return repository.getOne(reference.getId());
    }
}
