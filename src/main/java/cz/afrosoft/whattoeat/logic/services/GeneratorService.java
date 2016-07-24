/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.services;

import cz.afrosoft.whattoeat.logic.generator.Generator;
import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.dto.GeneratorParameters;
import java.util.Collection;

/**
 *
 * @author Tomas Rejent
 */
public interface GeneratorService {

    Collection<Generator> getGenerators();

    Diet generateAndSaveDiet(Generator generator, GeneratorParameters parameters);

}
