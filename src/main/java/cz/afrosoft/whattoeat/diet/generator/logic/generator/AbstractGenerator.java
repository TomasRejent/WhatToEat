/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.generator.logic.generator;

import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;
import cz.afrosoft.whattoeat.diet.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.diet.generator.logic.model.GeneratorParameters;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.print.attribute.standard.DialogTypeSelection;

/**
 *
 * @author Tomas Rejent
 */
public abstract class AbstractGenerator implements Generator{

    protected Diet createDietSkeleton(final GeneratorParameters parameters){
        checkNotNull(parameters, "Generator parameters cannot be null.");

        final Diet diet = new Diet();
        diet.setName(parameters.getName());
        diet.setFrom(parameters.getFrom());
        diet.setTo(parameters.getTo());
        diet.setGeneratorId(this.getId());
        return diet;
    }

    protected Collection<Recipe> filterRecipesByType(final Collection<Recipe> recipes, final RecipeType type){
        checkNotNull(recipes, "Recipes cannot be null.");
        checkNotNull(type, "Recipe type cannot be null.");

        return recipes.stream().filter((recipe) -> recipe.getRecipeTypes().contains(type)).collect(Collectors.toSet());
    }

    protected List<DayDiet> createDayDietSkeleton(final GeneratorParameters parameters){
        checkNotNull(parameters, "Generator parameters cannot be null.");

        final int numberOfDays = (int) ChronoUnit.DAYS.between(parameters.getFrom(), parameters.getTo()) + 1;
        List<DayDiet> dayDiets = new ArrayList<> (numberOfDays);

        LocalDate currentDay = parameters.getFrom();
        for(int day = 0; day < numberOfDays ;day++){
            DayDiet dayDiet = new DayDiet();
            dayDiet.setDay(currentDay);
            dayDiets.add(dayDiet);
            currentDay = currentDay.plusDays(1);
        }

        return dayDiets;
    }


}
