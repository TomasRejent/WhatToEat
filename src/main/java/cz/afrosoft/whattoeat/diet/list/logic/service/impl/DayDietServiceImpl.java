package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.repository.DayDietRepository;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomas Rejent
 */
@Service
class DayDietServiceImpl implements DayDietService {

    @Autowired
    private DayDietRepository repository;

    @Autowired
    private MealService mealService;

    @Override
    @Transactional(readOnly = true)
    public DayDiet loadDayDiet(final DayDietRef reference) {
        Validate.notNull(reference);
        return entityToDayDiet(repository.getOne(reference.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public DayDietUpdateObject getUpdateObject(final DayDietRef dayDiet) {
        Validate.notNull(dayDiet);
        DayDietEntity dayDietEntity = repository.getOne(dayDiet.getId());
        return new DayDietImpl.DayDietUpdateObjectImpl(dayDietEntity.getId())
                .setDay(dayDiet.getDay())
                .setBreakfasts(ConverterUtil.convertToList(dayDietEntity.getBreakfast(), mealService::entityToMeal))
                .setSnacks(ConverterUtil.convertToList(dayDietEntity.getSnack(), mealService::entityToMeal))
                .setLunch(ConverterUtil.convertToList(dayDietEntity.getLunch(), mealService::entityToMeal))
                .setAfternoonSnacks(ConverterUtil.convertToList(dayDietEntity.getAfternoonSnack(), mealService::entityToMeal))
                .setDinners(ConverterUtil.convertToList(dayDietEntity.getDinner(), mealService::entityToMeal))
                .setOthers(ConverterUtil.convertToList(dayDietEntity.getOther(), mealService::entityToMeal));
    }

    @Override
    public DayDiet update(final DayDietUpdateObject dayDietChanges) {
        if(!dayDietChanges.getId().isPresent()){
            throw new IllegalArgumentException("Cannot update non existing day diet.");
        }else {
            DayDietEntity dayDietEntity = repository.getOne(dayDietChanges.getId().get());
            //TODO
        }


        return null;
    }

    private DayDiet entityToDayDiet(final DayDietEntity entity) {
        Validate.notNull(entity);

        return new DayDietImpl.Builder(entity.getId())
                .setDay(entity.getDay())
                .setBreakfasts(ConverterUtil.convertToList(entity.getBreakfast(), mealService::entityToMeal))
                .setSnacks(ConverterUtil.convertToList(entity.getSnack(), mealService::entityToMeal))
                .setLunch(ConverterUtil.convertToList(entity.getLunch(), mealService::entityToMeal))
                .setAfternoonSnacks(ConverterUtil.convertToList(entity.getAfternoonSnack(), mealService::entityToMeal))
                .setDinners(ConverterUtil.convertToList(entity.getDinner(), mealService::entityToMeal))
                .setOthers(ConverterUtil.convertToList(entity.getOther(), mealService::entityToMeal))
                .build();
    }
}
