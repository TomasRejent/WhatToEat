package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.list.data.entity.DietEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.DietRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietUpdateObject;

/**
 * @author Tomas Rejent
 */
@Service
public class DietServiceImpl implements DietService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietServiceImpl.class);

    @Autowired
    private DietRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Set<Diet> getAllDiets() {
        LOGGER.debug("Getting all diets.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToDiet);
    }

    @Override
    public DietUpdateObject getCreateObject() {
        return new DietImpl.Builder();
    }

    @Override
    public Diet createOrUpdate(final DietUpdateObject dietChanges) {
        LOGGER.debug("Updating diet: {}", dietChanges);

        DietEntity entity = new DietEntity();
        entity.setId(dietChanges.getId().orElse(null))
                .setName(dietChanges.getName().get())
                .setFrom(dietChanges.getFrom().get())
                .setTo(dietChanges.getTo().get())
                .setGenerator(dietChanges.getGenerator().get())
                .setDescription(dietChanges.getDescription().orElse(null));

        // TODO generate day diets

        return entityToDiet(repository.save(entity));
    }

    private Diet entityToDiet(final DietEntity entity) {
        Validate.notNull(entity);

        DietImpl.Builder builder = new DietImpl.Builder(entity.getId());
        builder.setName(entity.getName())
                .setFrom(entity.getFrom())
                .setTo(entity.getTo())
                .setGenerator(entity.getGenerator())
                .setDescription(entity.getDescription());
        //TODO add day diets

        return builder.build();
    }
}
