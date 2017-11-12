package cz.afrosoft.whattoeat.cookbook.recipe.data.repository.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.data.repository.RecipeCustomRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository {

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
        filter.getName().ifPresent(name -> {
            queryPredicates.add(
                    builder.like(
                            builder.lower(queryRoot.get("name")),
                            "%" + name.toLowerCase() + "%"));
        });
        //filtering by cookbooks
        filter.getCookbooks().ifPresent(cookbookRefs -> {
            Join<RecipeEntity, CookbookEntity> join = queryRoot.join("cookbooks");
            //TODO
        });
        //filtering by type
        filter.getType().ifPresent(recipeTypes -> {
            List<Predicate> predicates = recipeTypes.stream().map(recipeType ->
                    builder.isMember(recipeType, queryRoot.get("recipeTypes"))
            ).collect(Collectors.toList());
            queryPredicates.add(
                    builder.or(predicates.toArray(new Predicate[predicates.size()]))
            );
        });


        query.where(queryPredicates.toArray(new Predicate[queryPredicates.size()]));
        TypedQuery<RecipeEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

}
