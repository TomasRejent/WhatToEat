package cz.afrosoft.whattoeat.cookbook.cookbook.logic.model;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

import java.util.Set;

/**
 * Represents cookbook. Cookbook can have multiple authors. Cookbook is set
 * of recipes which have something in common. One recipe can belong to multiple
 * cookbooks.
 *
 * @author Tomas Rejent
 */
public interface Cookbook extends CookbookRef, IdEntity, Comparable<CookbookRef> {

    /**
     * @return (NotNull) Description of cookbook or empty String.
     */
    String getDescription();

    /**
     * @return (NotEmpty) Set containing all authors of cookbook. At least one author must exist.
     */
    Set<AuthorRef> getAuthors();

    /**
     * @return (NotNull) Gets all recipes belonging to this cookbook.
     */
    Set<RecipeRef> getRecipes();
}
