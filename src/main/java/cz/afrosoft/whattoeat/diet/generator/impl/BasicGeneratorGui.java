package cz.afrosoft.whattoeat.diet.generator.impl;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import javafx.scene.Node;

/**
 * @author Tomas Rejent
 */
public class BasicGeneratorGui implements GeneratorGui<BasicGeneratorParams> {

    private LocalDate from;
    private LocalDate to;
    private Set<MealTime> dishes;
    private RecipeFilter filter;
    private User user;

    @Override
    public void setInterval(final LocalDate from, final LocalDate to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void setFilter(final RecipeFilter filter) {
        this.filter = filter;
    }

    @Override
    public void setDishes(final Set<MealTime> dishes) {
        this.dishes = dishes;
    }

    @Override
    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public BasicGeneratorParams getParameters() {
        return new BasicGeneratorParams(from, to, filter, dishes, user);
    }

    @Override
    public Optional<Node> getNode() {
        return Optional.empty();
    }
}
