/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.generator.logic.generator;

import cz.afrosoft.whattoeat.diet.generator.logic.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.RecipeOld;

import java.util.Collection;

/**
 * Interface representing generator which produces list of food from available recipes and specified parameters.
 * @author Tomas Rejent
 */
public interface Generator {

    String getId();

    String getName();

    Diet generate(Collection<RecipeOld> recipes, GeneratorParameters parameters);

}
