/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.data;

import java.io.Serializable;

/**
 * Interface to indicate that entity can be persisted. Such a entity must have a key to identify itself which is serializable.
 * @param <K> Type of entity key. Must be serializable.
 * @author Tomas Rejent
 */
public interface PersistentEntity<K extends Serializable> {

    /**
     * @return Gets key of entity. If it is null then entity is not persisted.
     */
    K getKey();

}
