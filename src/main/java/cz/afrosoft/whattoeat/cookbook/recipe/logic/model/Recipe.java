package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import java.time.Duration;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;
import cz.afrosoft.whattoeat.core.logic.model.KeywordableEntity;
import cz.afrosoft.whattoeat.core.logic.model.NamedEntity;

/**
 * Represents recipe and defines all its properties. Recipe must belong to at least
 * one cookbook and has at least one ingredient. Recipe can be tagged by multiple keywords.
 *
 * @author Tomas Rejent
 */
public interface Recipe extends RecipeRef, IdEntity, NamedEntity, KeywordableEntity, Comparable<RecipeRef> {

    /**
     * @return (NotNull) Gets description on how to prepare recipe ingredients
     * and cook meal from it.
     */
    String getPreparation();

    /**
     * @return Gets rating of recipe. Rating tells how meal from recipe is tasty for you. It ranges from 1 to 10 where 10 is best.
     * Rating is set per application. It is not user specific.
     */
    int getRating();

    /**
     * @return (NotNull) Gets all types of recipe to which recipe belongs.
     * Recipe must have at least one type.
     */
    Set<RecipeType> getRecipeTypes();

    /**
     * @return (NotNull) Gets taste of recipe. Determines if recipe is sweet or salty for example.
     */
    Taste getTaste();

    /**
     * @return (NotNull) Gets amount of time needed to prepare all ingredients for cooking,
     * Ingredient preparation is for example slicing of onions.
     * <p>
     * This value together with {@link this#getCookingTime()} determines total preparation time.
     * These durations are separated because of food list generator which can consider
     * ingredient preparation as non parallelizable operation and cooking time as parallelizable.
     */
    Duration getIngredientPreparationTime();

    /**
     * @return (NutNull) Gets amount of time needed to cook meal from already prepared ingredients.
     *
     * This value together with {@link this#getIngredientPreparationTime()} determines total preparation time.
     * These durations are separated because of food list generator which can consider
     * ingredient preparation as non parallelizable operation and cooking time as parallelizable.
     */
    Duration getCookingTime();

    /**
     * @return (NotNull) Gets total preparation time. This is more user readable value which is
     * determined from sum of {@link this#getIngredientPreparationTime()} and {@link this#getCookingTime()}
     * by fuzzyfication.
     */
    PreparationTime getTotalPreparationTime();

    /**
     * @return (NotNull) Gets all suitable side dishes for this recipe. Only recipes containing type {@link RecipeType#MAIN_DISH}
     * can have side dishes. Others types returns empty set.
     */
    Set<RecipeRef> getSideDishes();

    /**
     * @return (NotNull) Gets ingredients and their quantities needed to prepare meal from recipe.
     * Recipe must have at least one ingredient.
     */
    Set<RecipeIngredientRef> getIngredients();

    /**
     * @return (NotEmpty) Gets all cookbooks to which recipe belongs. Recipe must belong to at least one cookbook.
     */
    Set<CookbookRef> getCookbooks();

    /**
     * @return Gets default weight in grams for serving.
     */
    Float getDefaultServingWeight();
}
