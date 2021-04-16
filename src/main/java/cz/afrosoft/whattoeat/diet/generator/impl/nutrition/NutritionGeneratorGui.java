package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.gui.component.RecipeFilterComponent;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/NutritionGenerator.fxml")
public class NutritionGeneratorGui extends GridPane implements GeneratorGui<NutritionGeneratorParams>{

    @FXML
    private NutritionCriteriaField energyField;
    @FXML
    private NutritionCriteriaField fatField;
    @FXML
    private NutritionCriteriaField saturatedFatField;
    @FXML
    private NutritionCriteriaField carbohydrateField;
    @FXML
    private NutritionCriteriaField sugarField;
    @FXML
    private NutritionCriteriaField proteinField;
    @FXML
    private NutritionCriteriaField saltField;
    @FXML
    private NutritionCriteriaField fiberField;
    @FXML
    private RecipeFilterComponent breakfastFilter;
    @FXML
    private RecipeFilterComponent snackFilter;
    @FXML
    private RecipeFilterComponent lunchFilter;
    @FXML
    private RecipeFilterComponent afternoonSnackFilter;
    @FXML
    private RecipeFilterComponent dinnerFilter;

    private RecipeFilter basicFilter;
    private LocalDate from;
    private LocalDate to;
    private Set<MealTime> dishes;
    private User user;

    @PostConstruct
    private void init(){
        breakfastFilter.setRecipeFilter(new RecipeFilter.Builder().setType(Set.of(RecipeType.BREAKFAST)).build());
        snackFilter.setRecipeFilter(new RecipeFilter.Builder().setType(Set.of(RecipeType.SNACK)).build());
        lunchFilter.setRecipeFilter(new RecipeFilter.Builder().setType(Set.of(RecipeType.MAIN_DISH)).build());
        afternoonSnackFilter.setRecipeFilter(new RecipeFilter.Builder().setType(Set.of(RecipeType.SNACK)).build());
        dinnerFilter.setRecipeFilter(new RecipeFilter.Builder().setType(Set.of(RecipeType.DINNER)).build());
    }

    @Override
    public void setFilter(final RecipeFilter filter) {
        this.basicFilter = filter;
    }

    @Override
    public void setInterval(final LocalDate from, final LocalDate to) {
        this.from = from;
        this.to = to;
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
    public NutritionGeneratorParams getParameters() {
        return new NutritionGeneratorParams(
                from, to, user, basicFilter, dishes,
                energyField.getNutritionCriteria(),
                fatField.getNutritionCriteria(),
                saturatedFatField.getNutritionCriteria(),
                carbohydrateField.getNutritionCriteria(),
                sugarField.getNutritionCriteria(),
                proteinField.getNutritionCriteria(),
                saltField.getNutritionCriteria(),
                fiberField.getNutritionCriteria(),
                breakfastFilter.getRecipeFilter(),
                snackFilter.getRecipeFilter(),
                lunchFilter.getRecipeFilter(),
                afternoonSnackFilter.getRecipeFilter(),
                dinnerFilter.getRecipeFilter()
        );
    }

    @Override
    public Optional<Node> getNode() {
        return Optional.of(this);
    }
}
