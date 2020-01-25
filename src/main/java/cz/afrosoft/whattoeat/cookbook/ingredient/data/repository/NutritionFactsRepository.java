package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.NutritionFactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tomas Rejent
 */
@Repository
public interface NutritionFactsRepository extends JpaRepository<NutritionFactsEntity, Integer> {

}
