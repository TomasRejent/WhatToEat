package cz.afrosoft.whattoeat.core;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.BasicConversionInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.BasicConversionInfoJsonDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.IngredientInfoJsonDao;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientInfoService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientInfoServiceImpl;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeDao;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeJsonDao;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeServiceImpl;
import cz.afrosoft.whattoeat.core.logic.service.ConfigService;
import cz.afrosoft.whattoeat.core.logic.service.ConfigServiceImpl;
import cz.afrosoft.whattoeat.core.logic.service.UUIDService;
import cz.afrosoft.whattoeat.core.logic.service.UuidServiceImpl;
import cz.afrosoft.whattoeat.diet.data.DietDao;
import cz.afrosoft.whattoeat.diet.data.DietJsonDao;
import cz.afrosoft.whattoeat.diet.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.logic.service.DietServiceImpl;
import cz.afrosoft.whattoeat.diet.generator.logic.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.generator.logic.service.GeneratorServiceImpl;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityServiceImpl;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.PieceConversionService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.PieceConversionServiceImpl;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.PriceCalculatorService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.PriceCalculatorServiceImpl;

/**
 * Class which manages creation of services and provides static access to them.
 * @author Tomas Rejent
 */
public final class ServiceHolder {

    /* Core services */
    private static final ConfigService CONFIG_SERVICE = ConfigServiceImpl.getInstance();
    private static final UUIDService UUID_SERVICE = new UuidServiceImpl();

    /* DAO Services.*/
    private static final RecipeDao RECIPE_DAO = new RecipeJsonDao();
    private static final IngredientInfoDao INGREDIENT_INFO_DAO = new IngredientInfoJsonDao();
    private static final DietDao DIET_DAO = new DietJsonDao();
    private static final BasicConversionInfoDao BASIC_CONVERSION_INFO_DAO = new BasicConversionInfoJsonDao();

    /* Cookbook business layer services. */
    private static final RecipeService RECIPE_SERVICE = new RecipeServiceImpl(RECIPE_DAO);   
    private static final PieceConversionService PIECE_CONVERSION_INFO = new PieceConversionServiceImpl(BASIC_CONVERSION_INFO_DAO);
    private static final IngredientQuantityService INGREDIENT_QUANTITY_SERVICE = new IngredientQuantityServiceImpl(INGREDIENT_INFO_DAO);
    private static final PriceCalculatorService PRICE_CALCULATOR_SERVICE = new PriceCalculatorServiceImpl(INGREDIENT_INFO_DAO, INGREDIENT_QUANTITY_SERVICE);
    private static final IngredientInfoService INGREDIENT_INFO_SERVICE = new IngredientInfoServiceImpl(INGREDIENT_INFO_DAO, PIECE_CONVERSION_INFO);

    /* Diet business layer services. */
    private static final DietService DIET_SERVICE = new DietServiceImpl(DIET_DAO);
    private static final GeneratorService GENERATOR_SERVICE = new GeneratorServiceImpl(RECIPE_DAO, DIET_DAO);

    public static RecipeService getRecipeService(){
        return RECIPE_SERVICE;
    }

    public static IngredientInfoService getIngredientInfoService(){
        return INGREDIENT_INFO_SERVICE;
    }

    public static PieceConversionService getPieceConversionService() {
        return PIECE_CONVERSION_INFO;
    }
    
    public static IngredientQuantityService getIngredientQuantityService() {
        return INGREDIENT_QUANTITY_SERVICE;
    }
    
    public static PriceCalculatorService getPriceCalculatorService() {
        return PRICE_CALCULATOR_SERVICE;
    }

    public static DietService getDietService() {
        return DIET_SERVICE;
    }

    public static GeneratorService getGeneratorService() {
        return GENERATOR_SERVICE;
    }

    public static ConfigService getConfigService(){
        return CONFIG_SERVICE;
    }

    public static UUIDService getUUIDService(){
        return UUID_SERVICE;
    }


    public static RecipeDao getRecipeDao() {
        return RECIPE_DAO;
    }
}