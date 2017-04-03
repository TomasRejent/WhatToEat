/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.core.data;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for DAO implementations to separate business and data layers.
 * @author Tomas Rejent
 */
public interface BaseDao<T extends PersistentEntity<K>, K extends Serializable> {

    /**
     * Persists specified entity.
     * @param entity (NotNull) Entity to persist.
     * @return Persisted entity with filled key.
     */
    T create(T entity);

    /**
     * Reads persisted entity.
     * @param key (NotNull) Key of entity which should be read.
     * @return Entity with specified key.
     */
    T read(K key);

    /**
     * Check if entity with specified key exist or not.
     * @param key (NotNull) Key of entity.
     * @return True if already exist. False otherwise.
     */
    boolean exists(K key);

    /**
     * Update persisted entity.
     * @param entity (NotNull) Entity with updated values.
     */
    void update(T entity);

    /**
     * Delete persisted entity.
     * @param entity (NotNull) Entity to delete.
     */
    void delete(T entity);

    /**
     * Reads all persisted entities.
     * @return List of entities.
     */
    List<T> readAll();
}
