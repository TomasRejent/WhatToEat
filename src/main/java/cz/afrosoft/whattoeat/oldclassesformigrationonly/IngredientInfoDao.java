/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import java.util.Set;

/**
 * Interface for {@link OldIngredient} entity DAO to provide Ingredient specific data services.
 * @author Tomas Rejent
 */
public interface IngredientInfoDao extends BaseDao<OldIngredient, String> {

    /**
     * @return (NotNull) (ReadOnly) Set of all ingredient keywords.
     */
    Set<String> getIngredientKeywords();

}
