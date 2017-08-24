package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link IngredientEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Integer> {

}
