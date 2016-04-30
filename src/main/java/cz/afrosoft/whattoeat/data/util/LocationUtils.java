/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data.util;

import cz.afrosoft.whattoeat.data.exception.DataLoadException;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 *
 * @author Alexandra
 */
public final class LocationUtils {
    
    private static final String RECIPE_FILE_NAME = "recipes.txt";
    private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
    
    public static File getRecipeFile() throws DataLoadException{
        return getDataFile(RECIPE_FILE_NAME);
    }
    
    public static File getIngredientFile() throws DataLoadException{
        return getDataFile(INGREDIENTS_FILE_NAME);
    }
    
    private static File getDataFile(String name) throws DataLoadException{
        File baseLocaFile = getProgramLocation();
        File dataFile = new File(baseLocaFile, name);
        if(!dataFile.exists()){
            throw new DataLoadException(name + " file does not exist in location: " + dataFile);
        }
        
        if(!dataFile.canRead()){
            throw new DataLoadException(name + " file cannot be readed because of permissions.");
        }
        
        return dataFile;
    }
    
    private static File getProgramLocation() throws DataLoadException{
        ProtectionDomain protectionDomain = LocationUtils.class.getProtectionDomain();
        if(protectionDomain == null){
            throw new DataLoadException("Cannot get program location because protection domain is null.");
        }
        
        CodeSource codeSource = protectionDomain.getCodeSource();
        if(codeSource == null){
            throw new DataLoadException("Cannot get program location because code source null.");
        }
        
        String path;
        
        try {
            path = codeSource.getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            throw new DataLoadException("Cannot get program location because URI syntax.", ex);
        }
        
        //return new File(path).getParentFile();
        return new File("C:\\Users\\Alexandra\\Documents\\NetBeansProjects\\WhatToEat");
    }
    
    private LocationUtils() {
        throw new IllegalStateException("Cannot instantiate util class.");
    }
    
}
