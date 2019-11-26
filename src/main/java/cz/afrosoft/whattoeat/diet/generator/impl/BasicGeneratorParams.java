package cz.afrosoft.whattoeat.diet.generator.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.Set;

import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;

/**
 * @author Tomas Rejent
 */
public class BasicGeneratorParams implements GeneratorParameters {

    private final LocalDate from;
    private final LocalDate to;
    private final RecipeFilter filter;
    private Set<MealTime> dishes;

    public BasicGeneratorParams(final LocalDate from, final LocalDate to, final RecipeFilter filter, final Set<MealTime> dishes) {
        Validate.notNull(from);
        Validate.notNull(to);
        Validate.isTrue(!to.isBefore(from), "From date must be before or equal to to date.");

        this.from = from;
        this.to = to;
        this.filter = filter;
        this.dishes = dishes;
    }

    @Override
    public LocalDate getFrom() {
        return from;
    }

    @Override
    public LocalDate getTo() {
        return to;
    }

    @Override
    public RecipeFilter getFilter(){
        return filter;
    }

    @Override
    public Set<MealTime> getDishes() {
        return dishes;
    }
}
