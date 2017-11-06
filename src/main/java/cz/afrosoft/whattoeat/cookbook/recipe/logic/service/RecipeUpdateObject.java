package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

/**
 * Update object for {@link Recipe} Serves for its creation or editing. This allows {@link Recipe} to be immutable.
 *
 * @author Tomas Rejent
 */
public interface RecipeUpdateObject {

    /**
     * @return (NotNull) Empty optional if update object is for creating new entity or optional filled with id of entity
     * which is edited.
     */
    Optional<Integer> getId();

    /**
     * @return (NotNull) Name of recipe. Is empty optional if update object is for creating new entity and value
     * was not yet set by {@link #setName(String)}. Else is optional filled with name set by {@link #setName(String)}.
     * If name was not set and this object is for update of existing recipe then it contains original name of recipe.
     */
    Optional<String> getName();

    /**
     * @return (NotNull) Preparation of recipe. Is empty optional if update object is for creating new entity and value
     * was not yet set by {@link #setPreparation(String)}. Else is optional filled with preparation set by {@link #setPreparation(String)}.
     * If preparation was not set and this object is for update of existing recipe then it contains original preparation of recipe.
     */
    Optional<String> getPreparation();

    /**
     * @return (NotNull) Rating of recipe. Is empty optional if update object is for creating new entity and value
     * was not yet set by {@link #setRating(int)}. Else is optional filled with rating set by {@link #setRating(int)}.
     * If rating was not set and this object is for update of existing recipe then it contains original rating of recipe.
     */
    Optional<Integer> getRating();

    /**
     * @return (NotNull) All types of this recipe. Is empty set if update object is for creating new entity and value
     * was not yet set by {@link #setRecipeTypes(Set)}. Else is set filled with types set by {@link #setRecipeTypes(Set)}.
     * If recipe types was not set and this object is for update of existing recipe then it contains original recipe types.
     */
    Set<RecipeType> getRecipeTypes();

    /**
     * @return (NotNull) Taste of recipe. Is empty optional if update object is for creating new entity and value
     * was not yet set by {@link #setTaste(Taste)}. Else is optional filled with taste set by {@link #setTaste(Taste)}.
     * If taste was not set and this object is for update of existing recipe then it contains original taste of recipe.
     */
    Optional<Taste> getTaste();

    /**
     * @return (NotNull) Duration needed to prepare ingredients for recipe. Is empty optional if update object is for creating new entity and value
     * was not yet set by {@link #setIngredientPreparationTime(Duration)}. Else is optional filled with duration set by {@link #setIngredientPreparationTime(Duration)}.
     * If duration was not set and this object is for update of existing recipe then it contains original duration.
     */
    Optional<Duration> getIngredientPreparationTime();

    /**
     * @return (NotNull) Duration needed to cook meal from recipe ingredients. Is empty optional if update object is for creating new entity and value
     * was not yet set by {@link #setCookingTime(Duration)}. Else is optional filled with duration set by {@link #setCookingTime(Duration)}.
     * If duration was not set and this object is for update of existing recipe then it contains original duration.
     */
    Optional<Duration> getCookingTime();

    /**
     * @return (NotNull) Possible side dishes for this recipe. Is empty set if update object is for creating new entity and value
     * was not yet set by {@link #setSideDishes(Set)}. Else is set filled with side dishes set by {@link #setSideDishes(Set)}.
     * If side dishes were not set and this object is for update of existing recipe then it contains original side dishes for recipe.
     */
    Set<RecipeRef> getSideDishes();

    /**
     * @return (NotNull) Ingredients of recipe. Is empty set if update object is for creating new entity and value
     * was not yet set by {@link #setIngredients(Set)}. Else is set filled with ingredients set by {@link #setIngredients(Set)}.
     * If ingredients were not set and this object is for update of existing recipe then it contains original ingredients of recipe.
     */
    Set<RecipeIngredientUpdateObject> getIngredients();

    /**
     * @return (NotNull) Cookbooks to which recipe belongs. Is empty set if update object is for creating new entity and value
     * was not yet set by {@link #setCookbooks(Set)}. Else is set filled with cookbooks set by {@link #setCookbooks(Set)}.
     * If cookbooks were not set and this object is for update of existing recipe then it contains original cookbooks.
     */
    Set<CookbookRef> getCookbooks();

    /**
     * @return (NotNull) Keywords of recipe. Is empty set if update object is for creating new entity and value
     * was not yet set by {@link #setKeywords(Set)}. Else is set filled with keywords set by {@link #setKeywords(Set)}.
     * If keywords were not set and this object is for update of existing recipe then it contains original keywords of recipe.
     */
    Set<Keyword> getKeywords();

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
    RecipeUpdateObject setIngredients(Set<RecipeIngredientUpdateObject> ingredients);

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
