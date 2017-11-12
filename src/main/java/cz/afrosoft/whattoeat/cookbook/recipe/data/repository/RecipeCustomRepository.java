package cz.afrosoft.whattoeat.cookbook.recipe.data.repository;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;

import java.util.List;

public interface RecipeCustomRepository {

    List<RecipeEntity> findRecipesByFilter(RecipeFilter filter);

}
