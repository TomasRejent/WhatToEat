package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientFilter;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.IngredientRepositoryCustom;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
@Repository
class IngredientRepositoryImpl implements IngredientRepositoryCustom {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String MANUFACTURER = "manufacturer";
    private static final String GENERAL = "general";
    private static final String EDIBLE = "edible";
    private static final String PURCHASABLE = "purchasable";
    private static final String NUTRITION_FACTS = "nutritionFacts";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IngredientEntity> findIngredientsByFilter(final IngredientFilter filter) {
        Validate.notNull(filter);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<IngredientEntity> query = builder.createQuery(IngredientEntity.class);
        Root<IngredientEntity> queryRoot = query.from(IngredientEntity.class);
        List<Predicate> queryPredicates = new LinkedList<>();

        //filtering by name
        filter.getName().ifPresent(name -> queryPredicates.add(filterByName(name, builder, queryRoot)));
        //filtering by manufacturer
        filter.getManufacturer().ifPresent(name -> queryPredicates.add(filterByManufacturer(name, builder, queryRoot)));
        //filtering by flag if ingredient is general or particular
        filter.getGeneral().ifPresent(general -> queryPredicates.add(filterByGeneral(general, builder, queryRoot)));
        //filtering by exclusion of ids
        filter.getExcludedIds().ifPresent(excludedIds -> queryPredicates.add(filterOutExcludedIds(excludedIds, builder, queryRoot)));
        //filtering by flag if ingredient is edible as it is or if it requires preparation
        filter.getIsEdible().ifPresent(edible -> queryPredicates.add(filterByEdible(edible, builder, queryRoot)));
        //filtering by flag if ingredient is purchasable in shop or not
        filter.getIsPurchasable().ifPresent(purchasable -> queryPredicates.add(filterByPurchasable(purchasable, builder, queryRoot)));
        //filtering by flag if ingredient has specified nutrition facts. Nutrition fact may not be fully filled, only their existence is checked
        filter.getHasNutritionFacts().ifPresent(hasNutritionFacts -> queryPredicates.add(filterByHasNutritionFacts(hasNutritionFacts, builder, queryRoot)));

        query.where(queryPredicates.toArray(new Predicate[queryPredicates.size()]));
        TypedQuery<IngredientEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    private Predicate filterByName(final String name, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot) {
        return builder.like(
                builder.lower(
                        queryRoot.get(NAME)),
                "%" + name.toLowerCase() + "%");
    }

    private Predicate filterByManufacturer(final String manufacturer, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot) {
        return builder.like(
            builder.lower(
                queryRoot.get(MANUFACTURER)),
            "%" + manufacturer.toLowerCase() + "%");
    }

    private Predicate filterByGeneral(final Boolean general, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot){
        if(general){
            return builder.isTrue(queryRoot.get(GENERAL));
        } else {
            return builder.isFalse(queryRoot.get(GENERAL));
        }
    }

    private Predicate filterOutExcludedIds(final Set<Integer> excludedIds, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot){
        return queryRoot.get(ID).in(excludedIds).not();
    }

    private Predicate filterByEdible(final Boolean edible, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot){
        if(edible){
            return builder.isTrue(queryRoot.get(EDIBLE));
        } else {
            return builder.isFalse(queryRoot.get(EDIBLE));
        }
    }

    private Predicate filterByPurchasable(final Boolean purchasable, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot){
        if(purchasable){
            return builder.isTrue(queryRoot.get(PURCHASABLE));
        } else {
            return builder.isFalse(queryRoot.get(PURCHASABLE));
        }
    }

    private Predicate filterByHasNutritionFacts(final Boolean hasNutritionFacts, final CriteriaBuilder builder, final Root<IngredientEntity> queryRoot){
        Path<Object> nutritionFactsPath = queryRoot.get(NUTRITION_FACTS);
        return hasNutritionFacts ? nutritionFactsPath.isNotNull() : nutritionFactsPath.isNull();
    }
}
