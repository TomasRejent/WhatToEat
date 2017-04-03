/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.data;

import cz.afrosoft.whattoeat.core.data.JsonDao;
import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;

/**
 * Implementation of persistence service of JSON type for entity {@link Diet}.
 * @author Tomas Rejent
 */
public class DietJsonDao extends JsonDao<Diet, String> implements DietDao{

    public DietJsonDao() {
        super(LocationUtils.getDietFile(), Diet[].class);
    }
}
