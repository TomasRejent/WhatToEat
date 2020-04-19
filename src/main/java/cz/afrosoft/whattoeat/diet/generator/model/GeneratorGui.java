package cz.afrosoft.whattoeat.diet.generator.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import javafx.scene.Node;

/**
 * @author Tomas Rejent
 */
public interface GeneratorGui<T extends GeneratorParameters> {

    void setFilter(RecipeFilter filter);

    void setInterval(LocalDate from, LocalDate to);

    void setDishes(Set<MealTime> dishes);

    void setUser(User user);

    T getParameters();

    Optional<Node> getNode();
}
