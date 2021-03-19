package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.user.lodic.service.UserService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.entity.DietEntity;
import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.DietRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.*;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;

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
            .setUser(userService.userToEntity(dietChanges.getUser().get()))
            .setDayDiets(generatedDiet);

        return entityToDiet(repository.save(entity));
    }

    @Override
    @Transactional
    public Diet copy(final Diet source, final DietCopyParams params) {
        DietEntity entity = new DietEntity();

        entity.setName(params.getDietName());
        entity.setFrom(params.getStartDate());
        entity.setTo(params.getStartDate().plusDays(source.getFrom().until(source.getTo(), ChronoUnit.DAYS)));
        entity.setUser(userService.userToEntity(params.getUser()));
        entity.setGenerator(source.getGeneratorType());
        List<DayDietEntity> dayDiets = new LinkedList<>();
        LocalDate currentDay = params.getStartDate();
        for(DayDietRef dayDietRef : source.getDayDiets()){
            DayDietEntity dayDietEntity = dayDietRefService.toEntity(dayDietRef);
            DayDietEntity dayDietCopy = new DayDietEntity();
            dayDietCopy.setDay(currentDay);
            currentDay = currentDay.plusDays(1);
            dayDietCopy.setBreakfast(copyMeals(dayDietEntity.getBreakfast(), params.getBreakfastsParams()));
            dayDietCopy.setSnack(copyMeals(dayDietEntity.getSnack(), params.getSnacksParams()));
            dayDietCopy.setLunch(copyMeals(dayDietEntity.getLunch(), params.getLunchParams()));
            dayDietCopy.setAfternoonSnack(copyMeals(dayDietEntity.getAfternoonSnack(), params.getAfternoonSnacksParams()));
            dayDietCopy.setDinner(copyMeals(dayDietEntity.getDinner(), params.getDinnersParams()));
            dayDietCopy.setOther(copyMeals(dayDietEntity.getOther(), params.getOthersParams()));
            dayDiets.add(dayDietCopy);
        }

        entity.setDayDiets(dayDiets);
        return entityToDiet(repository.save(entity));
    }

    private List<MealEntity> copyMeals(List<MealEntity> source, MealCopyParams params){
        if(source != null && params.isCopyEnabled()){
            return source.stream().map(mealEntity -> {
                MealEntity mealCopy = new MealEntity();
                mealCopy.setRecipe(mealEntity.getRecipe());
                if(mealEntity.getRecipe() != null){ // servings parameter is applied only for recipes, it is skipped for ingredients
                    mealCopy.setServings(params.getServings());
                }
                mealCopy.setAmount(mealEntity.getAmount());
                mealCopy.setIngredient(mealEntity.getIngredient());
                return mealCopy;
            }).collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }
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
    public Collection<Meal> getDietMealsInInterval(final Diet diet, final LocalDate from, final LocalDate to) {
        Validate.notNull(diet);

        List<Meal> meals = new LinkedList<>();
        List<DayDiet> dayDiets = ConverterUtil.convertToList(diet.getDayDiets(), dayDietService::loadDayDiet);
        for (DayDiet dayDiet : dayDiets) {
            if(from != null && to != null){
                LocalDate day = dayDiet.getDay();
                if(day.isBefore(from) || day.isAfter(to)){
                    continue;
                }
            }

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
            .setUser(userService.entityToUser(entity.getUser()))
            .setGenerator(entity.getGenerator())
            .setDescription(entity.getDescription())
            .setDayDiets(ConverterUtil.convertToList(entity.getDayDiets(), dayDietRefService::fromEntity));

        return builder.build();
    }
}
