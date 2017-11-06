package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.UnitConversionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link UnitConversionEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface UnitConversionRepository extends JpaRepository<UnitConversionEntity, Integer> {

}
