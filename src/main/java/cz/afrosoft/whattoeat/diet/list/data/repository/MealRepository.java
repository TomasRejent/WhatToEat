package cz.afrosoft.whattoeat.diet.list.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;

/**
 * Repository for {@link MealEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface MealRepository extends JpaRepository<MealEntity, Integer> {

}
