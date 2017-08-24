package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.UnitConversionEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.IngredientRepository;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientUpdateObject;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Implementation of {@link IngredientService} which uses {@link IngredientImpl} as implementation of
 * {@link Ingredient} and provides its own implementation of {@link IngredientUpdateObject}.
 *
 * @author Tomas Rejent
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientServiceImpl.class);

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private IngredientRepository repository;

    @Override
    public Set<Ingredient> getAllIngredients() {
        LOGGER.debug("Getting all ingredients.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToIngredient);
    }

    @Override
    @Transactional
    public void delete(final Ingredient ingredient) {
        LOGGER.debug("Deleting ingredient: {}", ingredient);
        Validate.notNull(ingredient);
        repository.delete(ingredient.getId());
    }

    @Override
    public IngredientUpdateObject getCreateObject() {
        LOGGER.debug("Creating update object for new ingredient.");
        return new IngredientImpl.Builder();
    }

    @Override
    public IngredientUpdateObject getUpdateObject(final Ingredient ingredient) {
        LOGGER.debug("Getting update object for ingredient: {}", ingredient);
        Validate.notNull(ingredient);

        return new IngredientImpl.Builder()
                .setId(ingredient.getId())
                .setName(ingredient.getName())
                .setIngredientUnit(ingredient.getIngredientUnit())
                .setPrice(ingredient.getPrice())
                .setUnitConversion(ingredient.getUnitConversion())
                .setKeywords(ingredient.getKeywords());
    }

    @Override
    @Transactional
    public Ingredient createOrUpdate(final IngredientUpdateObject ingredientChanges) {
        LOGGER.debug("Updating ingredient: {}", ingredientChanges);
        Validate.notNull(ingredientChanges, "Cannot createOrUpdate ingredient with null changes.");

        IngredientEntity entity = new IngredientEntity();
        entity.setId(ingredientChanges.getId())
                .setName(ingredientChanges.getName())
                .setIngredientUnit(ingredientChanges.getIngredientUnit())
                .setPrice(ingredientChanges.getPrice())
                .setUnitConversion(unitConversionToEntity(ingredientChanges.getUnitConversion()))
                .setKeywords(ConverterUtil.convertToSet(ingredientChanges.getKeywords(), keywordService::keywordToEntity));
        return entityToIngredient(repository.save(entity));
    }

    private Ingredient entityToIngredient(final IngredientEntity entity) {
        Validate.notNull(entity);

        return new IngredientImpl.Builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setIngredientUnit(entity.getIngredientUnit())
                .setPrice(entity.getPrice())
                .setUnitConversion(entityToUnitConversion(entity.getUnitConversion()))
                .setKeywords(ConverterUtil.convertToSortedSet(entity.getKeywords(), keywordService::entityToKeyword))
                .build();
    }

    private UnitConversion entityToUnitConversion(UnitConversionEntity entity) {
        if (entity == null) {
            return null;
        } else {
            return new UnitConversionImpl.Builder()
                    .setId(entity.getId())
                    .setGramsPerPiece(entity.getGramsPerPiece())
                    .setMilliliterPerGram(entity.getMilliliterPerGram())
                    .setGramsPerPinch(entity.getGramsPerPinch())
                    .setGramsPerCoffeeSpoon(entity.getGramsPerCoffeeSpoon())
                    .setGramsPerSpoon(entity.getGramsPerCoffeeSpoon())
                    .build();
        }
    }

    private UnitConversionEntity unitConversionToEntity(final UnitConversion unitConversion) {
        if (unitConversion == null) {
            return null;
        } else {
            UnitConversionEntity entity = new UnitConversionEntity();
            return entity.setId(unitConversion.getId());
        }
    }
}
