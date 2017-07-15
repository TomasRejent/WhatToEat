/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.generator.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeDao;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeOld;
import cz.afrosoft.whattoeat.diet.data.DietDao;
import cz.afrosoft.whattoeat.diet.generator.logic.generator.Generator;
import cz.afrosoft.whattoeat.diet.generator.logic.generator.RandomGenerator;
import cz.afrosoft.whattoeat.diet.generator.logic.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

/**
 *
 * @author Tomas Rejent
 */
public class GeneratorServiceImpl implements GeneratorService{

    private final RecipeDao recipeDao;
    private final DietDao dietDao;

    private final Set<Generator> generators = new HashSet<>();

    public GeneratorServiceImpl(final RecipeDao recipeDao, final DietDao dietDao) {
        
        this.recipeDao = recipeDao;
        this.dietDao = dietDao;
        registerGenerators();
    }
    
    @Override
    public Collection<Generator> getGenerators() {
        return Collections.unmodifiableSet(generators);
    }

    @Override
    public Diet generateAndSaveDiet(Generator generator, GeneratorParameters parameters) {
        checkNotNull(generator, "Generator cannot be null.");
        checkNotNull(parameters, "Generator parameters cannot be null.");

        final Collection<RecipeOld> recipes = recipeDao.readAll();
        final Diet diet = generator.generate(recipes, parameters);
        dietDao.create(diet);
        return diet;
    }

    private void registerGenerators(){
        generators.add(new RandomGenerator());
    }

}
