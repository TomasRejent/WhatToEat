package cz.afrosoft.whattoeat.cookbook.recipe.data.repository;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link RecipeEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {

    @EntityGraph(attributePaths = {"keywords"}, type = EntityGraph.EntityGraphType.LOAD)
    List<RecipeEntity> findAll();

}
