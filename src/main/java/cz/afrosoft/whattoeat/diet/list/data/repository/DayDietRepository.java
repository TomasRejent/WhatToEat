package cz.afrosoft.whattoeat.diet.list.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;

/**
 * Repository for {@link DayDietEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface DayDietRepository extends JpaRepository<DayDietEntity, Integer> {

}
