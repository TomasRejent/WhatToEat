package cz.afrosoft.whattoeat;

import cz.afrosoft.whattoeat.data.DataHolderService;
import cz.afrosoft.whattoeat.data.DataHolderServiceImpl;
import cz.afrosoft.whattoeat.logic.services.IngredientQuantityService;
import cz.afrosoft.whattoeat.logic.services.IngredientQuantityServiceImpl;
import cz.afrosoft.whattoeat.logic.services.PriceCalculatorService;
import cz.afrosoft.whattoeat.logic.services.PriceCalculatorServiceImpl;

/**
 *
 * @author Alexandra
 */
public final class ServiceHolder {
    
    private static final DataHolderService dataHolderService = new DataHolderServiceImpl();
    private static final IngredientQuantityService ingredientQuantityService = new IngredientQuantityServiceImpl(dataHolderService);
    private static final PriceCalculatorService priceCalculatorService = new PriceCalculatorServiceImpl(dataHolderService, ingredientQuantityService);
    
    public static DataHolderService getDataHolderService(){
        return dataHolderService;
    }

    public static PriceCalculatorService getPriceCalculatorService() {
        return priceCalculatorService;
    }

    public static IngredientQuantityService getIngredientQuantityService() {
        return ingredientQuantityService;
    }
    
}
