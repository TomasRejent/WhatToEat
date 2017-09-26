package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.*;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import java.time.Duration;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface RecipeUpdateObject extends Recipe {

    /**
     * Changes name of recipe.
     *
     * @param name (NotBlank) Name of recipe to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setName(String name);

    /**
     * Changes preparation of recipe.
     *
     * @param preparation (NotBlank) Preparation to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setPreparation(String preparation);

    /**
     * Changes rating of recipe.
     *
     * @param rating Rating to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setRating(int rating);

    /**
     * Changes type of recipe.
     *
     * @param recipeTypes (NotEmpty) Types to set. At least one type must be specified.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setRecipeTypes(Set<RecipeType> recipeTypes);

    /**
     * Changes taste of recipe.
     *
     * @param taste (NotNull) Taste to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setTaste(Taste taste);

    /**
     * Changes preparation time for ingredients.
     *
     * @param ingredientPreparationTime (NotNull) Preparation time to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setIngredientPreparationTime(Duration ingredientPreparationTime);

    /**
     * Changes preparation time fot cooking.
     *
     * @param cookingTime (NotNull) Preparation time to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setCookingTime(Duration cookingTime);

    /**
     * Changes ingredients of recipe.
     *
     * @param ingredients (NotNull) Ingredients to set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setIngredients(Set<RecipeIngredientRef> ingredients);

    /**
     * Changes side dishes of recipe. Make sense only for some type of recipes.
     *
     * @param sideDishes (NotNull) Side dishes to set or empty set.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setSideDishes(Set<RecipeRef> sideDishes);

    /**
     * Changes cookbooks to which recipe belongs.
     *
     * @param cookbooks (NotEmpty) Cookbooks to which recipe is assigned. At least one cookbook must be specified.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setCookbooks(Set<CookbookRef> cookbooks);

    /**
     * Changes keywords of recipe.
     *
     * @param keywords (NotNull) Keywords to associate with recipe.
     * @return (NotNull) This createOrUpdate object so setter calls can be chained.
     */
    RecipeUpdateObject setKeywords(Set<Keyword> keywords);
}
