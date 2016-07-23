package cz.afrosoft.whattoeat.logic.exception;

import cz.afrosoft.whattoeat.logic.model.Ingredient;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;

/**
 * Exception representing case when {@link IngredientInfo} for {@link Ingredient} cannot be found.
 * @author Tomas Rejent
 */
public class IngredientInfoNotFound extends RuntimeException{

    public IngredientInfoNotFound() {
    }

    public IngredientInfoNotFound(String message) {
        super(message);
    }

    public IngredientInfoNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public IngredientInfoNotFound(Throwable cause) {
        super(cause);
    }

}
