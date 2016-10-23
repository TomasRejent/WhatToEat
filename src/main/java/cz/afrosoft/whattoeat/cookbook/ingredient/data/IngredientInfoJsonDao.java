/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.data;

import cz.afrosoft.whattoeat.data.JsonDao;
import cz.afrosoft.whattoeat.data.util.LocationUtils;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import java.io.File;

/**
 * Implementation of persistence service of JSON type for entity {@link IngredientInfo}.
 * @author Tomas Rejent
 */
public class IngredientInfoJsonDao extends JsonDao<IngredientInfo, String> implements IngredientInfoDao{

    public IngredientInfoJsonDao() {
        super(LocationUtils.getIngredientFile(), IngredientInfo[].class);
    }
}
