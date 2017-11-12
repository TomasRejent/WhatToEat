package cz.afrosoft.whattoeat.cookbook.recipe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;

/**
 * Repository for {@link RecipeEntity}.
 *
 * @author Tomas Rejent
 */
@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {

    List<RecipeEntity> findAll();

    List<RecipeEntity> findByRecipeTypesContains(RecipeType recipeType);

}