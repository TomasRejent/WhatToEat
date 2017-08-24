/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.core.data.util.LocationUtils;

/**
 * Implementation of persistence service of JSON type for entity {@link BasicConversionInfo}.
 * @author Tomas Rejent
 */
public class BasicConversionInfoJsonDao extends JsonDao<BasicConversionInfo, String> implements BasicConversionInfoDao{

    public BasicConversionInfoJsonDao() {
        super(LocationUtils.getPieceConversionFile(), BasicConversionInfo[].class);
    }

}
