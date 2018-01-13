package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientFilter;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.IngredientCustomRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tomas Rejent
 */
@Repository
class IngredientCustomRepositoryImpl implements IngredientCustomRepository {

    private static final String NAME = "name";

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
}
