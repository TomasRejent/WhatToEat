package cz.afrosoft.whattoeat.cookbook.cookbook.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

import java.util.Set;

/**
 * Represents author of cookbook and his properties.
 *
 * @author Tomas Rejent
 */
public interface Author extends IdEntity {

    /**
     * @return (NotNull) Get authors name.
     */
    String getName();

    /**
     * @return (NotNull) Authors email or empty String if not filled.
     */
    String getEmail();

    /**
     * @return (NotNull) Description of author or empty String if not filled.
     */
    String getDescription();

    /**
     * @return (NotNull) Set of cookbooks written by author. Can be empty set.
     */
    Set<Cookbook> getCookbooks();
}
