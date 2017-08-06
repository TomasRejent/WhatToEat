/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import com.google.common.collect.ImmutableSet;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeDao;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldRecipeType;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.RecipeOld;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RecipeService}.
 * @author Tomas Rejent
 */
public class RecipeServiceImpl implements RecipeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    private final RecipeDao recipeDao;

    public RecipeServiceImpl(final RecipeDao recipeDao) {
        LOGGER.debug("Creating Recipe service.");
        Validate.notNull(recipeDao);
        this.recipeDao = recipeDao;
    }

    @Override
    public RecipeOld getRecipeByKey(String recipeKey) {
        LOGGER.debug("Getting recipe by key: {}.", recipeKey);
        Validate.notNull(recipeKey);
        return recipeDao.read(recipeKey);
    }

    @Override
    public RecipeOld getRecipeByName(final String name) {
        LOGGER.debug("Getting recipe by name: {}.", name);
        Validate.notNull(name, "Recipe name cannot be null.");
        return recipeDao.getRecipeByName(name);
    }

    @Override
    public Set<RecipeOld> getAllSideDishes() {
        final List<RecipeOld> sideDishes = recipeDao.getRecipeByType(OldRecipeType.SIDE_DISH);
        return ImmutableSet.copyOf(sideDishes);
    }

    @Override
    public Set<RecipeOld> getAllRecipes() {
        LOGGER.debug("Reading all recipes.");
        final List<RecipeOld> recipeList = recipeDao.readAll();
        return ImmutableSet.copyOf(recipeList);
    }

    @Override
    public void addRecipe(final RecipeOld recipe) {
        LOGGER.debug("Adding new recipe: {}", recipe);
        Validate.notNull(recipe, "Cannot add null recipe.");
        recipeDao.create(recipe);
    }

    @Override
    public void updateRecipe(RecipeOld recipe) {
        LOGGER.debug("Updating recipe: {}", recipe);
        ParameterCheckUtils.checkNotNull(recipe, "Cannot createOrUpdate null recipe.");
        recipeDao.update(recipe);
    }

    @Override
    public void deleteRecipe(RecipeOld recipe) {
        LOGGER.debug("Deleting recipe: {}", recipe);
        ParameterCheckUtils.checkNotNull(recipe, "Cannot delete null recipe.");
        recipeDao.delete(recipe);
    }

    @Override
    public Set<String> getAllRecipeKeywords() {
        LOGGER.debug("Geting all keywords from recipes.");
        return recipeDao.getRecipeKeywords();
    }

    @Override
    public Set<String> getAllRecipeNames() {
        LOGGER.debug("Geting all names of recipes.");
        return recipeDao.readAll().stream().map(RecipeOld::getName).collect(Collectors.toSet());
    }
}
