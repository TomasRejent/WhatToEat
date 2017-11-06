package cz.afrosoft.whattoeat.cookbook.recipe.data.repository;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link RecipeIngredientEntity}.
 *
 * @author Tomas Rejent
 */
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Integer> {
}
