package cz.afrosoft.whattoeat.core.logic.service;

import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import java.util.Collection;
import java.util.Set;

/**
 * Service providing methods for operating on {@link Keyword}.
 *
 * @author Tomas Rejent
 */
public interface KeywordService {

    /**
     * Gets keywords for specified names. If keywords do not exist they are created. Created keywords are persisted.
     *
     * @param keywordNames (NotNull) Collection with name of keywords to get.
     * @return (NotNull) Set of keywords with specified names.
     */
    Set<Keyword> getOrCreateKeywords(Collection<String> keywordNames);

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
     * Creates entity from keyword.
     *
     * @param keyword (NotNull) Keyword to convert to entity.
     * @return (NotNull) Created entity.
     */
    KeywordEntity keywordToEntity(Keyword keyword);

}
