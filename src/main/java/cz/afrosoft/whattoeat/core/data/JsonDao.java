/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.core.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.afrosoft.whattoeat.core.ServiceHolder;
import cz.afrosoft.whattoeat.core.data.exception.DataLoadException;
import cz.afrosoft.whattoeat.core.data.exception.DataSaveException;
import cz.afrosoft.whattoeat.core.data.exception.NotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.afrosoft.whattoeat.core.logic.service.ConfigService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of basic DAO operations over JSON files as persistence medium.
 * @author Tomas Rejent
 */
public class JsonDao<T extends PersistentEntity<K>, K extends Serializable> implements BaseDao<T, K>{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDao.class);

    private final ConfigService configService;
    private final Gson gson;
    private final File storageFile;
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

        configService = ServiceHolder.getConfigService();
        this.storageFile = storageFile;
        this.clazz = clazz;
        this.gson = createGson();
    }

    /**
     * @return (NotNull) Creates gson instance based on config.
     */
    private Gson createGson(){
        final GsonBuilder gsonBuilder = new GsonBuilder();
        if(configService.getPrettyJson()){
            gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }

    /**
     * Creates entity in storage file specified when constructing this instance.
     * @param entity (NotNull) UUIDEntity which should be persisted.
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
     * @return (NotNull) UUIDEntity with specified key.
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
        throw new NotFoundException(String.format("UUIDEntity with key %s was not found in storage %s.", key, storageFile));
    }

    @Override
    public boolean exists(final K key) {
        LOGGER.debug("Checking existence of entity with key: {}.", key);
        Validate.notNull(key, "UUIDEntity key cannot be null.");
        final List<T> entities = readAllInternal();
        return entities.stream().anyMatch(entity -> entity.getKey().equals(key));
    }

    /**
     * Updates entity in storage file specified when constructing this instance.
     * @param entity (NotNull) UUIDEntity to update. UUIDEntity must already exist in storage.
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
     * @param entity (NotNull) UUIDEntity to delete. If it does not exist nothing happens.
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
        try(final Reader bufferedReader = createReaderWithCorrectEncoding(storageFile)){
            final T[] entityArray = gson.fromJson(bufferedReader, clazz);
            return new ArrayList<>(Arrays.asList(entityArray));
        }catch(IOException ex){
            throw new DataLoadException(String.format("Cannot read data from json file: %s.", storageFile), ex);
        }
    }

    protected void saveAllInternal(final List<T> entityList){
        Validate.notNull(entityList);
        try(final Writer writer = createWriterWithCorrectEncoding(storageFile)){
            final String serializedEntities = gson.toJson(entityList);
            writer.write(serializedEntities);
        }catch(IOException ex){
            throw new DataSaveException(String.format("Cannot save data to json file: %s.", storageFile), ex);
        }
    }

    private Reader createReaderWithCorrectEncoding(final File sourceFile) throws FileNotFoundException {
        final FileInputStream fileInputStream = new FileInputStream(sourceFile);
        final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, configService.getDataFileEncoding());
        return new BufferedReader(inputStreamReader);
    }

    private Writer createWriterWithCorrectEncoding(final File targetFile) throws FileNotFoundException {
        final FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        return new OutputStreamWriter(fileOutputStream, configService.getDataFileEncoding());
    }
}
