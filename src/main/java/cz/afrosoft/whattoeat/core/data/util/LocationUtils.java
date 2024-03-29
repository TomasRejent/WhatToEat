/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.core.data.util;

import cz.afrosoft.whattoeat.core.data.exception.DataLoadException;
import java.io.File;
import java.io.IOException;
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
    private static final String DIETS_FILE_NAME = "diets.txt";
    private static final String PIECE_CONVERSION_INFO_FILE_NAME = "piece_conversion_info.txt";
    private static final String CONFIG_FILE_NAME = "config.properties";
    
    public static File getRecipeFile() throws DataLoadException{
        return getDataFileIfExists(RECIPE_FILE_NAME);
    }
    
    public static File getIngredientFile() throws DataLoadException{
        return getDataFileIfExists(INGREDIENTS_FILE_NAME);
    }

    public static File getDietFile() throws DataLoadException{
        return getDataFile(DIETS_FILE_NAME);
    }

    public static File getPieceConversionFile() throws DataLoadException{
        return getDataFile(PIECE_CONVERSION_INFO_FILE_NAME);
    }

    public static File getConfigFile() throws DataLoadException{
        return getDataFileIfExists(CONFIG_FILE_NAME);
    }
    
    private static File getDataFileIfExists(String name) throws DataLoadException{
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

    private static File getDataFile(String name) throws DataLoadException{
        File baseLocaFile = getProgramLocation();
        File dataFile = new File(baseLocaFile, name);
        if(!dataFile.exists()){
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                throw new DataLoadException(String.format("Cannot create data file ", dataFile), ex);
            }
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
        //Temp soultion because NetBeans is running jar in target folder and config files get deleted
        return new File(path).getParentFile().getParentFile();
    }
    
    private LocationUtils() {
        throw new IllegalStateException("Cannot instantiate util class.");
    }
    
}
