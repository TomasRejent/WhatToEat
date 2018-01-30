package cz.afrosoft.whattoeat.cookbook.cookbook.data.repository;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link CookbookEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface CookbookRepository extends JpaRepository<CookbookEntity, Integer> {

    /**
     * @param cookbookId (NotNull) Id of cookbook to find.
     * @return (Nullable) Cookbook with specified id with joined authors or null if does not exist.
     */
    @Query("SELECT cb FROM CookbookEntity cb LEFT JOIN FETCH cb.authors WHERE cb.id = :cookbookId")
    CookbookEntity findOneWithAuthors(@Param("cookbookId") Integer cookbookId);

    /**
     * Loads all existing Cookbooks including their authors.
     *
     * @return (NotNull)
     */
    @Query("SELECT cb FROM CookbookEntity cb LEFT JOIN FETCH cb.authors")
    List<CookbookEntity> findAllWithAuthors();

    @Query("DELETE FROM CookbookEntity WHERE id = :id")
    void deleteById(Integer id);

}
