/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat;

import cz.afrosoft.whattoeat.data.DataHolderService;
import cz.afrosoft.whattoeat.data.DataHolderServiceImpl;
import cz.afrosoft.whattoeat.logic.services.PriceCalculatorService;
import cz.afrosoft.whattoeat.logic.services.PriceCalculatorServiceImpl;

/**
 *
 * @author Alexandra
 */
public final class ServiceHolder {
    
    private static final DataHolderService dataHolderService = new DataHolderServiceImpl();
    private static final PriceCalculatorService priceCalculatorService = new PriceCalculatorServiceImpl(dataHolderService);
    
    public static DataHolderService getDataHolderService(){
        return dataHolderService;
    }

    public static PriceCalculatorService getPriceCalculatorService() {
        return priceCalculatorService;
    }
    
    
    
    
    
}
