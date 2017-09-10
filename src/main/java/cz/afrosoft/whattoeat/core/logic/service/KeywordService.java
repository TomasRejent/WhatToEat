package cz.afrosoft.whattoeat.core.logic.service;

import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.gui.component.KeywordLabel;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import java.util.Set;

/**
 * Service providing methods for operating on {@link Keyword}.
 *
 * @author Tomas Rejent
 */
public interface KeywordService {

    /* Public API*/

    /**
     * @return (NotNull) Set of all existing keywords.
     */
    Set<Keyword> getAllKeywords();

    /**
     * Gets keyword for specified name. If keyword already exist it is loaded from database and id is filled.
     * If keyword does not exist, instance without id is created. Non existing keyword is not persisted.
     *
     * @param keywordName (NotBlank) Name for which keyword should be created.
     * @return (NotNull)
     */
    Keyword getKeyword(String keywordName);

    /* API for other services. */

    /**
     * This is not API for front end, it should be used only by other services.
     * <p>
     * Converts entity to keyword.
     *
     * @param entity (NotNull) Entity to convert.
     * @return (NotNull) Converted Keyword.
     */
    Keyword entityToKeyword(KeywordEntity entity);

    /**
     * This is not API for front end, it should be used only by other services.
     * <p>
     * Gets entity for keyword. If it does not exist it is created and persisted.
     *
     * @param keyword (NotNull) Keyword to convert to entity.
     * @return (NotNull) Created entity.
     */
    KeywordEntity keywordToEntity(Keyword keyword);

    /* GUI related services. */

    /**
     * Creates display component for specified keyword. It may apply specific component style based on keyword.
     * For example meat related keyword can have red background while vegetable green. This logic is however not yet
     * implemented.
     *
     * @param keyword (NotNull) Keyword to display.
     * @return (NotNull) New component for displaying keyword.
     */
    KeywordLabel createKeywordLabel(Keyword keyword);

}
