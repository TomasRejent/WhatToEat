package cz.afrosoft.whattoeat.diet.list.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.afrosoft.whattoeat.diet.list.data.entity.DietEntity;

/**
 * Repository for {@link DietEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface DietRepository extends JpaRepository<DietEntity, Integer> {

}
