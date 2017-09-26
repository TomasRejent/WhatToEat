package cz.afrosoft.whattoeat.cookbook.recipe.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeIngredientEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeIngredientRepository;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientRefService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link RecipeIngredientRefService} which uses {@link RecipeIngredientRefImpl} as {@link RecipeIngredientRef}
 * implementation.
 */

@Service
public class RecipeIngredientRefServiceImpl implements RecipeIngredientRefService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeIngredientRefServiceImpl.class);

    @Autowired
    private RecipeIngredientRepository repository;

    @Override
    public RecipeIngredientRef fromEntity(final RecipeIngredientEntity entity) {
        LOGGER.trace("Converting recipe ingredient entity {} to RecipeIngredientRef.", entity);
        Validate.notNull(entity);
        Validate.notNull(entity.getId(), "Only persisted entities can be used as reference.");

        return new RecipeIngredientRefImpl(entity.getId());
    }

    @Override
    public RecipeIngredientEntity toEntity(final RecipeIngredientRef reference) {
        LOGGER.trace("Converting reference {} to entity.", reference);
        Validate.notNull(reference);
        Validate.notNull(reference.getId(), "Reference must always contains id.");

        return repository.getOne(reference.getId());
    }
}
