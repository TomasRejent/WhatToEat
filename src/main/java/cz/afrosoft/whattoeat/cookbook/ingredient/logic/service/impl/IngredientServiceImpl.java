package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientFilter;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.UnitConversionEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.IngredientRepository;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.*;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    private RecipeRefService recipeRefService;
    @Autowired
    private IngredientRefService ingredientRefService;
    @Autowired
    private ShopService shopService;

    @Autowired
    private NutritionFactsService nutritionFactsService;

    @Autowired
    private IngredientRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Ingredient getById(final Integer id) {
        Validate.notNull(id);
        return entityToIngredient(repository.getOne(id));
    }

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
        return new IngredientImpl.Builder().setNutritionFacts(nutritionFactsService.getCreateObject());
    }

    @Override
    public IngredientUpdateObject getUpdateObject(final Ingredient ingredient) {
        LOGGER.debug("Getting update object for ingredient: {}", ingredient);
        Validate.notNull(ingredient);

        final IngredientImpl.Builder builder = new IngredientImpl.Builder(ingredient.getId())
                .setName(ingredient.getName())
                .setIngredientUnit(ingredient.getIngredientUnit())
                .setPrice(ingredient.getPrice())
                .setKeywords(ingredient.getKeywords())
                .setGeneral(ingredient.isGeneral())
                .setPurchasable(ingredient.isPurchasable())
                .setEdible(ingredient.isEdible())
                .setManufacturer(ingredient.getManufacturer())
                .setShops(ingredient.getShops());
        ingredient.getUnitConversion().ifPresent(unitConversion -> builder.setUnitConversion(toUpdateObject(unitConversion)));
        ingredient.getParent().ifPresent(builder::setParent);
        ingredient.getRecipe().ifPresent(builder::setRecipe);
        Optional<NutritionFacts> nutritionFacts = ingredient.getNutritionFacts();
        if(nutritionFacts.isPresent()){
            builder.setNutritionFacts(nutritionFactsService.getUpdateObject(nutritionFacts.get()));
        } else {
            builder.setNutritionFacts(nutritionFactsService.getCreateObject());
        }
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
                .setNutritionFacts(nutritionFactsService.toEntity(ingredientChanges.getNutritionFacts()))
                .setKeywords(ConverterUtil.convertToSet(ingredientChanges.getKeywords(), keywordService::keywordToEntity))
                .setGeneral(ingredientChanges.isGeneral())
                .setPurchasable(ingredientChanges.isPurchasable())
                .setEdible(ingredientChanges.isEdible())
                .setManufacturer(ingredientChanges.getManufacturer())
                .setRecipe(ingredientChanges.getRecipe().isPresent() ? recipeRefService.toEntity(ingredientChanges.getRecipe().get()) : null)
                .setShops(ConverterUtil.convertToSet(ingredientChanges.getShops(), shopService::shopToEntity));

        if(ingredientChanges.getParent().isPresent()){
            entity.setParent(ingredientRefService.toEntity(ingredientChanges.getParent().get()));
        } else {
            entity.setParent(null);
        }

        return entityToIngredient(repository.save(entity));
    }

    @Override
    public Ingredient entityToIngredient(final IngredientEntity entity) {
        Validate.notNull(entity);

        final IngredientImpl.Builder builder = new IngredientImpl.Builder(entity.getId())
                .setName(entity.getName())
                .setIngredientUnit(entity.getIngredientUnit())
                .setPrice(entity.getPrice())
                .setKeywords(ConverterUtil.convertToSortedSet(entity.getKeywords(), keywordService::entityToKeyword))
                .setGeneral(entity.isGeneral())
                .setPurchasable(entity.isPurchasable())
                .setEdible(entity.isEdible())
                .setManufacturer(entity.getManufacturer())
                .setRecipe(Optional.ofNullable(entity.getRecipe()).map(recipeRefService::fromEntity).orElse(null))
                .setShops(ConverterUtil.convertToSet(entity.getShops(), shopService::entityToShop))
                .setParent(Optional.ofNullable(entity.getParent()).map(ingredientRefService::fromEntity).orElse(null))
                .setChildren(ConverterUtil.convertToSet(entity.getChildren(), ingredientRefService::fromEntity));
        entityToUnitConversion(entity.getUnitConversion()).ifPresent(builder::setExistingUnitConversion);
        nutritionFactsService.toNutritionFacts(entity.getNutritionFacts()).ifPresent(builder::setExistingNutritionFacts);
        return builder.build();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<IngredientRef> getAllChildren(final IngredientRef ingredient) {
        IngredientEntity rootIngredient = repository.getOne(ingredient.getId());
        Set<IngredientRef> allChildren = new HashSet<>();
        rootIngredient.getChildren().forEach(child -> {
            IngredientRef childRef = ingredientRefService.fromEntity(child);
            allChildren.add(childRef);
            allChildren.addAll(getAllChildren(childRef));
        });

        return allChildren;
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
