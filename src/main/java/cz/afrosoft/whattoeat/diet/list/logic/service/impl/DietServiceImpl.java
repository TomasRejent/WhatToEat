package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.entity.DietEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.DietRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietRefService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietCreateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private DayDietService dayDietService;

    @Override
    @Transactional(readOnly = true)
    public Diet getById(final Integer id) {
        LOGGER.debug("Getting diet by id {}", id);
        Validate.notNull(id);
        return entityToDiet(repository.getOne(id));
    }

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

    @Override
    public Collection<Meal> getDietMeals(final Diet diet) {
        Validate.notNull(diet);

        List<Meal> meals = new LinkedList<>();
        List<DayDiet> dayDiets = ConverterUtil.convertToList(diet.getDayDiets(), dayDietService::loadDayDiet);
        for (DayDiet dayDiet : dayDiets) {
            meals.addAll(dayDiet.getBreakfasts());
            meals.addAll(dayDiet.getSnacks());
            meals.addAll(dayDiet.getLunch());
            meals.addAll(dayDiet.getAfternoonSnacks());
            meals.addAll(dayDiet.getDinners());
            meals.addAll(dayDiet.getOthers());
        }

        return meals;
    }

    @Override
    @Transactional
    public void replaceDayDiets(final Diet diet, final List<DayDietEntity> dayDiets) {
        LOGGER.debug("Replacing day diets for diet {}", diet);
        Validate.notNull(diet);

        DietEntity entity = repository.getOne(diet.getId());
        entity.setDayDiets(dayDiets);
        repository.save(entity);
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
