package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link IngredientEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Integer>, IngredientRepositoryCustom {

    @EntityGraph(attributePaths = {"keywords", "unitConversion"}, type = EntityGraph.EntityGraphType.LOAD)
    List<IngredientEntity> findAll();

    boolean existsByName(String name);

    Ingredient findByName(String name);
}
