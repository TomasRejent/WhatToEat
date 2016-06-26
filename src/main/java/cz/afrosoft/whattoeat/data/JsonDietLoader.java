/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import cz.afrosoft.whattoeat.data.util.ParameterCheckUtils;
import cz.afrosoft.whattoeat.logic.model.DayDiet;
import cz.afrosoft.whattoeat.logic.model.Diet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tomas Rejent
 */
public class JsonDietLoader implements DietLoader{

    private static final JsonDietLoader INSTANCE = new JsonDietLoader();

    private final Gson gson = new Gson();

    private JsonDietLoader() {
    }

    public static JsonDietLoader getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Diet> getAllDiets(File dietFile) throws FileNotFoundException, IOException {
        ParameterCheckUtils.checkNotNull(dietFile, "File from which diets should be loaded cannot be null.");

        List<Diet> dietList = loadWritableDietList(dietFile);
        return Collections.unmodifiableList(dietList);
    }

    @Override
    public void saveDiet(File dietFile, Diet diet) throws FileNotFoundException, IOException {
        ParameterCheckUtils.checkNotNull(dietFile, "File to which diet should be saved cannot be null.");
        ParameterCheckUtils.checkNotNull(diet, "There is no reason to save null diet.");

        List<Diet> dietList = loadWritableDietList(dietFile);
        dietList.add(diet);
        String serializedDiets = gson.toJson(dietList);

        FileWriter writter = new FileWriter(dietFile);
        writter.write(serializedDiets);
        writter.close();
    }

    private List<Diet> loadWritableDietList(File dietFile) throws FileNotFoundException, IOException{
        BufferedReader fileReader = new BufferedReader(new FileReader(dietFile));
        Diet[] diets = gson.fromJson(fileReader, Diet[].class);
        fileReader.close();
        if(diets == null){
            return new LinkedList<>();
        }else{
            return new LinkedList<>(Arrays.asList(diets));
        }
    }



}
