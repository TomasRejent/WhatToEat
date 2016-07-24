/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.generator;

import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import cz.afrosoft.whattoeat.logic.model.dto.GeneratorParameters;
import java.util.Collection;

/**
 * Interface representing generator which produces list of food from available recipes and specified parameters.
 * @author Tomas Rejent
 */
public interface Generator {

    String getId();

    String getName();

    Diet generate(Collection<Recipe> recipes, GeneratorParameters parameters);

}
