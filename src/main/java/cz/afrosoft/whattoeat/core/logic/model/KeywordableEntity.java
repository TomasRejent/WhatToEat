package cz.afrosoft.whattoeat.core.logic.model;

import java.util.Set;

/**
 * Represents entity which can have keywords attached to it.
 *
 * @author Tomas Rejent
 */
public interface KeywordableEntity {

    /**
     * @return (NotNull) Gets all keywords attached to entity or empty set if entity does
     * not have any keyword.
     */
    Set<Keyword> getKeywords();

}
