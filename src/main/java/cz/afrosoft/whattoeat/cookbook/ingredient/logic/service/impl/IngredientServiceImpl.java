package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientFilter;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.UnitConversionEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.IngredientRepository;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientUpdateObject;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.UnitConversionUpdateObject;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link IngredientService} which uses {@link IngredientImpl} as implementation of {@link Ingredient} and provides its own implementation of {@link
 * IngredientUpdateObject}.
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
    @Transactional(readOnly = true)
    public Set<Ingredient> getAllIngredients() {
        LOGGER.debug("Getting all ingredients.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToIngredient);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Ingredient> getFilteredIngredients(final IngredientFilter filter) {
        Validate.notNull(filter);
        return ConverterUtil.convertToSortedSet(repository.findIngredientsByFilter(filter), this::entityToIngredient);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByName(final String ingredientName) {
        Validate.notEmpty(ingredientName);
        return repository.existsByName(ingredientName);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ingredient> findByName(final String ingredientName) {
        Validate.notEmpty(ingredientName);
        return Optional.ofNullable(repository.findByName(ingredientName));
    }

    @Override
    @Transactional
    public void delete(final Ingredient ingredient) {
        LOGGER.debug("Deleting ingredient: {}", ingredient);
        Validate.notNull(ingredient);
        repository.deleteById(ingredient.getId());
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

        final IngredientImpl.Builder builder = new IngredientImpl.Builder(ingredient.getId())
                .setName(ingredient.getName())
                .setIngredientUnit(ingredient.getIngredientUnit())
                .setPrice(ingredient.getPrice())
                .setKeywords(ingredient.getKeywords());
        ingredient.getUnitConversion().ifPresent(unitConversion -> builder.setUnitConversion(toUpdateObject(unitConversion)));
        return builder;
    }

    @Override
    @Transactional
    public Ingredient createOrUpdate(final IngredientUpdateObject ingredientChanges) {
        LOGGER.debug("Updating ingredient: {}", ingredientChanges);
        Validate.notNull(ingredientChanges, "Cannot createOrUpdate ingredient with null changes.");

        IngredientEntity entity = new IngredientEntity();
        entity.setId(ingredientChanges.getId().orElse(null))
                .setName(ingredientChanges.getName().get())
                .setIngredientUnit(ingredientChanges.getIngredientUnit().get())
                .setPrice(ingredientChanges.getPrice().get())
                .setUnitConversion(unitConversionToEntity(ingredientChanges.getUnitConversion().orElse(null)))
                .setKeywords(ConverterUtil.convertToSet(ingredientChanges.getKeywords(), keywordService::keywordToEntity));
        return entityToIngredient(repository.save(entity));
    }

    @Override
    public Ingredient entityToIngredient(final IngredientEntity entity) {
        Validate.notNull(entity);

        final IngredientImpl.Builder builder = new IngredientImpl.Builder(entity.getId())
                .setName(entity.getName())
                .setIngredientUnit(entity.getIngredientUnit())
                .setPrice(entity.getPrice())
                .setKeywords(ConverterUtil.convertToSortedSet(entity.getKeywords(), keywordService::entityToKeyword));
        entityToUnitConversion(entity.getUnitConversion()).ifPresent(builder::setExistingUnitConversion);
        return builder.build();
    }

    private Optional<UnitConversion> entityToUnitConversion(final UnitConversionEntity entity) {
        if (entity == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(new UnitConversionImpl.Builder(entity.getId())
                    .setGramsPerPiece(entity.getGramsPerPiece())
                    .setMilliliterPerGram(entity.getMilliliterPerGram())
                    .setGramsPerPinch(entity.getGramsPerPinch())
                    .setGramsPerCoffeeSpoon(entity.getGramsPerCoffeeSpoon())
                    .setGramsPerSpoon(entity.getGramsPerSpoon())
                    .build());
        }
    }

    /**
     * Converts unit conversion to entity. Creates new entity and sets all values from unit conversion to it. Entity is not saved by this method.
     *
     * @param unitConversion (Nullable)
     * @return (Nullable)
     */
    private UnitConversionEntity unitConversionToEntity(final UnitConversionUpdateObject unitConversion) {
        if (unitConversion == null) {
            return null;
        } else {
            UnitConversionEntity entity = new UnitConversionEntity();
            return entity
                    .setId(unitConversion.getId().orElse(null))
                    .setGramsPerPiece(unitConversion.getGramsPerPiece().orElse(null))
                    .setMilliliterPerGram(unitConversion.getMilliliterPerGram().orElse(null))
                    .setGramsPerPinch(unitConversion.getGramsPerPinch().orElse(null))
                    .setGramsPerCoffeeSpoon(unitConversion.getGramsPerCoffeeSpoon().orElse(null))
                    .setGramsPerSpoon(unitConversion.getGramsPerSpoon().orElse(null));
        }
    }

    private UnitConversionUpdateObject toUpdateObject(final UnitConversion unitConversion) {
        Validate.notNull(unitConversion);

        return new UnitConversionImpl.Builder(unitConversion.getId())
                .setGramsPerPiece(unitConversion.getGramsPerPiece())
                .setMilliliterPerGram(unitConversion.getMilliliterPerGram())
                .setGramsPerPinch(unitConversion.getGramsPerPinch())
                .setGramsPerCoffeeSpoon(unitConversion.getGramsPerCoffeeSpoon())
                .setGramsPerSpoon(unitConversion.getGramsPerSpoon());
    }
}
