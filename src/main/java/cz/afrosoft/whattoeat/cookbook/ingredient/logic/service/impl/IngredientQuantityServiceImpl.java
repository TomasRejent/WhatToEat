package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientQuantityService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IngredientQuantityService}.
 * <p>
 * TODO - Currently only basic formatting where internacionalized ingerdient unit is appended to entity is implemented.
 *
 * @author Tomas Rejent
 */
@Service
public class IngredientQuantityServiceImpl implements IngredientQuantityService {

    @Override
    public String getFormattedQuantity(final Ingredient ingredient, final float quantity) {
        Validate.notNull(ingredient);
        Validate.isTrue(quantity >= 0);

        return StringUtils.join(quantity, " ", I18n.getText(ingredient.getIngredientUnit().getLabelKey()));
    }
}
