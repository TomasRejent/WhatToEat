package cz.afrosoft.whattoeat.diet.list.data.repository;

import cz.afrosoft.whattoeat.diet.list.data.entity.DietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link DietEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface DietRepository extends JpaRepository<DietEntity, Integer> {

}
