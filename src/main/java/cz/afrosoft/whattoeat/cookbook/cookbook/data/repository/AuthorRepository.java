package cz.afrosoft.whattoeat.cookbook.cookbook.data.repository;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link AuthorEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    /**
     * Loads author with specified id including his cookbooks.
     *
     * @param authorId (NotNull) Id of author to find.
     * @return (Nullable) Author with specified id and joined cookbooks or null if does not exist.
     */
    @Query("SELECT a FROM AuthorEntity a LEFT JOIN FETCH a.cookbooks WHERE a.id = :authorId")
    AuthorEntity findOneWithCookbooks(@Param("authorId") Integer authorId);

    /**
     * Loads all existing Authors including their cookbooks.
     *
     * @return (NotNull)
     */
    @Query("SELECT a FROM AuthorEntity a LEFT JOIN FETCH a.cookbooks")
    List<AuthorEntity> findAllWithCookbooks();

    @Query("DELETE FROM AuthorEntity WHERE id = :id")
    void deleteById(Integer id);
}
