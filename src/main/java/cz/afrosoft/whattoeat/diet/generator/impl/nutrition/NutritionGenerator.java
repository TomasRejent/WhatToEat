package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.diet.generator.model.Generator;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.data.entity.MealEntity;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
@Component
public class NutritionGenerator implements Generator<NutritionGeneratorParams> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NutritionGenerator.class);

    /**
     * Number of days which must past before recipe can be used again in diet.
     */
    private static final int RECIPE_EXCLUSION_TIME_LIMIT = 14;
    private static final float DEFAULT_SERVINGS = 1;

    private NutritionGeneratorParams parameters;

    private RecipePool breakfastPool;
    private RecipePool snackPool;
    private RecipePool lunchPool;
    private RecipePool afternoonSnackPool;
    private RecipePool dinnerPool;
    private Map<Recipe, MealNutritionFacts> nutritionFactsMap;

    private boolean generateBreakfast;
    private boolean generateSnack;
    private boolean generateLunch;
    private boolean generateAfternoonSnack;
    private boolean generateDinner;
    private boolean subsequentDay = false;
    private Random random = new Random();

    @Autowired
    private NutritionFactsService nutritionFactsService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRefService recipeRefService;
    @Autowired
    private DietService dietService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public GeneratorType getType() {
        return GeneratorType.NUTRITION_SALVATION;
    }

    @Override
    public GeneratorGui<NutritionGeneratorParams> getGui() {
        return applicationContext.getBean(NutritionGeneratorGui.class);
    }

    @Override
    public List<DayDietEntity> generate(final NutritionGeneratorParams parameters) {
        Validate.notNull(parameters);
        this.parameters = parameters;

        initGeneration();

        List<DayDietEntity> dayDiets = new LinkedList<>();
        for (LocalDate day = parameters.getFrom(); !day.isAfter(parameters.getTo()); day = day.plusDays(1)) {
            DayDietEntity dayDiet = new DayDietEntity();
            generateDay(dayDiet, dayDiets);
            dayDiet.setDay(day);
            dayDiets.add(dayDiet);
        }

        return dayDiets;
    }

    private MealEntity copyMealEntity(MealEntity mealEntity){
        return new MealEntity()
                .setServings(mealEntity.getServings())
                .setRecipe(mealEntity.getRecipe());
    }

    private Optional<RecipeRef> getSideDishIfDefined(Recipe recipe){
        List<RecipeRef> sideDishes = new ArrayList<>(recipe.getSideDishes());
        Optional<RecipeRef> sideDish;
        if(sideDishes.isEmpty()){
            sideDish = Optional.empty();
        }else if(sideDishes.size() == 1){
            sideDish = Optional.of(recipeService.getRecipeById(sideDishes.get(0).getId()));
        }else{
            RecipeRef sideDishRef = sideDishes.get(random.nextInt(sideDishes.size()));
            sideDish = Optional.of(recipeService.getRecipeById(sideDishRef.getId()));
        }
        return sideDish;
    }

    private void generateDay(DayDietEntity dayDiet, List<DayDietEntity> allGeneratedDays){
        if(this.generateLunch && (!this.lunchPool.isEmpty() || this.subsequentDay)){
            if(this.subsequentDay){
                dayDiet.setLunch(
                        allGeneratedDays.get(allGeneratedDays.size() - 1).getLunch().stream().map(this::copyMealEntity).collect(Collectors.toList())
                );
            } else {
                Recipe recipe = lunchPool.takeRandom();
                Optional<RecipeRef> sideDish = getSideDishIfDefined(recipe);
                List<RecipeRef> lunchRecipes = new ArrayList<>();
                lunchRecipes.add(recipe);
                sideDish.ifPresent(lunchRecipes::add);
                dayDiet.setLunch(lunchRecipes.stream().map(this::createMealEntity).collect(Collectors.toList()));
            }
        }

        if(this.generateBreakfast && !this.breakfastPool.isEmpty()){
            dayDiet.setBreakfast(List.of(createMealEntity(breakfastPool.takeRandom())));
        }

        if(this.generateSnack && !this.snackPool.isEmpty()){
            dayDiet.setSnack(List.of(createMealEntity(snackPool.takeRandom())));
        }

        if(this.generateAfternoonSnack && !this.afternoonSnackPool.isEmpty()){
            dayDiet.setAfternoonSnack(List.of(createMealEntity(afternoonSnackPool.takeRandom())));
        }

        if(this.generateDinner && !this.dinnerPool.isEmpty()){
            dayDiet.setDinner(List.of(createMealEntity(dinnerPool.takeRandom())));
        }

        this.subsequentDay = !this.subsequentDay;
    }

    private MealEntity createMealEntity(RecipeRef recipe){
        return new MealEntity()
                .setServings(DEFAULT_SERVINGS)
                .setRecipe(recipeRefService.toEntity(recipe));
    }

    private void initGeneration(){
        this.subsequentDay = false;
        Set<Recipe> recipesFromPreviousDiets = getRecipesFromPreviousDiets();
        this.breakfastPool = createPool(parameters.getBreakfastFilter(), recipesFromPreviousDiets);
        this.snackPool = createPool(parameters.getSnackFilter(), recipesFromPreviousDiets);
        this.lunchPool = createPool(parameters.getLunchFilter(), recipesFromPreviousDiets);
        this.afternoonSnackPool = createPool(parameters.getAfternoonSnackFilter(), recipesFromPreviousDiets);
        this.dinnerPool = createPool(parameters.getDinnerFilter(), recipesFromPreviousDiets);
        // TODO - enable when nutrition facts are used. Commented for performance now. this.nutritionFactsMap = getNutritionFactsForRecipes();

        this.generateBreakfast = parameters.getDishes().contains(MealTime.BREAKFAST);
        this.generateSnack = parameters.getDishes().contains(MealTime.MORNING_SNACK);
        this.generateLunch = parameters.getDishes().contains(MealTime.LUNCH);
        this.generateAfternoonSnack = parameters.getDishes().contains(MealTime.AFTERNOON_SNACK);
        this.generateDinner = parameters.getDishes().contains(MealTime.DINNER);
    }

    private Map<Recipe, MealNutritionFacts> getNutritionFactsForRecipes(){
        Map<Recipe, MealNutritionFacts> nutritionFactsMap = new HashMap<>();
        Set<Recipe> allRecipes = new HashSet<>();
        allRecipes.addAll(breakfastPool.getCompletePool());
        allRecipes.addAll(snackPool.getCompletePool());
        allRecipes.addAll(lunchPool.getCompletePool());
        allRecipes.addAll(afternoonSnackPool.getCompletePool());
        allRecipes.addAll(dinnerPool.getCompletePool());
        allRecipes.forEach(recipe -> {
            nutritionFactsMap.put(recipe, nutritionFactsService.getMealNutritionFacts(recipe, 1f));
        });
        return nutritionFactsMap;
    }

    private Set<Recipe> getRecipesFromPreviousDiets(){
        LocalDate now = LocalDate.now();
        LocalDate excludeLimit = now.minusDays(RECIPE_EXCLUSION_TIME_LIMIT);
        return dietService.getAllDiets()
                .stream()
                .filter((diet -> parameters.getUser().equals(diet.getUser()) && isInInterval(diet, excludeLimit, now)))
                .flatMap(diet -> dietService.getDietMealsInInterval(diet, excludeLimit, now).stream())
                .map(meal -> recipeService.getRecipeById(meal.getRecipe().getId()))
                .collect(Collectors.toSet());
    }

    private boolean isInInterval(Diet diet, LocalDate from, LocalDate to){
        LocalDate dietFrom = diet.getFrom();
        LocalDate dietTo = diet.getTo();
        return !(dietFrom.isAfter(to) || dietTo.isBefore(from));
    }

    private RecipePool createPool(RecipeFilter filter, Set<Recipe> recipesFromPreviousDiets){
        Set<Recipe> filteredRecipes = recipeService.getFilteredRecipes(filter);
        return new RecipePool(filteredRecipes, recipesFromPreviousDiets);
    }
}
