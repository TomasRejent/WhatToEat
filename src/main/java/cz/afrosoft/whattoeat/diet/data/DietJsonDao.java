/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.data;

import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import cz.afrosoft.whattoeat.diet.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.JsonDao;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of persistence service of JSON type for entity {@link Diet}.
 * @author Tomas Rejent
 */
public class DietJsonDao extends JsonDao<Diet, String> implements DietDao{

    public DietJsonDao() {
        super(LocationUtils.getDietFile(), Diet[].class);
    }

    @Override
    public void updateMeal(Meal meal) {
        Validate.notNull(meal);

        List<Diet> diets = new ArrayList<>(readAll());
        for(int dietIdx = 0 ; dietIdx < diets.size() ; dietIdx++){
            Diet diet = diets.get(dietIdx);
            List<DayDiet> days = diet.getDays();
            for(int dayIdx = 0 ; dayIdx < days.size() ; dayIdx++){
                DayDiet dayDiet = days.get(dayIdx);
                if(meal.equals(dayDiet.getBreakfast())){
                    dayDiet.setBreakfast(meal);
                    saveAllInternal(diets);
                    return;
                }
                if(meal.equals(dayDiet.getMorningSnack())){
                    dayDiet.setMorningSnack(meal);
                    saveAllInternal(diets);
                    return;
                }
                if(meal.equals(dayDiet.getSoup())){
                    dayDiet.setSoup(meal);
                    saveAllInternal(diets);
                    return;
                }
                if(meal.equals(dayDiet.getLunch())){
                    dayDiet.setLunch(meal);
                    saveAllInternal(diets);
                    return;
                }
                if(meal.equals(dayDiet.getSideDish())){
                    dayDiet.setSideDish(meal);
                    saveAllInternal(diets);
                    return;
                }
                if(meal.equals(dayDiet.getAfternoonSnack())){
                    dayDiet.setAfternoonSnack(meal);
                    saveAllInternal(diets);
                    return;
                }
                if(meal.equals(dayDiet.getDinner())){
                    dayDiet.setDinner(meal);
                    saveAllInternal(diets);
                    return;
                }
            }
        }
    }
}
