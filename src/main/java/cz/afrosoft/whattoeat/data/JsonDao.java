/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.data;

import com.google.gson.Gson;
import cz.afrosoft.whattoeat.data.exception.DataLoadException;
import cz.afrosoft.whattoeat.data.exception.DataSaveException;
import cz.afrosoft.whattoeat.data.exception.NotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of basic DAO operations over JSON files as persistence medium.
 * @author Tomas Rejent
 */
public class JsonDao<T extends PersistentEntity<K>, K extends Serializable> implements BaseDao<T, K>{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDao.class);

    private final File storageFile;
    private final Gson gson;
    private final Class<T[]> clazz;

    /**
     * Creates new JsonDao which handles basic DAO operations.
     * @param storageFile (NotNull) File in which entities of specified type are stored. Each entity type must have separate file else it will result in error.
     * @param clazz (NotNull) Array type of persisted entity. This is used for GSON so it know how to parse JSON file.
     */
    public JsonDao(final File storageFile, final Class<T[]> clazz) {
        LOGGER.debug("Creating JsonDao for type <{}> and storage file <{}>.", clazz, storageFile);
        Validate.notNull(storageFile, "JSon storage file cannot be null.");
        Validate.notNull(clazz, "Clazz must be specified for proper type casting.");

        this.storageFile = storageFile;
        this.clazz = clazz;
        gson = new Gson();
    }

    /**
     * Creates entity in storage file specified when constructing this instance.
     * @param entity (NotNull) Entity which should be persisted.
     * @return (NotNull) Persisted entity with filled key.
     * @throws DataLoadException When read fails for storage file.
     * @throws DataSaveException When entity already exist or if write to storage file fails.
     */
    @Override
    public T create(final T entity) {
        LOGGER.debug("Creating entity <{}> in storage <{}>.", entity, storageFile);
        Validate.notNull(entity);
        final List<T> entities = readAllInternal();
        if(entities.contains(entity)){
            throw new DataSaveException(String.format("Cannot create entity %s because it already exist in storage %s.", entity, storageFile));
        }
        entities.add(entity);
        saveAllInternal(entities);
        return entity;
    }

    /**
     * Reads entity from storage file specified when constructing this instance.
     * @param key (NotNull) Key of entity which should be loaded.
     * @return (NotNull) Entity with specified key.
     * @throws DataLoadException If storage file cannot be read.
     * @throws NotFoundException  If entity with given key does not exist.
     */
    @Override
    public T read(final K key) {
        LOGGER.debug("Reading entity with key <{}> from storage <{}>.", key, storageFile);
        Validate.notNull(key);
        final List<T> entities = readAllInternal();
        for(T entity : entities){
            if(key.equals(entity.getKey())){
                return entity;
            }
        }
        throw new NotFoundException(String.format("Entity with key %s was not found in storage %s.", key, storageFile));
    }

    /**
     * Updates entity in storage file specified when constructing this instance.
     * @param entity (NotNull) Entity to update. Entity must already exist in storage.
     * @throws DataLoadException If storage file cannot be read.
     * @throws DataSaveException If entity cannot be updated in storage.
     */
    @Override
    public void update(final T entity) {
        LOGGER.debug("Updating entity to <{}> in storage <{}>.", entity, storageFile);
        Validate.notNull(entity);
        final List<T> entities = readAllInternal();
        if(entities.contains(entity)){
            entities.set(entities.indexOf(entity), entity);
            saveAllInternal(entities);
        }else{
            throw new DataSaveException(String.format("Cannot update entity %s because it does not exist in storage %s. Use create first.", entity, storageFile));
        }
    }

    /**
     * Delete entity from storage file specified when constructing this instance.
     * @param entity (NotNull) Entity to delete. If it does not exist nothing happens.
     * @throws DataLoadException If storage file cannot be read.
     * @throws DataSaveException If entity cannot be deleted from storage.
     */
    @Override
    public void delete(final T entity) {
        LOGGER.debug("Deleting entity <{}> from storage <{}>.", entity, storageFile);
        Validate.notNull(entity);
        final List<T> entities = readAllInternal();
        entities.remove(entity);
        saveAllInternal(entities);
    }

    /**
     * Reads all entities from storage file specified when constructing this instance.
     * @return (NotNull)(ReadOnly) List of entities.
     */
    @Override
    public List<T> readAll() {
        LOGGER.debug("Reading all entities from storage <{}>.", storageFile);
        return Collections.unmodifiableList(readAllInternal());
    }

    protected List<T> readAllInternal(){
        try(final BufferedReader bufferedReader = new BufferedReader(new FileReader(storageFile))){
            final T[] entityArray = gson.fromJson(bufferedReader, clazz);
            return Arrays.asList(entityArray);
        }catch(IOException ex){
            throw new DataLoadException(String.format("Cannot read data from json file: %s.", storageFile), ex);
        }
    }

    protected void saveAllInternal(final List<T> entityList){
        Validate.notNull(entityList);
        try(final FileWriter writter = new FileWriter(storageFile)){
            final String serializedEntities = gson.toJson(entityList);
            writter.write(serializedEntities);
        }catch(IOException ex){
            throw new DataSaveException(String.format("Cannot save data to json file: %s.", storageFile), ex);
        }
    }
}