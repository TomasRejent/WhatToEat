/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.data.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Tomas Rejent
 */
public final class ParameterCheckUtils {

    private ParameterCheckUtils() {
        throw new IllegalStateException();
    }

    public static void checkNotNull(Object object, String message){
        if(object == null){
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNull(Object object, String message, Class<? extends RuntimeException> exceptionClass){
        if(object == null){
            try {
                final Constructor constructor = exceptionClass.getConstructor(String.class);
                throw (RuntimeException) constructor.newInstance(message);
            } catch (NoSuchMethodException ex) {
                throw new IllegalArgumentException("Exception class " + exceptionClass + " does not provide constructor for exception with message.");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new IllegalStateException("Exception class " + exceptionClass + " creation failed.");
            }
        }
    }

}
