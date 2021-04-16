package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.diet.list.logic.model.IngredientMap;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Rejent
 */
public class IngredientMapImpl implements IngredientMap {

    private final Integer id;
    private final Map<RecipeIngredient, RecipeIngredient> ingredientMap;

    public IngredientMapImpl(final Integer id, final Map<RecipeIngredient, RecipeIngredient> ingredientMap) {
        Validate.notNull(id);
        Validate.notNull(ingredientMap);

        this.id = id;
        this.ingredientMap = new HashMap<>(ingredientMap);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public RecipeIngredient getMappedIngredient(final RecipeIngredient genericIngredient) {
        Validate.notNull(genericIngredient);

        RecipeIngredient mappedIngredient = ingredientMap.get(genericIngredient);
        return mappedIngredient != null ? mappedIngredient : genericIngredient;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IngredientMapImpl that = (IngredientMapImpl) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("ingredientMap", ingredientMap)
            .toString();
    }
}
