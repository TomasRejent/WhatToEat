/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of persistence service of JSON type for entity {@link OldIngredient}.
 * @author Tomas Rejent
 */
public class IngredientInfoJsonDao extends JsonDao<OldIngredient, String> implements IngredientInfoDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeJsonDao.class);

    public IngredientInfoJsonDao() {
        super(LocationUtils.getIngredientFile(), OldIngredient[].class);
    }

    @Override
    public Set<String> getIngredientKeywords() {
        LOGGER.debug("Getting all ingredient keywords.");
        final List<OldIngredient> ingredients = readAll();
        final Set<String> keywordsSet = new HashSet<>();
        ingredients.stream().forEach((recipe) -> {
            keywordsSet.addAll(recipe.getKeywords());
        });
        LOGGER.debug("Found {} keywords.", keywordsSet.size());
        return Collections.unmodifiableSet(keywordsSet);
    }

}
