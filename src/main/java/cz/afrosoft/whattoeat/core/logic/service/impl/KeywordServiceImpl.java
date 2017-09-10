package cz.afrosoft.whattoeat.core.logic.service.impl;

import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.data.repository.KeywordRepository;
import cz.afrosoft.whattoeat.core.gui.component.KeywordLabel;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link KeywordService} which uses {@link KeywordImpl} as implementation
 * of {@link Keyword}.
 *
 * @author Tomas Rejent
 */
@Service
public class KeywordServiceImpl implements KeywordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordServiceImpl.class);

    @Autowired
    private KeywordRepository repository;

    @Override
    public Set<Keyword> getAllKeywords() {
        LOGGER.trace("Getting all keywords.");
        List<KeywordEntity> allKeywords = repository.findAll();
        return ConverterUtil.convertToSortedSet(allKeywords, this::entityToKeyword);
    }

    @Override
    public Keyword getKeyword(final String keywordName) {
        LOGGER.trace("Getting keyword for: {}.", keywordName);
        Keyword keyword = Optional.ofNullable(repository.findByName(keywordName))
                .map(this::entityToKeyword)
                .orElse(new KeywordImpl.Builder()
                        .setName(keywordName));
        LOGGER.trace("Obtained keyword: {}.", keyword);
        return keyword;
    }


    @Override
    public Keyword entityToKeyword(final KeywordEntity entity) {
        Validate.notNull(entity);
        return new KeywordImpl.Builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .build();
    }

    @Override
    public KeywordEntity keywordToEntity(final Keyword keyword) {
        Validate.notNull(keyword);

        return Optional.ofNullable(keyword.getId())
                .map(id -> repository.getOne(id))//id is present, get keyword for this id
                .orElse(//id is null, try to find existing keyword with same name
                        Optional.ofNullable(repository.findByName(keyword.getName()))
                                //no similar entity exist, so new is created with this name
                                .orElseGet(() -> repository.save(new KeywordEntity().setName(keyword.getName())))
                );
    }

    /* GUI related services. */

    @Override
    public KeywordLabel createKeywordLabel(final Keyword keyword) {
        Validate.notNull(keyword);
        return new KeywordLabel(keyword);
    }
}
