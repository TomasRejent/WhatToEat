/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;


import cz.afrosoft.whattoeat.logic.model.Diet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author afromanius
 */
public interface DietLoader {

    List<Diet> getAllDiets(File dietFile) throws FileNotFoundException, IOException;

    void saveDiet(File dietFile, Diet diet) throws FileNotFoundException, IOException;

}
