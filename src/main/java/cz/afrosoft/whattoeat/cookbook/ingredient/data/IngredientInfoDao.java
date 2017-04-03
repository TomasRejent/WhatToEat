/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.data;

import cz.afrosoft.whattoeat.core.data.BaseDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import java.util.Set;

/**
 * Interface for {@link IngredientInfo} entity DAO to provide IngredientInfo specific data services.
 * @author Tomas Rejent
 */
public interface IngredientInfoDao extends BaseDao<IngredientInfo, String>{

    /**
     * @return (NotNull) (ReadOnly) Set of all ingredient keywords.
     */
    Set<String> getIngredientKeywords();

}
