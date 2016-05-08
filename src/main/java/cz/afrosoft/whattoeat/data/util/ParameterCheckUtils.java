/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.data.util;

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

}
