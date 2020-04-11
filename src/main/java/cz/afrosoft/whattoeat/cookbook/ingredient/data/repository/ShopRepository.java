package cz.afrosoft.whattoeat.cookbook.ingredient.data.repository;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tomas Rejent
 */
@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {

    ShopEntity findByName(String name);

}
