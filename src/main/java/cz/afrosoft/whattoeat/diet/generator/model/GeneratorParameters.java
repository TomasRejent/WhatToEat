package cz.afrosoft.whattoeat.diet.generator.model;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author tomas.rejent
 */
public interface GeneratorParameters {

    LocalDate getFrom();

    LocalDate getTo();

    RecipeFilter getFilter();

    Set<MealTime> getDishes();

    User getUser();
}
