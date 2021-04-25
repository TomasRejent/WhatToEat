package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import org.apache.commons.collections4.SetUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
public class RecipePool {

    private Set<Recipe> completePool;
    private Set<Recipe> excludedByPreviousDiets;
    private Set<Recipe> usedRecipes;
    private Set<Recipe> availablePool;
    private Random random = new Random();

    public RecipePool(final Set<Recipe> completePool, final Set<Recipe> excludedByPreviousDiets) {
        this.completePool = completePool;
        this.excludedByPreviousDiets = excludedByPreviousDiets;
        this.availablePool = new HashSet<>(SetUtils.difference(completePool, excludedByPreviousDiets));
        this.usedRecipes = new HashSet<>();
    }

    public Set<Recipe> getCompletePool() {
        return completePool;
    }

    public Recipe takeRandom(){
        int index = random.nextInt(availablePool.size());
        Recipe takenRecipe = availablePool.toArray(new Recipe[0])[index];
        availablePool.remove(takenRecipe);
        usedRecipes.add(takenRecipe);
        return takenRecipe;
    }

    public void removeRecipe(Recipe recipe){
        if(availablePool.contains(recipe)){
            availablePool.remove(recipe);
            usedRecipes.add(recipe);
        }
    }

    public Recipe takeRecipe(Recipe recipe){
        if(!availablePool.contains(recipe)){
            throw new IllegalArgumentException("Specified recipe is not present in available recipe pool: " + recipe);
        }

        availablePool.remove(recipe);
        usedRecipes.add(recipe);
        return recipe;
    }

    public Set<Recipe> getAvailableRecipes(){
        return Collections.unmodifiableSet(this.availablePool);
    }

    public boolean isEmpty(){
        return availablePool.isEmpty();
    }
}
