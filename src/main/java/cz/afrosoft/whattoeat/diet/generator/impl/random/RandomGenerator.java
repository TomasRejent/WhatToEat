package cz.afrosoft.whattoeat.diet.generator.impl.random;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorParams;
import cz.afrosoft.whattoeat.diet.generator.model.Generator;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Tomas Rejent
 */
@Component
public class RandomGenerator implements Generator<BasicGeneratorParams> {

    private static final float DEFAULT_SERVINGS = 2;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRefService recipeRefService;

    private List<Recipe> recipes = List.of();
    private boolean subsequentDay = false;
    private Random random;
    private Recipe recipe;
    private Optional<Recipe> sideDish;

    @Override
    public GeneratorType getType() {
        return GeneratorType.RANDOM;
    }

    @Override
    public GeneratorGui<BasicGeneratorParams> getGui() {
        return new RandomGeneratorGui();
    }

    @Override
    public List<DayDietEntity> generate(final GeneratorParameters parameters) {
        Validate.notNull(parameters);

        initGeneration();

        List<DayDietEntity> dayDiets = new LinkedList<>();
        for (LocalDate day = parameters.getFrom(); !day.isAfter(parameters.getTo()); day = day.plusDays(1)) {
            DayDietEntity dayDiet = new DayDietEntity();
            dayDiet.setDay(day);
            dayDiet.setLunch(generateLunch());
            dayDiets.add(dayDiet);
        }

        return dayDiets;
    }

    private List<MealEntity> generateLunch(){
        if(!subsequentDay){
            recipe = recipes.get(random.nextInt(recipes.size()));
            List<RecipeRef> sideDishes = new ArrayList<>(recipe.getSideDishes());
            if(sideDishes.isEmpty()){
                sideDish = Optional.empty();
            }else if(sideDishes.size() == 1){
                sideDish = Optional.of(recipeService.getRecipeById(sideDishes.get(0).getId()));
            }else{
                RecipeRef sideDishRef = sideDishes.get(random.nextInt(sideDishes.size()));
                sideDish = Optional.of(recipeService.getRecipeById(sideDishRef.getId()));
            }
        }
        subsequentDay = !subsequentDay;
        return constructMeals();
    }

    private List<MealEntity> constructMeals(){
        List<MealEntity> meals = new ArrayList<>(2);
        MealEntity lunchMeal = new MealEntity();
        lunchMeal.setServings(DEFAULT_SERVINGS);
        lunchMeal.setRecipe(recipeRefService.toEntity(recipe));
        meals.add(lunchMeal);
        if(sideDish.isPresent()){
            MealEntity sideDishMeal = new MealEntity();
            sideDishMeal.setServings(DEFAULT_SERVINGS);
            sideDishMeal.setRecipe(recipeRefService.toEntity(sideDish.get()));
            meals.add(sideDishMeal);
        }
        return meals;
    }

    private void initGeneration(){
        RecipeFilter mainDishFilter = new RecipeFilter.Builder().setType(Set.of(RecipeType.MAIN_DISH)).build();
        recipes = new ArrayList<>(recipeService.getFilteredRecipes(mainDishFilter));
        subsequentDay = false;
        random = new Random();
    }
}
