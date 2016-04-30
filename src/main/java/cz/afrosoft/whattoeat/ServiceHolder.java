/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat;

import cz.afrosoft.whattoeat.data.DataHolderService;
import cz.afrosoft.whattoeat.data.DataHolderServiceImpl;

/**
 *
 * @author Alexandra
 */
public final class ServiceHolder {
    
    private static final DataHolderService dataHolderService = new DataHolderServiceImpl();
    
    public static DataHolderService getDataHolderService(){
        return dataHolderService;
    }
    
}
