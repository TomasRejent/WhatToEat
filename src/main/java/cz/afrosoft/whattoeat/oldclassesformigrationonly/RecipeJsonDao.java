/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of persistence service of JSON type for entity {@link RecipeOld}.
 * @author Tomas Rejent
 */
public class RecipeJsonDao extends JsonDao<RecipeOld, String> implements RecipeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeJsonDao.class);

    public RecipeJsonDao() {
        super(LocationUtils.getRecipeFile(), RecipeOld[].class);
    }

    @Override
    public RecipeOld getRecipeByName(final String name) {
        Validate.notBlank(name);
        List<RecipeOld> allRecipes = readAll();
        List<RecipeOld> filteredRecipes = allRecipes.stream().filter((recipe -> name.equals(recipe.getName()))).collect(Collectors.toList());
        //TODO add check for list size
        return filteredRecipes.get(0);
    }

    @Override
    public List<RecipeOld> getRecipeByType(final OldRecipeType... types) {
        LOGGER.debug("Filtering recipes by recipe type: {}", (Object[]) types);
        Validate.notNull(types, "At leas one recipe type must be specified.");
        final Set<OldRecipeType> filteredTypes = EnumSet.copyOf(Arrays.asList(types));
        final List<RecipeOld> recipes = readAll();
        final List<RecipeOld> filteredRecipes = recipes.stream().filter(
                (recipe) -> !Collections.disjoint(filteredTypes, recipe.getRecipeTypes())
        ).collect(Collectors.toList());
        return Collections.unmodifiableList(filteredRecipes);
    }

    @Override
    public Set<String> getRecipeKeywords() {
        LOGGER.debug("Getting all recipe keywords.");
        final List<RecipeOld> recipes = readAll();
        final Set<String> keywordsSet = new HashSet<>();
        recipes.stream().forEach((recipe) -> {
            keywordsSet.addAll(recipe.getKeywords());
        });
        LOGGER.debug("Found {} keywords.", keywordsSet.size());
        return Collections.unmodifiableSet(keywordsSet);
    }


}
