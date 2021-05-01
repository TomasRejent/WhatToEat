package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeRefService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.util.NumberUtils;
import cz.afrosoft.whattoeat.diet.generator.model.*;
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

import java.time.DayOfWeek;
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

    private static final float BREAKFAST_NUTRITION_PERCENTAGE = 0.2f;
    private static final float SNACK_NUTRITION_PERCENTAGE = 0.1f;
    private static final float LUNCH_NUTRITION_PERCENTAGE = 0.35f;
    private static final float AFTERNOON_SNACK_NUTRITION_PERCENTAGE = 0.1f;
    private static final float DINNER_NUTRITION_PERCENTAGE = 0.25f;

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
            dayDiet.setDay(day);
            generateDay(dayDiet, dayDiets);
            dayDiets.add(dayDiet);
        }

        return dayDiets;
    }

    /**
     * Removes this recipe from all existing pools so it is not used repeatedly.
     * @param recipe
     */
    private void removeFromPools(Recipe recipe){
        Validate.notNull(recipe);

        breakfastPool.removeRecipe(recipe);
        snackPool.removeRecipe(recipe);
        lunchPool.removeRecipe(recipe);
        afternoonSnackPool.removeRecipe(recipe);
        dinnerPool.removeRecipe(recipe);
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

    private boolean areNutritionFactsWithinRange(MealNutritionFacts nutritionFacts, float typeCoefficient){
        return Arrays.stream(NutritionFactType.values()).allMatch(type -> isNutritionFactWithinRange(nutritionFacts.getByType(type), parameters.getByType(type), typeCoefficient));
    }

    private boolean isNutritionFactWithinRange(Float mealNutritionFactValue, NutritionCriteria criteria, float typeCoefficient){
        if(mealNutritionFactValue == null) {
            return true;
        }
        return NumberUtils.isWithinRange(mealNutritionFactValue, criteria.getTargetAmount()*typeCoefficient, criteria.getAboveTolerance(), criteria.getBelowTolerance());
    }

    private Recipe pickRecipeWithinNutritionRange(RecipePool pool, float typeCoefficient){
        Optional<Recipe> matchingRecipe = pool.getAvailableRecipes().stream().filter(recipe -> {
            MealNutritionFacts nutritionFacts = nutritionFactsMap.get(recipe);
            return areNutritionFactsWithinRange(nutritionFacts, typeCoefficient);
        }).findAny();

        Recipe chosenRecipe = matchingRecipe.map(pool::takeRecipe).orElse(pool.takeRandom());
        if(chosenRecipe != null){
            removeFromPools(chosenRecipe);
        }
        return chosenRecipe;
    }

    private LunchRecipes pickLunchRecipesWithinNutritionRange(){
        Recipe lunchRecipe = null;
        Recipe sideDishRecipe = null;
        for(Recipe checkedLunchRecipe : lunchPool.getAvailableRecipes()){
            Set<RecipeRef> sideDishes = checkedLunchRecipe.getSideDishes();
            MealNutritionFacts lunchNutritionFacts = nutritionFactsMap.get(checkedLunchRecipe);
            if(sideDishes.isEmpty()){
                if(areNutritionFactsWithinRange(lunchNutritionFacts, LUNCH_NUTRITION_PERCENTAGE)){
                    lunchRecipe = checkedLunchRecipe;
                    break;
                }
            } else {
                for(RecipeRef sideDishRef : sideDishes){
                    Recipe checkedSideDishRecipe = recipeService.getRecipeById(sideDishRef.getId());
                    if(!nutritionFactsMap.containsKey(checkedSideDishRecipe)){
                        nutritionFactsMap.put(checkedSideDishRecipe, nutritionFactsService.getMealNutritionFacts(checkedSideDishRecipe, DEFAULT_SERVINGS));
                    }
                    MealNutritionFacts sideDishNutritionFacts = nutritionFactsMap.get(checkedSideDishRecipe);
                    if(areNutritionFactsWithinRange(MealNutritionFacts.add(lunchNutritionFacts, sideDishNutritionFacts), LUNCH_NUTRITION_PERCENTAGE)){
                        lunchRecipe = checkedLunchRecipe;
                        sideDishRecipe = checkedSideDishRecipe;
                        break;
                    }
                }
                if(lunchRecipe != null){
                    break;
                }
            }
        }

        if(lunchRecipe == null){
            lunchRecipe = lunchPool.takeRandom();
            sideDishRecipe = getSideDishIfDefined(lunchRecipe).map(recipeRef -> recipeService.getRecipeById(recipeRef.getId())).orElse(null);
        }
        removeFromPools(lunchRecipe);

        return new LunchRecipes()
            .setLunch(lunchRecipe)
            .setSideDish(sideDishRecipe);
    }

    private void generateDay(DayDietEntity dayDiet, List<DayDietEntity> allGeneratedDays){
        DayOfWeek dayOfWeek = dayDiet.getDay().getDayOfWeek();
        if(dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.MONDAY)){// Sunday is day with one-day lunch, this is specific for personal usage and will be moved to config later
            this.subsequentDay = false;
        }

        if(this.generateLunch && (!this.lunchPool.isEmpty() || this.subsequentDay)){
            if(this.subsequentDay){
                dayDiet.setLunch(
                        allGeneratedDays.get(allGeneratedDays.size() - 1).getLunch().stream().map(this::copyMealEntity).collect(Collectors.toList())
                );
            } else {
                LunchRecipes lunchRecipes = pickLunchRecipesWithinNutritionRange();
                dayDiet.setLunch(lunchRecipes.getLunchRecipes().stream().map(this::createMealEntity).collect(Collectors.toList()));
            }
        }

        if(this.generateBreakfast && !this.breakfastPool.isEmpty()){
            dayDiet.setBreakfast(List.of(createMealEntity(pickRecipeWithinNutritionRange(breakfastPool, BREAKFAST_NUTRITION_PERCENTAGE))));
        }

        if(this.generateSnack && !this.snackPool.isEmpty()){
            dayDiet.setSnack(List.of(createMealEntity(pickRecipeWithinNutritionRange(snackPool, SNACK_NUTRITION_PERCENTAGE))));
        }

        if(this.generateAfternoonSnack && !this.afternoonSnackPool.isEmpty()){
            dayDiet.setAfternoonSnack(List.of(createMealEntity(pickRecipeWithinNutritionRange(afternoonSnackPool, AFTERNOON_SNACK_NUTRITION_PERCENTAGE))));
        }

        if(this.generateDinner && !this.dinnerPool.isEmpty()){
            dayDiet.setDinner(List.of(createMealEntity(pickRecipeWithinNutritionRange(dinnerPool, DINNER_NUTRITION_PERCENTAGE))));
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

        this.nutritionFactsMap = getNutritionFactsForRecipes();

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
        LocalDate newDietFrom = parameters.getFrom();
        LocalDate excludeLimit = newDietFrom.minusDays(RECIPE_EXCLUSION_TIME_LIMIT);
        return dietService.getAllDiets()
                .stream()
                .filter((diet -> parameters.getUser().equals(diet.getUser()) && isInInterval(diet, excludeLimit, newDietFrom)))
                .flatMap(diet -> dietService.getDietMealsInInterval(diet, excludeLimit, newDietFrom).stream())
                .filter(meal -> meal.getRecipe() != null) // filtering for case where previous diets contains ingredients in meals instead of recipes
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

    private class LunchRecipes{

        private Optional<Recipe> lunch = Optional.empty();
        private Optional<Recipe> sideDish = Optional.empty();
        private Optional<Recipe> soup = Optional.empty();

        public LunchRecipes setLunch(Recipe lunch){
            this.lunch = Optional.ofNullable(lunch);
            return this;
        }

        public LunchRecipes setSideDish(Recipe sideDish){
            this.sideDish = Optional.ofNullable(sideDish);
            return this;
        }

        public LunchRecipes setSoup(Recipe soup){
            this.soup = Optional.ofNullable(soup);
            return this;
        }

        public List<Recipe> getLunchRecipes(){
            ArrayList<Recipe> recipes = new ArrayList<>(4);
            this.lunch.ifPresent(recipes::add);
            this.sideDish.ifPresent(recipes::add);
            this.soup.ifPresent(recipes::add);
            return recipes;
        }

    }
}
