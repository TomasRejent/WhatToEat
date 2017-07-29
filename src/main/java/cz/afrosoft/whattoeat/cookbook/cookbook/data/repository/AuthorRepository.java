package cz.afrosoft.whattoeat.cookbook.cookbook.data.repository;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link AuthorEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
}
