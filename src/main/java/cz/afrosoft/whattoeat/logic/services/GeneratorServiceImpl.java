/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.logic.services;

import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;

import cz.afrosoft.whattoeat.data.DataHolderService;
import cz.afrosoft.whattoeat.logic.generator.Generator;
import cz.afrosoft.whattoeat.logic.generator.RandomGenerator;
import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import cz.afrosoft.whattoeat.logic.model.dto.GeneratorParameters;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tomas Rejent
 */
public class GeneratorServiceImpl implements GeneratorService{

    private final DataHolderService dataHolderService;

    private final Set<Generator> generators = new HashSet<>();

    public GeneratorServiceImpl(final DataHolderService dataHolderService) {
        checkNotNull(dataHolderService, "Data holder service cannot be null.");
        this.dataHolderService = dataHolderService;
        
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

        final Collection<Recipe> recipes = dataHolderService.getRecipes();
        final Diet diet = generator.generate(recipes, parameters);
        dataHolderService.addDiet(diet);
        return diet;
    }

    private void registerGenerators(){
        generators.add(new RandomGenerator());
    }

}
