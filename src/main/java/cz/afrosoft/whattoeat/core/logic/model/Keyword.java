package cz.afrosoft.whattoeat.core.logic.model;

/**
 * Represents keyword. Keyword is String which can describe additional
 * properties of entity without need of changing database model.
 * This allows user to add additional information to recipes, ingredients
 * and other entities implementing {@link KeywordableEntity}.
 * <p>
 * There is no internationalization for keywords. This causes that keywords
 * are not shared between languages.
 *
 * @author Tomas Rejent
 */
public interface Keyword extends IdEntity, Comparable<Keyword> {

    /**
     * @return (NotNull) Gets name (text) of keyword.
     */
    String getName();

}
