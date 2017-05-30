/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.core.data.JsonDao;
import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of persistence service of JSON type for entity {@link Recipe}.
 * @author Tomas Rejent
 */
public class RecipeJsonDao extends JsonDao<Recipe, String> implements RecipeDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeJsonDao.class);

    public RecipeJsonDao() {
        super(LocationUtils.getRecipeFile(), Recipe[].class);
    }

    @Override
    public Recipe getRecipeByName(final String name) {
        Validate.notBlank(name);
        List<Recipe> allRecipes = readAll();
        List<Recipe> filteredRecipes = allRecipes.stream().filter((recipe -> name.equals(recipe.getName()))).collect(Collectors.toList());
        //TODO add check for list size
        return filteredRecipes.get(0);
    }

    @Override
    public List<Recipe> getRecipeByType(final RecipeType... types) {
        LOGGER.debug("Filtering recipes by recipe type: {}", (Object[]) types);
        Validate.notNull(types, "At leas one recipe type must be specified.");
        final Set<RecipeType> filteredTypes = EnumSet.copyOf(Arrays.asList(types));
        final List<Recipe> recipes = readAll();
        final List<Recipe> filteredRecipes = recipes.stream().filter(
                (recipe) -> !Collections.disjoint(filteredTypes, recipe.getRecipeTypes())
        ).collect(Collectors.toList());
        return Collections.unmodifiableList(filteredRecipes);
    }

    @Override
    public Set<String> getRecipeKeywords() {
        LOGGER.debug("Getting all recipe keywords.");
        final List<Recipe> recipes = readAll();
        final Set<String> keywordsSet = new HashSet<>();
        recipes.stream().forEach((recipe) -> {
            keywordsSet.addAll(recipe.getKeywords());
        });
        LOGGER.debug("Found {} keywords.", keywordsSet.size());
        return Collections.unmodifiableSet(keywordsSet);
    }


}
