package cz.afrosoft.whattoeat.cookbook.user.data.repository;

import cz.afrosoft.whattoeat.cookbook.user.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Tomas Rejent
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
