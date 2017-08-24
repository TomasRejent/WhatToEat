package cz.afrosoft.whattoeat.core.logic.service.impl;

import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.data.repository.KeywordRepository;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link KeywordService} which uses {@link KeywordImpl} as implementation
 * of {@link Keyword}.
 *
 * @author Tomas Rejent
 */
@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordRepository repository;

    @Override
    @Transactional
    public Set<Keyword> getOrCreateKeywords(final Collection<String> keywordNames) {
        Validate.noNullElements(keywordNames);
        List<KeywordEntity> existingKeywords = repository.findByNameIn(keywordNames);
        Set<String> existingNames = existingKeywords.stream().map(KeywordEntity::getName).collect(Collectors.toSet());
        Set<String> toCreate = keywordNames.stream().filter(name -> !existingNames.contains(name)).collect(Collectors.toSet());
        List<KeywordEntity> savedEntities = repository.save(toCreate.stream().map(name -> new KeywordEntity().setName(name)).collect(Collectors.toSet()));
        SortedSet<Keyword> result = new TreeSet<>();
        result.addAll(ConverterUtil.convertToSet(existingKeywords, this::entityToKeyword));
        result.addAll(ConverterUtil.convertToSet(savedEntities, this::entityToKeyword));
        return result;
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
        return new KeywordEntity()
                .setId(keyword.getId())
                .setName(keyword.getName());
    }
}
