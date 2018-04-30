package cz.afrosoft.whattoeat.diet.list.data.repository;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link DayDietEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface DayDietRepository extends JpaRepository<DayDietEntity, Integer> {

}
