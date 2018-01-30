package cz.afrosoft.whattoeat.cookbook.recipe.data.repository;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link RecipeIngredientEntity}.
 *
 * @author Tomas Rejent
 */
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Integer> {

    @Query("SELECT ri FROM RecipeIngredientEntity ri WHERE id IN :ids")
    List<RecipeIngredientEntity> findAllById(Set<Integer> ids);

}
