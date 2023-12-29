package cz.afrosoft.whattoeat.cookbook.recipe.data.repository.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeRepositoryCustom;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of custom recipe repository which provides dynamically constructed query for filtering by recipe filter.
 */
@Repository
class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    private static final String NAME = "name";
    private static final String RECIPE_TYPE = "recipeTypes";
    private static final String COOKBOOKS = "cookbooks";
    private static final String COOKBOOKS_ID = "id";
    private static final String KEYWORDS = "keywords";
    private static final String KEYWORDS_ID = "id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RecipeEntity> findRecipesByFilter(final RecipeFilter filter) {
        Validate.notNull(filter);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RecipeEntity> query = builder.createQuery(RecipeEntity.class);
        Root<RecipeEntity> queryRoot = query.from(RecipeEntity.class);
        List<Predicate> queryPredicates = new LinkedList<>();

        //filtering by name
        filter.getName().ifPresent(name -> queryPredicates.add(filterByName(name, builder, queryRoot)));
        //filtering by type
        filter.getType().ifPresent(recipeTypes -> queryPredicates.add(filterByType(recipeTypes, builder, queryRoot)));
        //filtering by cookbooks
        filter.getCookbooks().ifPresent(cookbooks -> queryPredicates.add(filterByCookbooks(cookbooks, queryRoot)));
        //filtering by keywords
        filter.getKeywords().ifPresent(keywords -> queryPredicates.add(filterByKeywords(keywords, builder, queryRoot)));

        query.where(queryPredicates.toArray(new Predicate[queryPredicates.size()]));
        TypedQuery<RecipeEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    private Predicate filterByName(final String name, final CriteriaBuilder builder, final Root<RecipeEntity> queryRoot) {
        return builder.like(
                builder.lower(
                        queryRoot.get(NAME)),
                "%" + name.toLowerCase() + "%");
    }

    private Predicate filterByType(final Set<RecipeType> recipeTypes, final CriteriaBuilder builder, final Root<RecipeEntity> queryRoot) {
        List<Predicate> predicates = recipeTypes.stream().map(recipeType ->
                builder.isMember(recipeType, queryRoot.get(RECIPE_TYPE))
        ).collect(Collectors.toList());
        return builder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    private Predicate filterByCookbooks(final Set<CookbookRef> cookbooks, final Root<RecipeEntity> queryRoot) {
        Join<RecipeEntity, CookbookEntity> join = queryRoot.join(COOKBOOKS);
        return join.get(COOKBOOKS_ID).in(cookbooks.stream().map(CookbookRef::getId).collect(Collectors.toList()));
    }

    private Predicate filterByKeywords(final Set<Keyword> keywords, final CriteriaBuilder builder, final Root<RecipeEntity> queryRoot){
        return builder.and(
                keywords.stream().map(keyword ->
                        queryRoot.join(KEYWORDS).get(KEYWORDS_ID).in(keyword.getId())
                ).toArray(Predicate[]::new)
        );
    }
}
