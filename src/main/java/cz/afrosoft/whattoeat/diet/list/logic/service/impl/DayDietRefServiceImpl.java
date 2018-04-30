package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.DayDietRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietRefService;

/**
 * @author Tomas Rejent
 */
@Service
public class DayDietRefServiceImpl implements DayDietRefService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayDietRefServiceImpl.class);

    @Autowired
    private DayDietRepository repository;

    @Override
    public DayDietRef fromEntity(final DayDietEntity entity) {
        LOGGER.trace("Converting day diet entity {} to DayDietRef.", entity);
        Validate.notNull(entity);
        Validate.notNull(entity.getId(), "Only persisted entities can be used as reference.");

        return new DayDietRefImpl(entity.getId(), entity.getDay());
    }

    @Override
    public DayDietEntity toEntity(final DayDietRef reference) {
        LOGGER.trace("Converting reference {} to entity.", reference);
        Validate.notNull(reference);
        Validate.notNull(reference.getId(), "Reference must always contains id.");

        return repository.getOne(reference.getId());
    }
}
