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
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
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

    private boolean subsequentDay = false;
    private Random random;
    private Recipe recipe;
    private Optional<Recipe> sideDish;

    private GeneratorParameters parameters;
    private List<Recipe> breakfastRecipes;
    private List<Recipe> snackRecipes;
    private List<Recipe> mainDishRecipes;

    private List<Recipe> remainingUniqueBreakfastRecipes;
    private List<Recipe> remainingUniqueSnackRecipes;
    private List<Recipe> remainingUniqueMainDishRecipes;

    @Override
    public GeneratorType getType() {
        return GeneratorType.RANDOM;
    }

    @Override
    public GeneratorGui<BasicGeneratorParams> getGui() {
        return new RandomGeneratorGui();
    }

    @Override
    public List<DayDietEntity> generate(final BasicGeneratorParams parameters) {
        Validate.notNull(parameters);
        this.parameters = parameters;

        initGeneration(parameters);

        List<DayDietEntity> dayDiets = new LinkedList<>();
        for (LocalDate day = parameters.getFrom(); !day.isAfter(parameters.getTo()); day = day.plusDays(1)) {
            DayDietEntity dayDiet = new DayDietEntity();
            dayDiet.setDay(day);
            dayDiet.setBreakfast(generateBreakfast());
            dayDiet.setSnack(generateMorningSnack());
            dayDiet.setLunch(generateLunch());
            dayDiet.setAfternoonSnack(generateAfternoonSnack());
            dayDiet.setDinner(generateDinner());
            dayDiets.add(dayDiet);
        }

        return dayDiets;
    }

    private List<MealEntity> generateBreakfast(){
        return generateOneServingMeal(MealTime.BREAKFAST, breakfastRecipes, remainingUniqueBreakfastRecipes);
    }

    private List<MealEntity> generateMorningSnack(){
        return generateOneServingMeal(MealTime.MORNING_SNACK, snackRecipes, remainingUniqueSnackRecipes);
    }

    private List<MealEntity> generateAfternoonSnack(){
        return generateOneServingMeal(MealTime.AFTERNOON_SNACK, snackRecipes, remainingUniqueSnackRecipes);
    }

    private List<MealEntity> generateDinner(){
        return generateOneServingMeal(MealTime.DINNER, mainDishRecipes, remainingUniqueMainDishRecipes);
    }

    private List<MealEntity> generateOneServingMeal(MealTime dish, List<Recipe> availableRecipes, List<Recipe> remainingUniqueRecipes){
        if(!parameters.getDishes().contains(dish) || availableRecipes.isEmpty()){
            return Collections.emptyList();
        }
        Recipe selectedRecipe = remainingUniqueRecipes.get(random.nextInt(remainingUniqueRecipes.size()));
        List<MealEntity> meals = new ArrayList<>(1);
        MealEntity lunchMeal = new MealEntity();
        lunchMeal.setServings(1f);
        lunchMeal.setRecipe(recipeRefService.toEntity(selectedRecipe));
        meals.add(lunchMeal);

        remainingUniqueRecipes.remove(selectedRecipe);
        if(remainingUniqueRecipes.isEmpty()){
            remainingUniqueRecipes.addAll(availableRecipes);
        }

        return meals;
    }

    private List<MealEntity> generateLunch(){
        if(!parameters.getDishes().contains(MealTime.LUNCH)){
            return Collections.emptyList();
        }

        if(!subsequentDay){
            recipe = remainingUniqueMainDishRecipes.get(random.nextInt(remainingUniqueMainDishRecipes.size()));
            List<RecipeRef> sideDishes = new ArrayList<>(recipe.getSideDishes());
            if(sideDishes.isEmpty()){
                sideDish = Optional.empty();
            }else if(sideDishes.size() == 1){
                sideDish = Optional.of(recipeService.getRecipeById(sideDishes.get(0).getId()));
            }else{
                RecipeRef sideDishRef = sideDishes.get(random.nextInt(sideDishes.size()));
                sideDish = Optional.of(recipeService.getRecipeById(sideDishRef.getId()));
            }
            remainingUniqueMainDishRecipes.remove(recipe);
            if(remainingUniqueMainDishRecipes.isEmpty()){
                remainingUniqueMainDishRecipes.addAll(mainDishRecipes);
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

    private boolean containsAny(Set<MealTime> dishes, MealTime... checkedDishes){
        for(MealTime checkedDish : checkedDishes){
            if(dishes.contains(checkedDish)){
                return true;
            }
        }
        return false;
    }

    private void initGeneration(final GeneratorParameters parameters){
        subsequentDay = false;
        random = new Random();
        // filter recipes for main dishes if necessary
        if(containsAny(parameters.getDishes(), MealTime.LUNCH, MealTime.DINNER)){
            RecipeFilter mainDishFilter = new RecipeFilter.Builder()
                    .setType(Set.of(RecipeType.MAIN_DISH))
                    .setCookbooks(parameters.getFilter().getCookbooks().orElse(null))
                    .build();
            mainDishRecipes = new ArrayList<>(recipeService.getFilteredRecipes(mainDishFilter));
            remainingUniqueMainDishRecipes = new ArrayList<>(mainDishRecipes);
        }
        // filter recipes for snacks if necessary
        if(containsAny(parameters.getDishes(), MealTime.MORNING_SNACK, MealTime.AFTERNOON_SNACK)){
            RecipeFilter snackDishFilter = new RecipeFilter.Builder()
                    .setType(Set.of(RecipeType.SNACK))
                    .setCookbooks(parameters.getFilter().getCookbooks().orElse(null))
                    .build();
            snackRecipes = new ArrayList<>(recipeService.getFilteredRecipes(snackDishFilter));
            remainingUniqueSnackRecipes = new ArrayList<>(snackRecipes);
        }
        // filter recipes for breakfast if necessary
        if(containsAny(parameters.getDishes(), MealTime.BREAKFAST)){
            RecipeFilter breakfastDishFilter = new RecipeFilter.Builder()
                    .setType(Set.of(RecipeType.BREAKFAST))
                    .setCookbooks(parameters.getFilter().getCookbooks().orElse(null))
                    .build();
            breakfastRecipes = new ArrayList<>(recipeService.getFilteredRecipes(breakfastDishFilter));
            remainingUniqueBreakfastRecipes = new ArrayList<>(breakfastRecipes);
        }
    }
}
