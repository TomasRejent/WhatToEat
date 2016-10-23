/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.generator.logic.service;

import cz.afrosoft.whattoeat.diet.generator.logic.generator.Generator;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.generator.logic.model.GeneratorParameters;
import java.util.Collection;

/**
 *
 * @author Tomas Rejent
 */
public interface GeneratorService {

    Collection<Generator> getGenerators();

    Diet generateAndSaveDiet(Generator generator, GeneratorParameters parameters);

}
