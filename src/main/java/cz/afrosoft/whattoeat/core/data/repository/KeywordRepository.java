package cz.afrosoft.whattoeat.core.data.repository;

import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for {@link Keyword}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {

    List<KeywordEntity> findByNameIn(Collection<String> names);

}
