package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.entity.DietEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.DietRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietRefService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietCreateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;

/**
 * @author Tomas Rejent
 */
@Service
public class DietServiceImpl implements DietService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietServiceImpl.class);

    @Autowired
    private DietRepository repository;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private DayDietRefService dayDietRefService;

    @Override
    @Transactional(readOnly = true)
    public Set<Diet> getAllDiets() {
        LOGGER.debug("Getting all diets.");
        return ConverterUtil.convertToSortedSet(repository.findAll(), this::entityToDiet);
    }

    @Override
    public DietCreateObject getCreateObject() {
        return new DietImpl.Builder();
    }

    @Override
    public Diet create(final DietCreateObject dietChanges) {
        LOGGER.debug("Creating diet: {}", dietChanges);
        Validate.notNull(dietChanges);

        GeneratorType generatorType = dietChanges.getGenerator().get();
        List<DayDietEntity> generatedDiet = generatorService.generate(generatorType, dietChanges.getGeneratorParams().get());

        DietEntity entity = new DietEntity();
        entity.setName(dietChanges.getName().get())
            .setFrom(dietChanges.getFrom().get())
            .setTo(dietChanges.getTo().get())
            .setGenerator(generatorType)
            .setDescription(dietChanges.getDescription().orElse(null))
            .setDayDiets(generatedDiet);

        return entityToDiet(repository.save(entity));
    }

    @Override
    public void delete(final Diet diet) {
        LOGGER.debug("Deleting diet: {}", diet);
        Validate.notNull(diet, "Cannot delete null diet.");

        repository.deleteById(diet.getId());
    }

    private Diet entityToDiet(final DietEntity entity) {
        Validate.notNull(entity);

        DietImpl.Builder builder = new DietImpl.Builder(entity.getId());
        builder.setName(entity.getName())
            .setFrom(entity.getFrom())
            .setTo(entity.getTo())
            .setGenerator(entity.getGenerator())
            .setDescription(entity.getDescription())
            .setDayDiets(ConverterUtil.convertToList(entity.getDayDiets(), dayDietRefService::fromEntity));

        return builder.build();
    }
}
