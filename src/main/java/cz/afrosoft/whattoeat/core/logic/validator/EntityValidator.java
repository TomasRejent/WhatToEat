package cz.afrosoft.whattoeat.core.logic.validator;


import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tomas Rejent
 */
public interface EntityValidator<T> {

    boolean isValid(T entity);

    Map<String, String> validate(T entity);
}
